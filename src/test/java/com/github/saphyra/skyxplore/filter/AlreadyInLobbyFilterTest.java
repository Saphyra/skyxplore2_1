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

import com.github.saphyra.skyxplore.common.PageController;
import com.github.saphyra.skyxplore.lobby.lobby.LobbyQueryService;
import com.github.saphyra.skyxplore.lobby.lobby.domain.Lobby;
import com.github.saphyra.util.CookieUtil;

@RunWith(MockitoJUnitRunner.class)
public class AlreadyInLobbyFilterTest {
    private static final String ALLOWED_URI = "/";
    private static final String PROTECTED_URI = "asd";
    private static final String CHARACTER_ID = "character_id";

    @Mock
    private CookieUtil cookieUtil;

    @Mock
    private CustomFilterHelper customFilterHelper;

    @Mock
    private LobbyQueryService lobbyQueryService;

    @InjectMocks
    private AlreadyInLobbyFilter underTest;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private Lobby lobby;

    @Test
    public void isRestCall() throws ServletException, IOException {
        //GIVEN
        given(customFilterHelper.isRestCall(request)).willReturn(true);
        //WHEN
        underTest.doFilterInternal(request, response, filterChain);
        //THEN
        verify(filterChain).doFilter(request, response);
    }

    @Test
    public void allowedPath() throws ServletException, IOException {
        //GIVEN
        given(customFilterHelper.isRestCall(request)).willReturn(false);
        given(request.getRequestURI()).willReturn(ALLOWED_URI);
        //WHEN
        underTest.doFilterInternal(request, response, filterChain);
        //THEN
        verify(filterChain).doFilter(request, response);
    }

    @Test
    public void noCharacterIdCookie() throws ServletException, IOException {
        //GIVEN
        given(customFilterHelper.isRestCall(request)).willReturn(false);
        given(request.getRequestURI()).willReturn(PROTECTED_URI);
        given(cookieUtil.getCookie(request, COOKIE_CHARACTER_ID)).willReturn(Optional.empty());
        //WHEN
        underTest.doFilterInternal(request, response, filterChain);
        //THEN
        verify(filterChain).doFilter(request, response);
    }

    @Test
    public void notInLobby() throws ServletException, IOException {
        //GIVEN
        given(customFilterHelper.isRestCall(request)).willReturn(false);
        given(request.getRequestURI()).willReturn(PROTECTED_URI);
        given(cookieUtil.getCookie(request, COOKIE_CHARACTER_ID)).willReturn(Optional.of(CHARACTER_ID));
        given(lobbyQueryService.findByCharacterId(CHARACTER_ID)).willReturn(Optional.empty());
        //WHEN
        underTest.doFilterInternal(request, response, filterChain);
        //THEN
        verify(filterChain).doFilter(request, response);
    }

    @Test
    public void redirectWhenCharacterIsInLobby() throws ServletException, IOException {
        //GIVEN
        given(customFilterHelper.isRestCall(request)).willReturn(false);
        given(request.getRequestURI()).willReturn(PROTECTED_URI);
        given(cookieUtil.getCookie(request, COOKIE_CHARACTER_ID)).willReturn(Optional.of(CHARACTER_ID));
        given(lobbyQueryService.findByCharacterId(CHARACTER_ID)).willReturn(Optional.of(lobby));
        //WHEN
        underTest.doFilterInternal(request, response, filterChain);
        //THEN
        verify(response).sendRedirect(PageController.LOBBY_MAPPING);
        verifyZeroInteractions(filterChain);
    }
}