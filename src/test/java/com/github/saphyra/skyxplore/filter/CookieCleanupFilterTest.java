package com.github.saphyra.skyxplore.filter;

import com.github.saphyra.skyxplore.common.PageController;
import com.github.saphyra.util.CookieUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.util.AntPathMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CookieCleanupFilterTest {
    private static final String AUTHENTICATED_PATH = "authenticated_path";

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Mock
    private CookieUtil cookieUtil;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    private CookieCleanupFilter underTest;

    @Before
    public void setUp(){
        underTest = new CookieCleanupFilter(antPathMatcher, cookieUtil);
    }

    @Test
    public void testShouldCallCookieUtilWhenCharacterSelect() throws ServletException, IOException {
        //GIVEN
        when(request.getRequestURI()).thenReturn(PageController.CHARACTER_SELECT_MAPPING);
        //WHEN
        underTest.doFilterInternal(request, response, filterChain);
        //THEN
        verify(filterChain).doFilter(request, response);
        verify(cookieUtil).setCookie(response, CustomFilterHelper.COOKIE_CHARACTER_ID, "");
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