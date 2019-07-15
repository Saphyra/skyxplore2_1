package com.github.saphyra.skyxplore.lobby.lobby.creation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.skyxplore.lobby.lobby.domain.ClanWarsType;
import com.github.saphyra.skyxplore.lobby.lobby.domain.GameMode;
import com.github.saphyra.skyxplore.lobby.lobby.domain.Lobby;

@RunWith(MockitoJUnitRunner.class)
public class ClanWarsLobbyFactoryTest extends AbstractLobbyFactoryTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String WRONG_DATA = "wrong_data";
    private static final String DATA = ClanWarsType.DREADNOUGHT.name();
    private static final Integer LOBBY_SIZE = 34;

    @Mock
    private LobbyCreationConfiguration configuration;

    @Mock
    private LobbyObjectFactory lobbyObjectFactory;

    @InjectMocks
    private ClanWarsLobbyFactory underTest;

    @Mock
    private Lobby lobby;

    @Override
    protected Collection<GameMode> getProcessableGameModes() {
        return Arrays.asList(GameMode.CLAN_WARS);
    }

    @Override
    protected LobbyFactory getUnderTest() {
        return underTest;
    }

    @Test
    public void create_wrongData() {
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.create(GameMode.CLAN_WARS, CHARACTER_ID, WRONG_DATA));
        //THEN
        assertThat(ex).isInstanceOf(BadRequestException.class);
    }

    @Test
    public void create() {
        //GIVEN
        given(configuration.getClanWarsLobbySize()).willReturn(LOBBY_SIZE);
        given(lobbyObjectFactory.create(GameMode.CLAN_WARS, CHARACTER_ID, DATA, LOBBY_SIZE)).willReturn(lobby);
        //WHEN
        Lobby result = underTest.create(GameMode.TOURNAMENT, CHARACTER_ID, DATA);
        //THEN
        assertThat(result).isEqualTo(lobby);
    }
}