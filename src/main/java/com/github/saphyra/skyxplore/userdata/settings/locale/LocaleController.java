package com.github.saphyra.skyxplore.userdata.settings.locale;

import static com.github.saphyra.skyxplore.common.RequestConstants.COOKIE_LOCALE;
import static org.apache.logging.log4j.util.Strings.isBlank;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.saphyra.skyxplore.common.RequestConstants;
import com.github.saphyra.skyxplore.data.errorcode.ErrorCodeService;
import com.github.saphyra.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
class LocaleController {
    private static final String SET_LOCALE_MAPPING = RequestConstants.API_PREFIX + "/locale/{locale}";

    private final CookieUtil cookieUtil;
    private final ErrorCodeService errorCodeService;
    private final LocaleService localeService;

    @PostMapping(SET_LOCALE_MAPPING)
    void setLocale(
        @PathVariable("locale") String locale,
        @CookieValue(value = RequestConstants.COOKIE_USER_ID, required = false) String userId,
        HttpServletResponse response
    ) {
        log.info("setLocale endpoint called with locale {} by userId {}", locale, userId);
        errorCodeService.validateLocale(locale);

        if (!isBlank(userId)) {
            localeService.setLocale(userId, locale);
        }

        cookieUtil.setCookie(response, COOKIE_LOCALE, locale);
    }
}
