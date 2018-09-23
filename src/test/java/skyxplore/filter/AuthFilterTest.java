package skyxplore.filter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.service.AccessTokenFacade;
import skyxplore.util.CookieUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;
import static skyxplore.controller.PageController.INDEX_MAPPING;
import static skyxplore.filter.FilterHelper.*;
import static skyxplore.testutil.TestUtils.*;

@RunWith(MockitoJUnitRunner.class)
public class AuthFilterTest {

    @Mock
    private AccessTokenFacade accessTokenFacade;

    @Mock
    private CookieUtil cookieUtil;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private FilterHelper filterHelper;

    @InjectMocks
    private AuthFilter underTest;

    @Before
    public void setUp() {
        when(request.getRequestURI()).thenReturn(AUTHENTICATED_PATH);
        when(cookieUtil.getCookie(request, COOKIE_USER_ID)).thenReturn(USER_ID_COOKIE);
        when(cookieUtil.getCookie(request, COOKIE_ACCESS_TOKEN)).thenReturn(ACCESS_TOKEN_COOKIE);
    }

    @Test
    public void testLogoutShouldCallFacade() throws ServletException, IOException {
        //GIVEN
        when(request.getRequestURI()).thenReturn("/logout");
        //WHEN
        underTest.doFilterInternal(request, response, filterChain);
        //THEN
        verify(cookieUtil).getCookie(request, COOKIE_USER_ID);
        verify(cookieUtil).getCookie(request, COOKIE_ACCESS_TOKEN);
        verify(accessTokenFacade).logout(USER_ID_COOKIE, ACCESS_TOKEN_COOKIE);
        verifyNoMoreInteractions(filterChain);
    }

    @Test
    public void testAllowedPathShouldNotCallFacade() throws ServletException, IOException {
        //GIVEN
        when(request.getRequestURI()).thenReturn("/js/");
        //WHEN
        underTest.doFilterInternal(request, response, filterChain);
        //THEN
        verifyNoMoreInteractions(accessTokenFacade);
        verify(filterChain).doFilter(request, response);
    }

    @Test
    public void testAuthenticationShouldNotCallFacadeWhenAccessTokenCookieNotFound() throws ServletException, IOException {
        //GIVEN
        when(cookieUtil.getCookie(request, COOKIE_ACCESS_TOKEN)).thenReturn(null);
        //WHEN
        underTest.doFilterInternal(request, response, filterChain);
        //THEN
        verify(cookieUtil).getCookie(request, COOKIE_ACCESS_TOKEN);
        verify(cookieUtil).getCookie(request, COOKIE_USER_ID);
        verifyNoMoreInteractions(accessTokenFacade);
        verifyNoMoreInteractions(filterChain);
        verify(filterHelper).handleUnauthorized(request, response, INDEX_MAPPING);
    }

    @Test
    public void testAuthenticationShouldNotCallFacadeWhenUserIdCookieNotFound() throws ServletException, IOException {
        //GIVEN
        when(cookieUtil.getCookie(request, COOKIE_USER_ID)).thenReturn(null);
        //WHEN
        underTest.doFilterInternal(request, response, filterChain);
        //THEN
        verify(cookieUtil).getCookie(request, COOKIE_ACCESS_TOKEN);
        verify(cookieUtil).getCookie(request, COOKIE_USER_ID);
        verifyNoMoreInteractions(accessTokenFacade);
        verifyNoMoreInteractions(filterChain);
        verify(filterHelper).handleUnauthorized(request, response, INDEX_MAPPING);
    }

    @Test
    public void testAuthenticationShouldCallFacadeAndFilterChain() throws ServletException, IOException {
        //GIVEN
        when(accessTokenFacade.isAuthenticated(USER_ID_COOKIE, ACCESS_TOKEN_COOKIE)).thenReturn(true);
        //WHEN
        underTest.doFilterInternal(request, response, filterChain);
        //THEN
        verify(cookieUtil).getCookie(request, COOKIE_ACCESS_TOKEN);
        verify(cookieUtil).getCookie(request, COOKIE_USER_ID);
        verify(accessTokenFacade).isAuthenticated(USER_ID_COOKIE, ACCESS_TOKEN_COOKIE);
        verify(filterChain).doFilter(request, response);
    }

    @Test
    public void testShouldCallFilterHelperWhenNotAuthenticated() throws ServletException, IOException {
        //GIVEN
        when(accessTokenFacade.isAuthenticated(USER_ID_COOKIE, ACCESS_TOKEN_COOKIE)).thenReturn(false);
        //WHEN
        underTest.doFilterInternal(request, response, filterChain);
        //THEN
        verify(cookieUtil).getCookie(request, COOKIE_ACCESS_TOKEN);
        verify(cookieUtil).getCookie(request, COOKIE_USER_ID);
        verify(accessTokenFacade).isAuthenticated(USER_ID_COOKIE, ACCESS_TOKEN_COOKIE);
        verifyNoMoreInteractions(filterChain);
    }
}