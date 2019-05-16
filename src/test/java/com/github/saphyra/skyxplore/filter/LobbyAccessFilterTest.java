package com.github.saphyra.skyxplore.filter;

import static com.github.saphyra.skyxplore.filter.CustomFilterHelper.COOKIE_CHARACTER_ID;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpMethod;

import com.github.saphyra.skyxplore.common.PageController;
import com.github.saphyra.skyxplore.lobby.lobby.LobbyQueryService;
import com.github.saphyra.skyxplore.lobby.lobby.domain.Lobby;
import com.github.saphyra.util.CookieUtil;

@RunWith(MockitoJUnitRunner.class)
public class LobbyAccessFilterTest {
    private static final String CHARACTER_ID = "character_id";

    @Mock
    private CookieUtil cookieUtil;

    @Mock
    private LobbyQueryService lobbyQueryService;

    @InjectMocks
    private LobbyAccessFilter underTest;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private Lobby lobby;

    @Test
    public void notLobbyMapping() throws ServletException, IOException {
        //GIVEN
        given(request.getRequestURI()).willReturn(PageController.CHARACTER_SELECT_MAPPING);
        //WHEN
        underTest.doFilterInternal(request, response, filterChain);
        //THEN
        verify(filterChain).doFilter(request, response);
    }

    @Test
    public void notGetMethod() throws ServletException, IOException {
        //GIVEN
        given(request.getRequestURI()).willReturn(PageController.LOBBY_MAPPING);
        given(request.getMethod()).willReturn(HttpMethod.POST.name());
        //WHEN
        underTest.doFilterInternal(request, response, filterChain);
        //THEN
        verify(filterChain).doFilter(request, response);
    }

    @Test
    public void noCharacterIdPresent_shouldRedirect() throws ServletException, IOException {
        //GIVEN
        given(request.getRequestURI()).willReturn(PageController.LOBBY_MAPPING);
        given(request.getMethod()).willReturn(HttpMethod.GET.name());
        given(cookieUtil.getCookie(request, COOKIE_CHARACTER_ID)).willReturn(Optional.empty());
        //WHEN
        underTest.doFilterInternal(request, response, filterChain);
        //THEN
        verify(response).sendRedirect(PageController.OVERVIEW_MAPPING);
        verifyZeroInteractions(filterChain);
    }

    @Test
    public void notInLobby_shouldRedirect() throws ServletException, IOException {
        //GIVEN
        given(request.getRequestURI()).willReturn(PageController.LOBBY_MAPPING);
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
    public void inLobby() throws ServletException, IOException {
        //GIVEN
        given(request.getRequestURI()).willReturn(PageController.LOBBY_MAPPING);
        given(request.getMethod()).willReturn(HttpMethod.GET.name());
        given(cookieUtil.getCookie(request, COOKIE_CHARACTER_ID)).willReturn(Optional.of(CHARACTER_ID));
        given(lobbyQueryService.findByCharacterId(CHARACTER_ID)).willReturn(Optional.of(lobby));
        //WHEN
        underTest.doFilterInternal(request, response, filterChain);
        //THEN
        verify(filterChain).doFilter(request, response);
    }
}