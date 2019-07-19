package com.github.saphyra.skyxplore.filter;

import com.github.saphyra.skyxplore.common.PageController;
import com.github.saphyra.skyxplore.lobby.lobby.LobbyQueryService;
import com.github.saphyra.skyxplore.lobby.lobby.domain.Lobby;
import com.github.saphyra.util.CookieUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpMethod;
import org.springframework.util.AntPathMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static com.github.saphyra.skyxplore.filter.CustomFilterHelper.COOKIE_CHARACTER_ID;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class LobbyFilterTest {
    private static final String ALLOWED_URI = "/js/asd.js";
    private static final String PROTECTED_URI = "/rve";
    private static final String CHARACTER_ID = "character_id";

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Mock
    private CookieUtil cookieUtil;

    @Mock
    private CustomFilterHelper customFilterHelper;

    @Mock
    private LobbyQueryService lobbyQueryService;

    private LobbyFilter underTest;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private Lobby lobby;

    @Before
    public void setUp(){
        underTest = LobbyFilter.builder()
            .antPathMatcher(antPathMatcher)
            .cookieUtil(cookieUtil)
            .customFilterHelper(customFilterHelper)
            .lobbyQueryService(lobbyQueryService)
            .build();
    }

    @Test
    public void restCall_shouldFilter() throws ServletException, IOException {
        //GIVEN
        given(customFilterHelper.isRestCall(request)).willReturn(true);
        //WHEN
        underTest.doFilterInternal(request, response, filterChain);
        //THEN
        verify(filterChain).doFilter(request, response);
        verifyZeroInteractions(response);
    }

    @Test
    public void allowedPath_shouldFilter() throws ServletException, IOException {
        //GIVEN
        given(customFilterHelper.isRestCall(request)).willReturn(false);
        given(request.getRequestURI()).willReturn(ALLOWED_URI);
        //WHEN
        underTest.doFilterInternal(request, response, filterChain);
        //THEN
        verify(filterChain).doFilter(request, response);
        verifyZeroInteractions(response);
    }

    @Test
    public void notGetMethod_shouldFilter() throws ServletException, IOException {
        //GIVEN
        given(customFilterHelper.isRestCall(request)).willReturn(false);
        given(request.getRequestURI()).willReturn(PROTECTED_URI);
        given(request.getMethod()).willReturn(HttpMethod.POST.name());
        //WHEN
        underTest.doFilterInternal(request, response, filterChain);
        //THEN
        verify(filterChain).doFilter(request, response);
        verifyZeroInteractions(response);
    }

    @Test
    public void characterIdCookieIsEmpty_shouldFilter() throws ServletException, IOException {
        //GIVEN
        given(customFilterHelper.isRestCall(request)).willReturn(false);
        given(request.getRequestURI()).willReturn(PROTECTED_URI);
        given(request.getMethod()).willReturn(HttpMethod.GET.name());

        given(cookieUtil.getCookie(request, COOKIE_CHARACTER_ID)).willReturn(Optional.empty());
        //WHEN
        underTest.doFilterInternal(request, response, filterChain);
        //THEN
        verify(filterChain).doFilter(request, response);
        verifyZeroInteractions(response);
    }

    @Test
    public void inQueueAndNotQueuePageRequest_shouldRedirectToQueuePage() throws ServletException, IOException {
        //GIVEN
        given(customFilterHelper.isRestCall(request)).willReturn(false);
        given(request.getRequestURI()).willReturn(PROTECTED_URI);
        given(request.getMethod()).willReturn(HttpMethod.GET.name());

        given(cookieUtil.getCookie(request, COOKIE_CHARACTER_ID)).willReturn(Optional.of(CHARACTER_ID));
        given(lobbyQueryService.findByCharacterId(CHARACTER_ID)).willReturn(Optional.of(lobby));
        given(lobby.isInQueue()).willReturn(true);
        //WHEN
        underTest.doFilterInternal(request, response, filterChain);
        //THEN
        verify(response).sendRedirect(PageController.LOBBY_QUEUE_MAPPING);
        verifyZeroInteractions(filterChain);
    }

    @Test
    public void inLobbyAndNotLobbyPageRequest_shouldRedirectToLobbyPage() throws ServletException, IOException {
        //GIVEN
        given(customFilterHelper.isRestCall(request)).willReturn(false);
        given(request.getRequestURI()).willReturn(PROTECTED_URI);
        given(request.getMethod()).willReturn(HttpMethod.GET.name());

        given(cookieUtil.getCookie(request, COOKIE_CHARACTER_ID)).willReturn(Optional.of(CHARACTER_ID));
        given(lobbyQueryService.findByCharacterId(CHARACTER_ID)).willReturn(Optional.of(lobby));
        given(lobby.isInQueue()).willReturn(false);
        //WHEN
        underTest.doFilterInternal(request, response, filterChain);
        //THEN
        verify(response).sendRedirect(PageController.LOBBY_PAGE_MAPPING);
        verifyZeroInteractions(filterChain);
    }

    @Test
    public void notInLobbyAndLobbyPageRequest_shouldRedirectToOverviewPage() throws ServletException, IOException {
        //GIVEN
        given(customFilterHelper.isRestCall(request)).willReturn(false);
        given(request.getRequestURI()).willReturn(PageController.LOBBY_PAGE_MAPPING);
        given(request.getMethod()).willReturn(HttpMethod.GET.name());

        given(cookieUtil.getCookie(request, COOKIE_CHARACTER_ID)).willReturn(Optional.of(CHARACTER_ID));
        given(lobbyQueryService.findByCharacterId(CHARACTER_ID)).willReturn(Optional.empty());
        //WHEN
        underTest.doFilterInternal(request, response, filterChain);
        //THEN
        verify(response).sendRedirect(PageController.OVERVIEW_MAPPING);
        verifyZeroInteractions(filterChain);
    }

    @Test
    public void notInLobbyAndQueuePageRequest_shouldRedirectToOverviewPage() throws ServletException, IOException {
        //GIVEN
        given(customFilterHelper.isRestCall(request)).willReturn(false);
        given(request.getRequestURI()).willReturn(PageController.LOBBY_QUEUE_MAPPING);
        given(request.getMethod()).willReturn(HttpMethod.GET.name());

        given(cookieUtil.getCookie(request, COOKIE_CHARACTER_ID)).willReturn(Optional.of(CHARACTER_ID));
        given(lobbyQueryService.findByCharacterId(CHARACTER_ID)).willReturn(Optional.empty());
        //WHEN
        underTest.doFilterInternal(request, response, filterChain);
        //THEN
        verify(response).sendRedirect(PageController.OVERVIEW_MAPPING);
        verifyZeroInteractions(filterChain);
    }

    @Test
    public void inQueueAndQueuePageRequest_shouldFilter() throws ServletException, IOException {
        //GIVEN
        given(customFilterHelper.isRestCall(request)).willReturn(false);
        given(request.getRequestURI()).willReturn(PageController.LOBBY_QUEUE_MAPPING);
        given(request.getMethod()).willReturn(HttpMethod.GET.name());

        given(cookieUtil.getCookie(request, COOKIE_CHARACTER_ID)).willReturn(Optional.of(CHARACTER_ID));
        given(lobbyQueryService.findByCharacterId(CHARACTER_ID)).willReturn(Optional.of(lobby));
        given(lobby.isInQueue()).willReturn(true);
        //WHEN
        underTest.doFilterInternal(request, response, filterChain);
        //THEN
        verify(filterChain).doFilter(request, response);
        verifyZeroInteractions(response);
    }

    @Test
    public void inLobbyAndLobbyPageRequest_shouldFilter() throws ServletException, IOException {
        //GIVEN
        given(customFilterHelper.isRestCall(request)).willReturn(false);
        given(request.getRequestURI()).willReturn(PageController.LOBBY_PAGE_MAPPING);
        given(request.getMethod()).willReturn(HttpMethod.GET.name());

        given(cookieUtil.getCookie(request, COOKIE_CHARACTER_ID)).willReturn(Optional.of(CHARACTER_ID));
        given(lobbyQueryService.findByCharacterId(CHARACTER_ID)).willReturn(Optional.of(lobby));
        given(lobby.isInQueue()).willReturn(false);
        //WHEN
        underTest.doFilterInternal(request, response, filterChain);
        //THEN
        verify(filterChain).doFilter(request, response);
        verifyZeroInteractions(response);
    }

    @Test
    public void notInLobbyAndNotLobbyRelatedPageRequest_shouldFilter() throws ServletException, IOException {
        //GIVEN
        given(customFilterHelper.isRestCall(request)).willReturn(false);
        given(request.getRequestURI()).willReturn(PROTECTED_URI);
        given(request.getMethod()).willReturn(HttpMethod.GET.name());

        given(cookieUtil.getCookie(request, COOKIE_CHARACTER_ID)).willReturn(Optional.of(CHARACTER_ID));
        given(lobbyQueryService.findByCharacterId(CHARACTER_ID)).willReturn(Optional.empty());
        //WHEN
        underTest.doFilterInternal(request, response, filterChain);
        //THEN
        verify(filterChain).doFilter(request, response);
        verifyZeroInteractions(response);
    }
}