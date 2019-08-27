package com.github.saphyra.skyxplore.userdata.settings.locale;

import com.github.saphyra.skyxplore.common.RequestConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
class LocaleController {
    private static final String SET_LOCALE_MAPPING = RequestConstants.API_PREFIX + "/locale/{locale}";

    private final LocaleService localeService;

    @PostMapping(SET_LOCALE_MAPPING)
    //TODO unit test
    void setLocale(
        @PathVariable("locale") String locale,
        @CookieValue(RequestConstants.COOKIE_USER_ID) String userId
    ) {
        log.info("setLocale endpoint called with locale {} by userId {}", locale, userId);
        localeService.setLocale(userId, locale);
    }
}
