package org.github.saphyra.skyxplore.filter;

import static org.github.saphyra.skyxplore.filter.CustomFilterHelper.COOKIE_CHARACTER_ID;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.github.saphyra.skyxplore.common.PageController.CHARACTER_SELECT_MAPPING;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.github.saphyra.skyxplore.common.CookieUtil;

@RunWith(MockitoJUnitRunner.class)
public class CookieCleanupFilterTest {
    private static final String AUTHENTICATED_PATH = "authenticated_path";

    @Mock
    private CookieUtil cookieUtil;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private CookieCleanupFilter underTest;

    @Test
    public void testShouldCallCookieUtilWhenCharacterSelect() throws ServletException, IOException {
        //GIVEN
        when(request.getRequestURI()).thenReturn(CHARACTER_SELECT_MAPPING);
        //WHEN
        underTest.doFilterInternal(request, response, filterChain);
        //THEN
        verify(filterChain).doFilter(request, response);
        verify(cookieUtil).setCookie(response, COOKIE_CHARACTER_ID, "");
    }

    @Test
    public void testShouldNotCallCookieUtilWhenNotCharacterSelect() throws ServletException, IOException {
        //GIVEN
        when(request.getRequestURI()).thenReturn(AUTHENTICATED_PATH);
        //WHEN
        underTest.doFilterInternal(request, response, filterChain);
        //THEN
        verify(filterChain).doFilter(request, response);
        verifyNoMoreInteractions(cookieUtil);
    }
}