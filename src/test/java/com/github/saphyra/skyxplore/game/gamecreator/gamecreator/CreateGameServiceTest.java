package com.github.saphyra.skyxplore.game.gamecreator.gamecreator;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.game.game.GameStorage;
import com.github.saphyra.skyxplore.game.game.domain.Game;
import com.github.saphyra.skyxplore.game.gamecreator.GameGroupingQueryService;
import com.github.saphyra.skyxplore.game.gamecreator.GameGroupingStorage;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGrouping;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.converter.GameGroupingToGameConverterStrategy;
import com.github.saphyra.skyxplore.game.lobby.lobby.LobbyStorage;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.GameMode;

@RunWith(MockitoJUnitRunner.class)
public class CreateGameServiceTest {
    private static final UUID GAME_ID = UUID.randomUUID();
    private static final UUID GAME_GROUPING_ID = UUID.randomUUID();
    private static final UUID LOBBY_ID = UUID.randomUUID();

    @Mock
    private GameGroupingQueryService gameGroupingQueryService;

    @Mock
    private GameGroupingStorage gameGroupingStorage;

    @Mock
    private GameStorage gameStorage;

    @Mock
    private GameGroupingToGameConverterStrategy converterStrategy;

    @Mock
    private LobbyStorage lobbyStorage;

    private CreateGameService underTest;

    @Mock
    private GameGrouping gameGrouping;

    @Mock
    private Game game;

    @Before
    public void setUp() {
        underTest = CreateGameService.builder()
            .gameGroupingQueryService(gameGroupingQueryService)
            .gameGroupingStorage(gameGroupingStorage)
            .gameStorage(gameStorage)
            .converterStrategies(Arrays.asList(converterStrategy))
            .lobbyStorage(lobbyStorage)
            .build();
    }

    @Test
    public void createGameFromGroupings() {
        //GIVEN
        given(gameGroupingQueryService.getGroupingsWithEnoughMembers()).willReturn(Arrays.asList(gameGrouping));
        given(gameGrouping.getGameMode()).willReturn(GameMode.TEAMFIGHT);
        given(converterStrategy.canConvert(GameMode.TEAMFIGHT)).willReturn(true);
        given(converterStrategy.convert(gameGrouping)).willReturn(game);
        given(game.getGameId()).willReturn(GAME_ID);
        given(gameGrouping.getGameGroupingId()).willReturn(GAME_GROUPING_ID);
        given(gameGrouping.getLockedLobbyIds()).willReturn(Arrays.asList(LOBBY_ID));
        //WHEN
        underTest.createGameFromGroupings();
        //THEN
        verify(gameStorage).put(GAME_ID, game);
        verify(gameGroupingStorage).remove(GAME_GROUPING_ID);
        verify(lobbyStorage).remove(LOBBY_ID);
    }
}