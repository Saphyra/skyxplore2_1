package com.github.saphyra.skyxplore.game.lobby.lobby;

import com.github.saphyra.skyxplore.userdata.character.CharacterViewQueryService;
import com.github.saphyra.skyxplore.common.domain.character.CharacterView;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.GameMode;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.Lobby;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.LobbyEvent;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.LobbyEventType;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.LobbyEventView;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.LobbyView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class LobbyViewQueryServiceTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String LOBBY_DATA = "lobby_data";
    private static final String OWNER_ID = "owner_id";

    @Mock
    private CharacterViewQueryService characterViewQueryService;

    @Mock
    private LobbyQueryService lobbyQueryService;

    @InjectMocks
    private LobbyViewQueryService underTest;

    @Mock
    private Lobby lobby;

    @Mock
    private LobbyEvent lobbyEvent;

    @Mock
    private LobbyEvent queriedLobbyEvent;

    @Mock
    private CharacterView characterView;

    @Before
    public void setUp() {
        given(lobbyQueryService.findByCharacterIdValidated(CHARACTER_ID)).willReturn(lobby);
        given(lobby.getEvents()).willReturn(Arrays.asList(lobbyEvent, queriedLobbyEvent));
        given(lobby.getGameMode()).willReturn(GameMode.TEAMFIGHT);
        given(lobby.getData()).willReturn(LOBBY_DATA);
        given(lobby.getOwnerId()).willReturn(OWNER_ID);
        given(lobbyEvent.getEventType()).willReturn(LobbyEventType.EXIT);
        given(lobbyEvent.getQueriedBy()).willReturn(Collections.emptyList());
        given(lobbyEvent.getData()).willReturn(CHARACTER_ID);
        given(queriedLobbyEvent.getQueriedBy()).willReturn(Arrays.asList(CHARACTER_ID));
    }

    @Test
    public void getLobbyView() {
        //WHEN
        LobbyView result = underTest.getLobbyView(CHARACTER_ID);
        //THEN
        verify(lobbyEvent).addQueriedBy(CHARACTER_ID);
        assertThat(result.getGameMode()).isEqualTo(GameMode.TEAMFIGHT);
        assertThat(result.getData()).isEqualTo(LOBBY_DATA);
        assertThat(result.getOwnerId()).isEqualTo(OWNER_ID);
    }

    @Test
    public void getEvents() {
        //GIVEN
        given(characterViewQueryService.findByCharacterId(CHARACTER_ID)).willReturn(characterView);
        //WHEN
        List<LobbyEventView> result = underTest.getEvents(CHARACTER_ID);
        //THEN
        verify(lobbyEvent).addQueriedBy(CHARACTER_ID);
        verify(queriedLobbyEvent).getQueriedBy();
        verifyNoMoreInteractions(queriedLobbyEvent);
        assertThat(result).hasSize(1);
        LobbyEventView lobbyEventView = result.get(0);
        assertThat(lobbyEventView.getEventType()).isEqualTo(LobbyEventType.EXIT);
        assertThat(lobbyEventView.getData()).isEqualTo(characterView);
    }
}