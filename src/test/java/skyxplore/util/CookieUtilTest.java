package skyxplore.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        assertEquals(COOKIE_VALUE, result);
        verify(request).getCookies();
    }

    @Test
    public void testSetCookie() {
        //WHEN
        underTest.setCookie(response, COOKIE_NAME, COOKIE_VALUE);
        //THEN
        ArgumentCaptor<Cookie> argumentCaptor = ArgumentCaptor.forClass(Cookie.class);
        verify(response).addCookie(argumentCaptor.capture());
        assertEquals(COOKIE_VALUE, argumentCaptor.getValue().getValue());
        assertEquals(COOKIE_NAME, argumentCaptor.getValue().getName());
    }
}