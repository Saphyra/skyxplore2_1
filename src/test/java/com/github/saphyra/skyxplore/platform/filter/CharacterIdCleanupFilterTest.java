package com.github.saphyra.skyxplore.platform.filter;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.util.AntPathMatcher;

import com.github.saphyra.skyxplore.common.PageController;
import com.github.saphyra.skyxplore.common.RequestConstants;
import com.github.saphyra.skyxplore.platform.auth.repository.AccessTokenDao;
import com.github.saphyra.util.CookieUtil;

@RunWith(MockitoJUnitRunner.class)
public class CharacterIdCleanupFilterTest {
    private static final String CHARACTER_ID = "character_id";

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Mock
    private AccessTokenDao accessTokenDao;

    @Mock
    private CookieUtil cookieUtil;

    private CharacterIdCleanupFilter underTest;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Before
    public void setUp(){
        underTest = CharacterIdCleanupFilter.builder()
            .accessTokenDao(accessTokenDao)
            .antPathMatcher(antPathMatcher)
            .cookieUtil(cookieUtil)
            .build();
    }

    @Test
    public void filter_protectedPath_cookieNotPresent() throws ServletException, IOException {
        //GIVEN
        given(request.getRequestURI()).willReturn(PageController.INDEX_MAPPING);
        given(cookieUtil.getCookie(request, RequestConstants.COOKIE_CHARACTER_ID)).willReturn(Optional.empty());
        //WHEN
        underTest.doFilterInternal(request, response, filterChain);
        //THEN
        verifyZeroInteractions(accessTokenDao);
        verify(filterChain).doFilter(request, response);
    }

    @Test
    public void filter_protectedPath_cookiePresent() throws ServletException, IOException {
        //GIVEN
        given(request.getRequestURI()).willReturn(PageController.INDEX_MAPPING);
        given(cookieUtil.getCookie(request, RequestConstants.COOKIE_CHARACTER_ID)).willReturn(Optional.of(CHARACTER_ID));
        //WHEN
        underTest.doFilterInternal(request, response, filterChain);
        //THEN
        verify(accessTokenDao).cleanupCharacterId(CHARACTER_ID);
        verify(filterChain).doFilter(request, response);
    }

    @Test
    public void filter_notProtectedPath() throws ServletException, IOException {
        //GIVEN
        given(request.getRequestURI()).willReturn(PageController.LOBBY_QUEUE_MAPPING);
        //WHEN
        underTest.doFilterInternal(request, response, filterChain);
        //THEN
        verifyZeroInteractions(accessTokenDao);
        verify(filterChain).doFilter(request, response);
    }
}