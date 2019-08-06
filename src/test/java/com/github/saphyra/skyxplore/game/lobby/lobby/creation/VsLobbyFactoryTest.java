package com.github.saphyra.skyxplore.game.lobby.lobby.creation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.game.lobby.lobby.domain.GameMode;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.Lobby;

@RunWith(MockitoJUnitRunner.class)
public class VsLobbyFactoryTest extends AbstractLobbyFactoryTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String DATA = "data";

    @Mock
    private LobbyObjectFactory lobbyObjectFactory;

    @InjectMocks
    private VsLobbyFactory underTest;

    @Mock
    private Lobby lobby;

    @Override
    protected Collection<GameMode> getProcessableGameModes() {
        return Arrays.asList(GameMode.VS);
    }

    @Override
    protected LobbyFactory getUnderTest() {
        return underTest;
    }

    @Test
    public void create() {
        //GIVEN
        given(lobbyObjectFactory.create(GameMode.VS, CHARACTER_ID, DATA, 2)).willReturn(lobby);
        //WHEN
        Lobby result = underTest.create(GameMode.TOURNAMENT, CHARACTER_ID, DATA);
        //THEN
        assertThat(result).isEqualTo(lobby);
    }
}