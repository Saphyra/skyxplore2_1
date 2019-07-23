package com.github.saphyra.skyxplore.game.lobby.lobby.creation;

import com.github.saphyra.skyxplore.game.lobby.lobby.LobbyContext;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.GameMode;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.Lobby;
import com.github.saphyra.util.IdGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class LobbyObjectFactoryTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String DATA = "data";
    private static final int TEAM_SIZE = 5;
    private static final UUID LOBBY_ID = UUID.randomUUID();

    @Mock
    private IdGenerator idGenerator;

    @Mock
    private LobbyContext lobbyContext;

    @InjectMocks
    private LobbyObjectFactory underTest;

    @Test
    public void create(){
        //GIVEN
        given(idGenerator.randomUUID()).willReturn(LOBBY_ID);
        //WHEN
        Lobby lobby = underTest.create(GameMode.ARCADE, CHARACTER_ID, DATA, TEAM_SIZE);
        //THEN
        assertThat(lobby.getLobbyId()).isEqualTo(LOBBY_ID);
        assertThat(lobby.getGameMode()).isEqualTo(GameMode.ARCADE);
        assertThat(lobby.getMembers()).hasSize(1);
        assertThat(lobby.getMembers().get(0).getCharacterId()).isEqualTo(CHARACTER_ID);
        assertThat(lobby.getMembers().get(0).isReady()).isFalse();
        assertThat(lobby.getData()).isEqualTo(DATA);
        assertThat(lobby.getOwnerId()).isEqualTo(CHARACTER_ID);
        assertThat(lobby.getLobbyContext()).isEqualTo(lobbyContext);
        assertThat(lobby.getEvents()).isNotNull();
        assertThat(lobby.getMessages()).isNotNull();
        assertThat(lobby.isInQueue()).isFalse();
        assertThat(lobby.isAutoFill()).isTrue();
    }
}