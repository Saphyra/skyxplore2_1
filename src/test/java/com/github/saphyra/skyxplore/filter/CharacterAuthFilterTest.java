package com.github.saphyra.skyxplore.filter;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.common.exception.CharacterNotFoundException;
import com.github.saphyra.skyxplore.character.CharacterQueryService;
import com.github.saphyra.skyxplore.common.CookieUtil;
import com.github.saphyra.skyxplore.common.PageController;

@RunWith(MockitoJUnitRunner.class)
public class CharacterAuthFilterTest {
    private static final String CHARACTER_ID_COOKIE = "character_id_cookie";
    private static final String USER_ID_COOKIE = "user_id_cookie";
    private static final String AUTHENTICATED_PATH = "authenticated";

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
    private CustomFilterHelper customFilterHelper;

    @InjectMocks
    private CharacterAuthFilter underTest;

    @Before
    public void setUp() {
        when(cookieUtil.getCookie(request, CustomFilterHelper.COOKIE_CHARACTER_ID)).thenReturn(CHARACTER_ID_COOKIE);
        when(cookieUtil.getCookie(request, CustomFilterHelper.COOKIE_USER_ID)).thenReturn(USER_ID_COOKIE);
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
        when(cookieUtil.getCookie(request, CustomFilterHelper.COOKIE_USER_ID)).thenReturn(null);
        //WHEN
        underTest.doFilterInternal(request, response, filterChain);
        //THEN
        verify(cookieUtil).getCookie(request, CustomFilterHelper.COOKIE_USER_ID);
        verify(cookieUtil).getCookie(request, CustomFilterHelper.COOKIE_CHARACTER_ID);
        verify(customFilterHelper).handleUnauthorized(request, response, PageController.CHARACTER_SELECT_MAPPING);
        verifyNoMoreInteractions(characterQueryService);
        verifyNoMoreInteractions(filterChain);
    }

    @Test
    public void testShouldCallFilterHelperWhenCharacterIdCookieNotFound() throws ServletException, IOException {
        //GIVEN
        when(cookieUtil.getCookie(request, CustomFilterHelper.COOKIE_CHARACTER_ID)).thenReturn(null);
        //WHEN
        underTest.doFilterInternal(request, response, filterChain);
        //THEN
        verify(cookieUtil).getCookie(request, CustomFilterHelper.COOKIE_USER_ID);
        verify(cookieUtil).getCookie(request, CustomFilterHelper.COOKIE_CHARACTER_ID);
        verify(customFilterHelper).handleUnauthorized(request, response, PageController.CHARACTER_SELECT_MAPPING);
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
        verify(cookieUtil).getCookie(request, CustomFilterHelper.COOKIE_USER_ID);
        verify(cookieUtil).getCookie(request, CustomFilterHelper.COOKIE_CHARACTER_ID);
        verify(customFilterHelper).handleUnauthorized(request, response, PageController.CHARACTER_SELECT_MAPPING);
        verify(characterQueryService).findCharacterByIdAuthorized(CHARACTER_ID_COOKIE, USER_ID_COOKIE);
        verifyNoMoreInteractions(filterChain);
    }

    @Test
    public void testShouldCallFilterChainWhenAuthenticated() throws ServletException, IOException {
        //WHEN
        underTest.doFilterInternal(request, response, filterChain);
        //THEN
        verify(cookieUtil).getCookie(request, CustomFilterHelper.COOKIE_USER_ID);
        verify(cookieUtil).getCookie(request, CustomFilterHelper.COOKIE_CHARACTER_ID);
        verify(characterQueryService).findCharacterByIdAuthorized(CHARACTER_ID_COOKIE, USER_ID_COOKIE);
        verify(filterChain).doFilter(request, response);
    }
}