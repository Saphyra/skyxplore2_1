package com.github.saphyra.skyxplore.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CookieUtilTest {
    private static final String COOKIE_NAME = "cookie_name";
    private static final String COOKIE_VALUE = "cookie_value";

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private CookieUtil underTest;

    @Test
    public void testGetCookieShouldReturnNullWhenNoCookies() {
        //GIVEN
        when(request.getCookies()).thenReturn(null);
        //WHEN
        assertNull(underTest.getCookie(request, COOKIE_NAME));
        //THEN
        verify(request).getCookies();
    }

    @Test
    public void testGetCookieShouldReturnNullWhenNotFound() {
        //GIVEN
        when(request.getCookies()).thenReturn(new Cookie[0]);
        //WHEN
        assertNull(underTest.getCookie(request, COOKIE_NAME));
        //THEN
        verify(request).getCookies();
    }

    @Test
    public void testGetCookieShouldReturnCookie() {
        //GIVEN
        Cookie cookie = new Cookie(COOKIE_NAME, COOKIE_VALUE);
        Cookie cookie2 = new Cookie("asd", "das");
        when(request.getCookies()).thenReturn(new Cookie[]{cookie, cookie2});
        //WHEN
        String result = underTest.getCookie(request, COOKIE_NAME);
        //THEN
        assertThat(result).isEqualTo(COOKIE_VALUE);
        verify(request).getCookies();
    }

    @Test
    public void testSetCookie() {
        //WHEN
        underTest.setCookie(response, COOKIE_NAME, COOKIE_VALUE);
        //THEN
        ArgumentCaptor<Cookie> argumentCaptor = ArgumentCaptor.forClass(Cookie.class);
        verify(response).addCookie(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getName()).isEqualTo(COOKIE_NAME);
        assertThat(argumentCaptor.getValue().getValue()).isEqualTo(COOKIE_VALUE);
    }
}