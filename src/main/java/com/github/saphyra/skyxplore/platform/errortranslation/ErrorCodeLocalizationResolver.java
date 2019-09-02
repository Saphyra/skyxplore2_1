package com.github.saphyra.skyxplore.platform.errortranslation;

import static java.util.Objects.isNull;
import static org.apache.http.util.TextUtils.isBlank;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.common.RequestConstants;
import com.github.saphyra.skyxplore.data.errorcode.ErrorCodeLocalization;
import com.github.saphyra.skyxplore.data.errorcode.ErrorCodeService;
import com.github.saphyra.skyxplore.userdata.settings.locale.LocaleCache;
import com.github.saphyra.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
//TODO unit test
class ErrorCodeLocalizationResolver {
    private final CookieUtil cookieUtil;
    private final ErrorCodeService errorCodeService;
    private final LocaleCache localeCache;

    ErrorCodeLocalization getErrorCodeLocalization(HttpServletRequest request) {
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
            errorCodeLocalization = getErrorCodeLocalizationByUserId(userId);
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

    private ErrorCodeLocalization getErrorCodeLocalizationByUserId(String userId) {
        Optional<String> locale = localeCache.get(userId);
        log.debug("Saved locale for userId {}: {}", userId, locale);
        return locale.map(errorCodeService::get).orElse(null);
    }
}
