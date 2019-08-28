package com.github.saphyra.skyxplore.platform.filter;

import static com.github.saphyra.skyxplore.common.RequestConstants.COOKIE_LOCALE;
import static com.github.saphyra.skyxplore.common.RequestConstants.COOKIE_USER_ID;
import static com.github.saphyra.skyxplore.common.RequestConstants.DEFAULT_LOCALE;
import static org.apache.logging.log4j.util.Strings.isBlank;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.github.saphyra.skyxplore.userdata.settings.locale.LocaleCache;
import com.github.saphyra.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class LocaleFilter extends OncePerRequestFilter {
    private static final String HEADER_BROWSER_LANGUAGE = "BrowserLanguage";

    private final CookieUtil cookieUtil;
    private final LocaleCache localeCache;

    @Override
    //TODO unit test
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.debug("LocaleFilter");
        Optional<String> localeCookie = cookieUtil.getCookie(request, COOKIE_LOCALE)
            .filter(userIdValue -> !isBlank(userIdValue));
        Optional<String> userId = cookieUtil.getCookie(request, COOKIE_USER_ID);
        Optional<String> browserLanguage = Optional.ofNullable(request.getHeader(HEADER_BROWSER_LANGUAGE));
        Optional<String> savedLocale = userId.flatMap(localeCache::get);

        try {
            if (localeCookie.isPresent()) {
                log.debug("Locale cookie is present with value {}", localeCookie.get());
                userId.ifPresent(userIdValue -> {
                    log.debug("UserId is set: {}", userIdValue);
                    if (savedLocale.isPresent() && !savedLocale.get().equals(localeCookie.get())) {
                        log.debug("SavedLocale {} is not equal with locale cookie value {}. Updating...");
                        setLocale(response, savedLocale.get());
                    }
                });
            } else if (savedLocale.isPresent()) {
                log.debug("Locale cookie is not present. Setting up from savedLocale {}", savedLocale.get());
                setLocale(response, savedLocale.get());
            } else if (browserLanguage.isPresent()) {
                log.debug("Locale cookie is not present. Setting up from browserLanguage {}", browserLanguage.get());
                setLocale(response, browserLanguage.get());
            } else {
                log.debug("Locale cookie is not present. Setting up from defaultLocale {}", DEFAULT_LOCALE);
                setLocale(response, DEFAULT_LOCALE);
            }
        } catch (Exception e) {
            log.error("Unexpected error occurred during locale setup", e);
        }

        filterChain.doFilter(request, response);
    }

    private void setLocale(HttpServletResponse response, String locale) {
        cookieUtil.setCookie(response, COOKIE_LOCALE, locale, Integer.MAX_VALUE);
    }
}
