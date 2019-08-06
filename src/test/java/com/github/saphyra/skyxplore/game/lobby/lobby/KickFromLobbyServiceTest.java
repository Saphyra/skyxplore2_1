package com.github.saphyra.skyxplore.game.lobby.lobby;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.exceptionhandling.exception.ForbiddenException;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.Lobby;

@RunWith(MockitoJUnitRunner.class)
public class KickFromLobbyServiceTest {
    private static final String OWNER_ID = "owner_id";
    private static final String MEMBER_ID = "member_id";

    @Mock
    private LobbyQueryService lobbyQueryService;

    @InjectMocks
    private KickFromLobbyService underTest;

    @Mock
    private Lobby lobby;

    @Test(expected = ForbiddenException.class)
    public void kickFromLobby_notLobbyOwner() {
        //GIVEN
        given(lobbyQueryService.findByCharacterIdValidated(MEMBER_ID)).willReturn(lobby);
        given(lobby.getOwnerId()).willReturn(OWNER_ID);
        //WHEN
        underTest.kickFromLobby(MEMBER_ID, OWNER_ID);
    }

    @Test
    public void kickFromLobby() {
        //GIVEN
        given(lobbyQueryService.findByCharacterIdValidated(OWNER_ID)).willReturn(lobby);
        given(lobby.getOwnerId()).willReturn(OWNER_ID);
        //WHEN
        underTest.kickFromLobby(OWNER_ID, MEMBER_ID);
        //THEN
        verify(lobby).removeMember(MEMBER_ID);
    }
}