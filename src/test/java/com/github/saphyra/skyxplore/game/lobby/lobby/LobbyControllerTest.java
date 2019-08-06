package com.github.saphyra.skyxplore.game.lobby.lobby;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.game.lobby.lobby.creation.LobbyCreatorService;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.CreateLobbyRequest;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.LobbyEventView;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.LobbyMemberView;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.LobbyView;

@RunWith(MockitoJUnitRunner.class)
public class LobbyControllerTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String KICKED_ID = "kicked_id";
    private static final String NEW_OWNER_ID = "new_owner_id";

    @Mock
    private LobbyCreatorService lobbyCreatorService;

    @Mock
    private LobbyMemberHandler lobbyMemberHandler;

    @Mock
    private LobbyQueueService lobbyQueueService;

    @Mock
    private LobbyViewQueryService lobbyViewQueryService;

    @Mock
    private KickFromLobbyService kickFromLobbyService;

    @Mock
    private MemberStatusService memberStatusService;

    @Mock
    private TransferOwnershipService transferOwnershipService;

    @InjectMocks
    private LobbyController underTest;

    @Mock
    private CreateLobbyRequest createLobbyRequest;

    @Mock
    private LobbyView lobbyView;

    @Mock
    private LobbyEventView lobbyEventView;

    @Mock
    private LobbyMemberView lobbyMemberView;

    @Test
    public void createLobby() {
        //WHEN
        underTest.createLobby(createLobbyRequest, CHARACTER_ID);
        //THEN
        verify(lobbyCreatorService).createLobby(createLobbyRequest, CHARACTER_ID);
    }

    @Test
    public void exitFromLobby() {
        //WHEN
        underTest.exitFromLobby(CHARACTER_ID);
        //THEN
        verify(lobbyMemberHandler).exitFromLobby(CHARACTER_ID);
    }

    @Test
    public void getLobby() {
        //GIVEN
        given(lobbyViewQueryService.getLobbyView(CHARACTER_ID)).willReturn(lobbyView);
        //WHEN
        LobbyView result = underTest.getLobby(CHARACTER_ID);
        //THEN
        assertThat(result).isEqualTo(lobbyView);
    }

    @Test
    public void getEvents() {
        //GIVEN
        given(lobbyViewQueryService.getEvents(CHARACTER_ID)).willReturn(Arrays.asList(lobbyEventView));
        //WHEN
        List<LobbyEventView> result = underTest.getEvents(CHARACTER_ID);
        //THEN
        assertThat(result).containsOnly(lobbyEventView);
    }

    @Test
    public void getLobbyMembers() {
        //GIVEN
        given(lobbyMemberHandler.getMembers(CHARACTER_ID)).willReturn(Arrays.asList(lobbyMemberView));
        //WHEN
        List<LobbyMemberView> result = underTest.getLobbyMembers(CHARACTER_ID);
        //THEN
        assertThat(result).containsOnly(lobbyMemberView);
    }

    @Test
    public void kickMember() {
        //WHEN
        underTest.kickMember(CHARACTER_ID, KICKED_ID);
        //THEN
        verify(kickFromLobbyService).kickFromLobby(CHARACTER_ID, KICKED_ID);
    }

    @Test
    public void setReady() {
        //WHEN
        underTest.setReady(CHARACTER_ID);
        //THEN
        verify(memberStatusService).setReady(CHARACTER_ID);
    }

    @Test
    public void setUnready() {
        //WHEN
        underTest.setUnready(CHARACTER_ID);
        //THEN
        verify(memberStatusService).setUnready(CHARACTER_ID);
    }

    @Test
    public void startQueue() {
        //WHEN
        underTest.startQueue(CHARACTER_ID, true);
        //THEN
        verify(lobbyQueueService).startQueue(CHARACTER_ID, true);
    }

    @Test
    public void transferOwnership() {
        //WHEN
        underTest.transferOwnership(CHARACTER_ID, NEW_OWNER_ID);
        //THEN
        verify(transferOwnershipService).transferOwnership(CHARACTER_ID, NEW_OWNER_ID);
    }
}