package com.github.saphyra.skyxplore.platform.errortranslation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.common.RequestConstants;
import com.github.saphyra.skyxplore.data.errorcode.ErrorCodeLocalization;
import com.github.saphyra.skyxplore.data.errorcode.ErrorCodeService;
import com.github.saphyra.skyxplore.userdata.settings.locale.LocaleCache;
import com.github.saphyra.util.CookieUtil;

@RunWith(MockitoJUnitRunner.class)
public class ErrorCodeLocalizationResolverTest {
    private static final String USER_ID = "user-id";
    private static final String STORED_LOCALE = "stored-locale";
    private static final String LOCALE_COOKIE_VALUE = "locale_cookie_value";
    private static final String BROWSER_LANGUAGE = "browser-language";

    @Mock
    private CookieUtil cookieUtil;

    @Mock
    private ErrorCodeService errorCodeService;

    @Mock
    private LocaleCache localeCache;

    @InjectMocks
    private ErrorCodeLocalizationResolver underTest;

    @Mock
    private HttpServletRequest request;

    @Mock
    private ErrorCodeLocalization errorCodeLocalization;

    @Test
    public void getErrorCodeLocalization_userSettingsFound() {
        //GIVEN
        given(cookieUtil.getCookie(request, RequestConstants.COOKIE_USER_ID)).willReturn(Optional.of(USER_ID));
        given(localeCache.get(USER_ID)).willReturn(Optional.of(STORED_LOCALE));
        given(errorCodeService.getOptional(STORED_LOCALE)).willReturn(Optional.of(errorCodeLocalization));
        //WHEN
        Optional<ErrorCodeLocalization> result = underTest.getErrorCodeLocalization(request);
        //THEN
        assertThat(result).contains(errorCodeLocalization);
    }

    @Test
    public void getErrorCodeLocalization_storedLocaleNotSupported_fallbackToLocaleCookie() {
        //GIVEN
        given(cookieUtil.getCookie(request, RequestConstants.COOKIE_USER_ID)).willReturn(Optional.of(USER_ID));
        given(localeCache.get(USER_ID)).willReturn(Optional.of(STORED_LOCALE));
        given(errorCodeService.getOptional(STORED_LOCALE)).willReturn(Optional.empty());
        given(cookieUtil.getCookie(request, RequestConstants.COOKIE_LOCALE)).willReturn(Optional.of(LOCALE_COOKIE_VALUE));
        given(errorCodeService.getOptional(LOCALE_COOKIE_VALUE)).willReturn(Optional.of(errorCodeLocalization));
        //WHEN
        Optional<ErrorCodeLocalization> result = underTest.getErrorCodeLocalization(request);
        //THEN
        assertThat(result).contains(errorCodeLocalization);
    }

    @Test
    public void getErrorCodeLocalization_storedLocaleNotFound_fallbackToLocaleCookie() {
        //GIVEN
        given(cookieUtil.getCookie(request, RequestConstants.COOKIE_USER_ID)).willReturn(Optional.of(USER_ID));
        given(localeCache.get(USER_ID)).willReturn(Optional.empty());
        given(cookieUtil.getCookie(request, RequestConstants.COOKIE_LOCALE)).willReturn(Optional.of(LOCALE_COOKIE_VALUE));
        given(errorCodeService.getOptional(LOCALE_COOKIE_VALUE)).willReturn(Optional.of(errorCodeLocalization));
        //WHEN
        Optional<ErrorCodeLocalization> result = underTest.getErrorCodeLocalization(request);
        //THEN
        assertThat(result).contains(errorCodeLocalization);
    }

    @Test
    public void getErrorCodeLocalization_userIdCookieNotFound_fallbackToLocaleCookie() {
        //GIVEN
        given(cookieUtil.getCookie(request, RequestConstants.COOKIE_USER_ID)).willReturn(Optional.of(""));
        given(cookieUtil.getCookie(request, RequestConstants.COOKIE_LOCALE)).willReturn(Optional.of(LOCALE_COOKIE_VALUE));
        given(errorCodeService.getOptional(LOCALE_COOKIE_VALUE)).willReturn(Optional.of(errorCodeLocalization));
        //WHEN
        Optional<ErrorCodeLocalization> result = underTest.getErrorCodeLocalization(request);
        //THEN
        assertThat(result).contains(errorCodeLocalization);
    }

    @Test
    public void getErrorCodeLocalization_localeCookieNotFound_fallbackToBrowserLanguage() {
        //GIVEN
        given(cookieUtil.getCookie(request, RequestConstants.COOKIE_USER_ID)).willReturn(Optional.of(""));
        given(cookieUtil.getCookie(request, RequestConstants.COOKIE_LOCALE)).willReturn(Optional.of(""));
        given(request.getHeader(RequestConstants.HEADER_BROWSER_LANGUAGE)).willReturn(BROWSER_LANGUAGE);
        given(errorCodeService.getOptional(BROWSER_LANGUAGE)).willReturn(Optional.of(errorCodeLocalization));
        //WHEN
        Optional<ErrorCodeLocalization> result = underTest.getErrorCodeLocalization(request);
        //THEN
        assertThat(result).contains(errorCodeLocalization);
    }

    @Test
    public void getErrorCodeLocalization_browserLanguageNotFound_fallbackToDefaultLocale() {
        //GIVEN
        given(cookieUtil.getCookie(request, RequestConstants.COOKIE_USER_ID)).willReturn(Optional.of(""));
        given(cookieUtil.getCookie(request, RequestConstants.COOKIE_LOCALE)).willReturn(Optional.of(""));
        given(request.getHeader(RequestConstants.HEADER_BROWSER_LANGUAGE)).willReturn("");
        given(errorCodeService.getOptional(RequestConstants.DEFAULT_LOCALE)).willReturn(Optional.of(errorCodeLocalization));
        //WHEN
        Optional<ErrorCodeLocalization> result = underTest.getErrorCodeLocalization(request);
        //THEN
        assertThat(result).contains(errorCodeLocalization);
    }

    @Test
    public void getErrorCodeLocalization_notFound() {
        //GIVEN
        given(cookieUtil.getCookie(request, RequestConstants.COOKIE_USER_ID)).willReturn(Optional.of(""));
        given(cookieUtil.getCookie(request, RequestConstants.COOKIE_LOCALE)).willReturn(Optional.of(""));
        given(request.getHeader(RequestConstants.HEADER_BROWSER_LANGUAGE)).willReturn("");
        given(errorCodeService.getOptional(RequestConstants.DEFAULT_LOCALE)).willReturn(Optional.empty());
        //WHEN
        Optional<ErrorCodeLocalization> result = underTest.getErrorCodeLocalization(request);
        //THEN
        assertThat(result).isEmpty();
    }
}