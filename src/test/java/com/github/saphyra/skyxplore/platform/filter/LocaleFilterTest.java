package com.github.saphyra.skyxplore.platform.filter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.common.RequestConstants;
import com.github.saphyra.skyxplore.userdata.settings.locale.LocaleCache;
import com.github.saphyra.util.CookieUtil;

@RunWith(MockitoJUnitRunner.class)
public class LocaleFilterTest {
    private static final String LOCALE_COOKIE_VALUE = "locale_cookie_value";
    private static final String USER_ID = "user_id";
    private static final String STORED_LOCALE = "stored_locale";
    private static final String BROWSER_LANGUAGE = "browser-language";

    @Mock
    private CookieUtil cookieUtil;

    @Mock
    private LocaleCache localeCache;

    @InjectMocks
    private LocaleFilter underTest;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Test
    public void filter_updateCookieFromStoredValue() throws ServletException, IOException {
        //GIVEN
        given(cookieUtil.getCookie(request, RequestConstants.COOKIE_LOCALE)).willReturn(Optional.of(LOCALE_COOKIE_VALUE));
        given(cookieUtil.getCookie(request, RequestConstants.COOKIE_USER_ID)).willReturn(Optional.of(USER_ID));
        given(localeCache.get(USER_ID)).willReturn(Optional.of(STORED_LOCALE));
        //WHEN
        underTest.doFilterInternal(request, response, filterChain);
        //THEN
        verify(cookieUtil).setCookie(response, RequestConstants.COOKIE_LOCALE, STORED_LOCALE, Integer.MAX_VALUE);
        verify(filterChain).doFilter(request, response);
    }

    @Test
    public void filter_shouldNotUpdateCookieFromStoredValue() throws ServletException, IOException {
        //GIVEN
        given(cookieUtil.getCookie(request, RequestConstants.COOKIE_LOCALE)).willReturn(Optional.of(LOCALE_COOKIE_VALUE));
        given(cookieUtil.getCookie(request, RequestConstants.COOKIE_USER_ID)).willReturn(Optional.of(USER_ID));
        given(localeCache.get(USER_ID)).willReturn(Optional.of(LOCALE_COOKIE_VALUE));
        //WHEN
        underTest.doFilterInternal(request, response, filterChain);
        //THEN
        verify(cookieUtil, times(0)).setCookie(any(), anyString(), anyString(), anyInt());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    public void filter_setCookieFromStoredLocale() throws ServletException, IOException {
        //GIVEN
        given(cookieUtil.getCookie(request, RequestConstants.COOKIE_LOCALE)).willReturn(Optional.empty());
        given(cookieUtil.getCookie(request, RequestConstants.COOKIE_USER_ID)).willReturn(Optional.of(USER_ID));
        given(localeCache.get(USER_ID)).willReturn(Optional.of(STORED_LOCALE));
        //WHEN
        underTest.doFilterInternal(request, response, filterChain);
        //THEN
        verify(cookieUtil).setCookie(response, RequestConstants.COOKIE_LOCALE, STORED_LOCALE, Integer.MAX_VALUE);
        verify(filterChain).doFilter(request, response);
    }

    @Test
    public void filter_setCookieFromBrowserLanguage() throws ServletException, IOException {
        //GIVEN
        given(cookieUtil.getCookie(request, RequestConstants.COOKIE_LOCALE)).willReturn(Optional.empty());
        given(cookieUtil.getCookie(request, RequestConstants.COOKIE_USER_ID)).willReturn(Optional.empty());
        given(request.getHeader(RequestConstants.HEADER_BROWSER_LANGUAGE)).willReturn(BROWSER_LANGUAGE);
        //WHEN
        underTest.doFilterInternal(request, response, filterChain);
        //THEN
        verify(cookieUtil).setCookie(response, RequestConstants.COOKIE_LOCALE, BROWSER_LANGUAGE, Integer.MAX_VALUE);
        verify(filterChain).doFilter(request, response);
    }

    @Test
    public void filter_setCookieFromDefaultLocale() throws ServletException, IOException {
        //GIVEN
        given(cookieUtil.getCookie(request, RequestConstants.COOKIE_LOCALE)).willReturn(Optional.empty());
        given(cookieUtil.getCookie(request, RequestConstants.COOKIE_USER_ID)).willReturn(Optional.empty());
        given(request.getHeader(RequestConstants.HEADER_BROWSER_LANGUAGE)).willReturn("");
        //WHEN
        underTest.doFilterInternal(request, response, filterChain);
        //THEN
        verify(cookieUtil).setCookie(response, RequestConstants.COOKIE_LOCALE, RequestConstants.DEFAULT_LOCALE, Integer.MAX_VALUE);
        verify(filterChain).doFilter(request, response);
    }
}