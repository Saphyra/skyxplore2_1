package skyxplore.filter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.exception.CharacterNotFoundException;
import skyxplore.service.character.CharacterQueryService;
import skyxplore.util.CookieUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;
import static skyxplore.controller.PageController.CHARACTER_SELECT_MAPPING;
import static skyxplore.filter.FilterHelper.COOKIE_CHARACTER_ID;
import static skyxplore.filter.FilterHelper.COOKIE_USER_ID;
import static skyxplore.testutil.TestUtils.*;

@RunWith(MockitoJUnitRunner.class)
public class CharacterAuthFilterTest {
    @Mock
    private CharacterQueryService characterQueryService;

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
    private CharacterAuthFilter underTest;

    @Before
    public void setUp() {
        when(cookieUtil.getCookie(request, COOKIE_CHARACTER_ID)).thenReturn(CHARACTER_ID_COOKIE);
        when(cookieUtil.getCookie(request, COOKIE_USER_ID)).thenReturn(USER_ID_COOKIE);
        when(request.getRequestURI()).thenReturn(AUTHENTICATED_PATH);
    }

    @Test
    public void testAllowedPathShouldNotCallService() throws ServletException, IOException {
        //GIVEN
        when(request.getRequestURI()).thenReturn("/login");
        //WHEN
        underTest.doFilterInternal(request, response, filterChain);
        //THEN
        verify(filterChain).doFilter(request, response);
        verifyNoMoreInteractions(characterQueryService);
    }

    @Test
    public void testShouldCallFilterHelperWhenUserIdCookieNotFound() throws ServletException, IOException {
        //GIVEN
        when(cookieUtil.getCookie(request, COOKIE_USER_ID)).thenReturn(null);
        //WHEN
        underTest.doFilterInternal(request, response, filterChain);
        //THEN
        verify(cookieUtil).getCookie(request, COOKIE_USER_ID);
        verify(cookieUtil).getCookie(request, COOKIE_CHARACTER_ID);
        verify(filterHelper).handleUnauthorized(request, response, CHARACTER_SELECT_MAPPING);
        verifyNoMoreInteractions(characterQueryService);
        verifyNoMoreInteractions(filterChain);
    }

    @Test
    public void testShouldCallFilterHelperWhenCharacterIdCookieNotFound() throws ServletException, IOException {
        //GIVEN
        when(cookieUtil.getCookie(request, COOKIE_CHARACTER_ID)).thenReturn(null);
        //WHEN
        underTest.doFilterInternal(request, response, filterChain);
        //THEN
        verify(cookieUtil).getCookie(request, COOKIE_USER_ID);
        verify(cookieUtil).getCookie(request, COOKIE_CHARACTER_ID);
        verify(filterHelper).handleUnauthorized(request, response, CHARACTER_SELECT_MAPPING);
        verifyNoMoreInteractions(characterQueryService);
        verifyNoMoreInteractions(filterChain);
    }

    @Test
    public void testShouldCallFilterHelperWhenNotAuthorized() throws ServletException, IOException {
        //GIVEN
        when(characterQueryService.findCharacterByIdAuthorized(CHARACTER_ID_COOKIE, USER_ID_COOKIE)).thenThrow(new CharacterNotFoundException("asd"));
        //WHEN
        underTest.doFilterInternal(request, response, filterChain);
        //THEN
        verify(cookieUtil).getCookie(request, COOKIE_USER_ID);
        verify(cookieUtil).getCookie(request, COOKIE_CHARACTER_ID);
        verify(filterHelper).handleUnauthorized(request, response, CHARACTER_SELECT_MAPPING);
        verify(characterQueryService).findCharacterByIdAuthorized(CHARACTER_ID_COOKIE, USER_ID_COOKIE);
        verifyNoMoreInteractions(filterChain);
    }

    @Test
    public void testShouldCallFilterChainWhenAuthenticated() throws ServletException, IOException {
        //WHEN
        underTest.doFilterInternal(request, response, filterChain);
        //THEN
        verify(cookieUtil).getCookie(request, COOKIE_USER_ID);
        verify(cookieUtil).getCookie(request, COOKIE_CHARACTER_ID);
        verify(characterQueryService).findCharacterByIdAuthorized(CHARACTER_ID_COOKIE, USER_ID_COOKIE);
        verify(filterChain).doFilter(request, response);
    }
}