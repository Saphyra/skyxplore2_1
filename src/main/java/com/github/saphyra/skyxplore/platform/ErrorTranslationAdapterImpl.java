package com.github.saphyra.skyxplore.platform;

import static java.util.Objects.isNull;
import static org.apache.http.util.TextUtils.isBlank;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.github.saphyra.exceptionhandling.ErrorTranslationAdapter;
import com.github.saphyra.skyxplore.common.RequestConstants;
import com.github.saphyra.skyxplore.data.errorcode.ErrorCodeLocalization;
import com.github.saphyra.skyxplore.data.errorcode.ErrorCodeService;
import com.github.saphyra.skyxplore.userdata.settings.locale.LocaleCache;
import com.github.saphyra.util.CookieUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ErrorTranslationAdapterImpl implements ErrorTranslationAdapter {
    private static final String DEFAULT_ERROR_TRANSLATION_PREFIX = "Could not translate errorCode %s";

    private final CookieUtil cookieUtil;
    private final ErrorCodeService errorCodeService;
    private final LocaleCache localeCache;

    public String translateMessage(HttpServletRequest request, @NonNull String errorCode, @NonNull Map<String, String> params) {
        log.info("Translating errorCode {} with params {}", errorCode, params);

        ErrorCodeLocalization errorCodeLocalization = getErrorCodeLocalization(request);
        String errorMessage = getErrorMessage(errorCode, errorCodeLocalization);
        String result = insertParams(errorMessage, params);
        log.info("Translated errorMessage: {}", result);
        return result;
    }

    private ErrorCodeLocalization getErrorCodeLocalization(HttpServletRequest request) {
        Optional<String> userIdCookie = cookieUtil.getCookie(request, RequestConstants.COOKIE_USER_ID)
            .filter(s -> !isBlank(s));
        log.debug("userId in cookie: {}", userIdCookie);

        Optional<String> localeCookie = cookieUtil.getCookie(request, RequestConstants.COOKIE_LOCALE)
            .filter(s -> !isBlank(s));
        log.debug("locale in cookie: {}", localeCookie);

        Optional<String> browserLanguageCookie = Optional.ofNullable(request.getHeader(RequestConstants.HEADER_BROWSER_LANGUAGE))
            .filter(bl -> !isBlank(bl));
        log.debug("browserLanguage in header: {}", browserLanguageCookie);

        ErrorCodeLocalization errorCodeLocalization = null;
        if (userIdCookie.isPresent()) {
            String userId = userIdCookie.get();
            Optional<String> locale = localeCache.get(userId);
            log.debug("Saved locale for userId {}: {}", userId, locale);
            if (locale.isPresent()) {
                errorCodeLocalization = errorCodeService.get(locale.get());
            }
        }

        if (isNull(errorCodeLocalization) && localeCookie.isPresent()) {
            log.debug("errorCodeLocalization not found with userSetting. Using localeCookie...");
            errorCodeLocalization = errorCodeService.get(localeCookie.get());
        }

        if (isNull(errorCodeLocalization) && browserLanguageCookie.isPresent()) {
            log.debug("errorCodeLocalization not found with userSetting and localeCookie. Using browserLanguage...");
            errorCodeLocalization = errorCodeService.get(browserLanguageCookie.get());
        }

        if (isNull(errorCodeLocalization)) {
            log.debug("errorCodeLocalization not found with userSetting, localeCookie and browserLanguage.. Using defaultLocale...");
            errorCodeLocalization = errorCodeService.get(RequestConstants.DEFAULT_LOCALE);
        }
        return errorCodeLocalization;
    }

    private String getErrorMessage(String errorCode, ErrorCodeLocalization errorCodeLocalization) {
        String errorMessage = Optional.ofNullable(errorCodeLocalization)
            .flatMap(errorCodes -> Optional.ofNullable(errorCodes.get(errorCode)))
            .orElseGet(() -> String.format(DEFAULT_ERROR_TRANSLATION_PREFIX, errorCode));
        log.debug("ErrorMessage found: {}", errorMessage);
        return errorMessage;
    }

    private String insertParams(String errorMessage, Map<String, String> params) {
        String result = errorMessage;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = assembleKey(entry.getKey());
            result = result.replaceAll(key, entry.getValue());
        }
        return result;
    }

    private String assembleKey(String key) {
        return "${" + key + "}";
    }
}
