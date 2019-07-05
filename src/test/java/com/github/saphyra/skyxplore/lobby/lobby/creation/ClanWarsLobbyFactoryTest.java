package com.github.saphyra.skyxplore.lobby.lobby.creation;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.skyxplore.lobby.lobby.domain.ClanWarsType;
import com.github.saphyra.skyxplore.lobby.lobby.domain.GameMode;
import com.github.saphyra.skyxplore.lobby.lobby.domain.Lobby;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collection;

import static com.github.saphyra.skyxplore.lobby.lobby.creation.ClanWarsLobbyFactory.MAX_LOBBY_SIZE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ClanWarsLobbyFactoryTest extends AbstractLobbyFactoryTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String WRONG_DATA = "wrong_data";
    private static final String DATA = ClanWarsType.DREADNOUGHT.name();

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
        given(lobbyObjectFactory.create(GameMode.CLAN_WARS, CHARACTER_ID, DATA, MAX_LOBBY_SIZE)).willReturn(lobby);
        //WHEN
        Lobby result = underTest.create(GameMode.TOURNAMENT, CHARACTER_ID, DATA);
        //THEN
        assertThat(result).isEqualTo(lobby);
    }
}