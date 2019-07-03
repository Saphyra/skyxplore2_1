package com.github.saphyra.skyxplore.lobby.lobby.creation;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.lobby.lobby.domain.GameMode;
import com.github.saphyra.skyxplore.lobby.lobby.domain.Lobby;

@RunWith(MockitoJUnitRunner.class)
public class TournamentLobbyFactoryTest extends AbstractLobbyFactoryTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String DATA = "data";
    private static final Integer LOBBY_SIZE = 6;

    @Mock
    private LobbyObjectFactory lobbyObjectFactory;

    @Mock
    private LobbyCreationConfiguration configuration;

    @InjectMocks
    private TournamentLobbyFactory underTest;

    @Mock
    private Lobby lobby;

    @Override
    protected Collection<GameMode> getProcessableGameModes() {
        return Arrays.asList(GameMode.TOURNAMENT);
    }

    @Override
    protected LobbyFactory getUnderTest() {
        return underTest;
    }

    @Test
    public void create() {
        //GIVEN
        given(configuration.getTournamentLobbySize()).willReturn(LOBBY_SIZE);
        given(lobbyObjectFactory.create(GameMode.TOURNAMENT, CHARACTER_ID, DATA, LOBBY_SIZE)).willReturn(lobby);
        //WHEN
        Lobby result = underTest.create(GameMode.TEAMFIGHT, CHARACTER_ID, DATA);
        //THEN
        assertThat(result).isEqualTo(lobby);
    }
}