package com.github.saphyra.skyxplore.userdata.settings.locale;

import static com.github.saphyra.skyxplore.common.RequestConstants.COOKIE_LOCALE;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.data.errorcode.ErrorCodeService;
import com.github.saphyra.util.CookieUtil;

@RunWith(MockitoJUnitRunner.class)
public class LocaleControllerTest {
    private static final String LOCALE = "locale";
    private static final String USER_ID = "user_id";

    @Mock
    private CookieUtil cookieUtil;

    @Mock
    private ErrorCodeService errorCodeService;

    @Mock
    private LocaleService localeService;

    @InjectMocks
    private LocaleController underTest;

    @Mock
    private HttpServletResponse response;

    @Test
    public void setLocale() {
        //WHEN
        underTest.setLocale(LOCALE, USER_ID, response);
        //THEN
        verify(errorCodeService).validateLocale(LOCALE);
        verify(localeService).setLocale(USER_ID, LOCALE);
        verify(cookieUtil).setCookie(response, COOKIE_LOCALE, LOCALE);
    }

    @Test
    public void setLocale_emptyUserId() {
        //WHEN
        underTest.setLocale(LOCALE, "", response);
        //THEN
        verify(errorCodeService).validateLocale(LOCALE);
        verifyZeroInteractions(localeService);
        verify(cookieUtil).setCookie(response, COOKIE_LOCALE, LOCALE);
    }
}