package skyxplore.filter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.controller.PageController;
import skyxplore.service.AccessTokenFacade;
import skyxplore.util.CookieUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static skyxplore.filter.AuthFilter.*;

@RunWith(MockitoJUnitRunner.class)
public class AuthFilterTest {
    private static final String USER_ID_COOKIE = "user_id_cookie";
    private static final String ACCESS_TOKEN_COOKIE = "access_token_cookie";
    private static final String AUTHENTICATED_PATH = "authenticated_path";
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
    public void testShouldRedirectToLoginPageWhenNotAuthenticated() throws ServletException, IOException {
        //GIVEN
        when(accessTokenFacade.isAuthenticated(USER_ID_COOKIE, ACCESS_TOKEN_COOKIE)).thenReturn(false);
        //WHEN
        underTest.doFilterInternal(request, response, filterChain);
        //THEN
        verify(cookieUtil).getCookie(request, COOKIE_ACCESS_TOKEN);
        verify(cookieUtil).getCookie(request, COOKIE_USER_ID);
        verify(accessTokenFacade).isAuthenticated(USER_ID_COOKIE, ACCESS_TOKEN_COOKIE);
        verifyNoMoreInteractions(filterChain);
        verify(response).sendRedirect(PageController.INDEX_MAPPING);
    }

    @Test
    public void testShouldSendErrorWhenNotAuthenticated() throws ServletException, IOException {
        //GIVEN
        when(accessTokenFacade.isAuthenticated(USER_ID_COOKIE, ACCESS_TOKEN_COOKIE)).thenReturn(false);
        when(request.getHeader(REQUEST_TYPE_HEADER)).thenReturn(REST_TYPE_REQUEST);
        //WHEN
        underTest.doFilterInternal(request, response, filterChain);
        //THEN
        verify(cookieUtil).getCookie(request, COOKIE_ACCESS_TOKEN);
        verify(cookieUtil).getCookie(request, COOKIE_USER_ID);
        verify(accessTokenFacade).isAuthenticated(USER_ID_COOKIE, ACCESS_TOKEN_COOKIE);
        verifyNoMoreInteractions(filterChain);
        verify(response).sendError(eq(HttpServletResponse.SC_UNAUTHORIZED), anyString());
    }
}