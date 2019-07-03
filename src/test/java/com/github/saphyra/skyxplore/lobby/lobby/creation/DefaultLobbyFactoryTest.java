package com.github.saphyra.skyxplore.lobby.lobby.creation;

import com.github.saphyra.skyxplore.lobby.lobby.domain.GameMode;
import com.github.saphyra.skyxplore.lobby.lobby.domain.Lobby;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collection;

import static com.github.saphyra.skyxplore.lobby.lobby.creation.DefaultLobbyFactory.MAX_LOBBY_SIZE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class DefaultLobbyFactoryTest extends AbstractLobbyFactoryTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String DATA = "data";

    @Mock
    private LobbyObjectFactory lobbyObjectFactory;

    @InjectMocks
    private DefaultLobbyFactory underTest;

    @Mock
    private Lobby lobby;

    @Override
    protected Collection<GameMode> getProcessableGameModes() {
        return Arrays.asList(GameMode.ARCADE, GameMode.BATTLE_ROYALE);
    }

    @Override
    protected LobbyFactory getUnderTest() {
        return underTest;
    }

    @Test(expected = IllegalArgumentException.class)
    public void create_wrongGameMode() {
        //WHEN
        underTest.create(GameMode.TEAMFIGHT, CHARACTER_ID, DATA);
    }

    @Test
    public void create() {
        //GIVEN
        given(lobbyObjectFactory.create(GameMode.ARCADE, CHARACTER_ID, DATA, MAX_LOBBY_SIZE)).willReturn(lobby);
        //WHEN
        Lobby result = underTest.create(GameMode.ARCADE, CHARACTER_ID, DATA);
        //THEN
        assertThat(result).isEqualTo(lobby);
    }
}