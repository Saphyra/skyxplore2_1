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

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.GameMode;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.Lobby;

@RunWith(MockitoJUnitRunner.class)
public class TeamfightLobbyFactoryTest extends AbstractLobbyFactoryTest {
    private static final String CHARACTER_ID = "character_id";

    @Mock
    private LobbyObjectFactory lobbyObjectFactory;

    @InjectMocks
    private TeamfightLobbyFactory underTest;

    @Mock
    private Lobby lobby;

    @Override
    protected Collection<GameMode> getProcessableGameModes() {
        return Arrays.asList(GameMode.TEAMFIGHT);
    }

    @Override
    protected LobbyFactory getUnderTest() {
        return underTest;
    }

    @Test(expected = BadRequestException.class)
    public void create_wrongNumberFormat() {
        underTest.create(null, CHARACTER_ID, "asd");
    }

    @Test(expected = BadRequestException.class)
    public void create_wrongTeamSize() {
        underTest.create(null, CHARACTER_ID, String.valueOf(7));
    }

    @Test
    public void create() {
        //GIVEN
        given(lobbyObjectFactory.create(GameMode.TEAMFIGHT, CHARACTER_ID, String.valueOf(5), 5)).willReturn(lobby);
        //WHEN
        Lobby result = underTest.create(GameMode.CLAN_WARS, CHARACTER_ID, String.valueOf(5));
        //THEN
        assertThat(result).isEqualTo(lobby);
    }
}