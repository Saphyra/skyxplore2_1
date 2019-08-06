package com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.fill.fromlobby;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.game.gamecreator.GameGroupingStorage;
import com.github.saphyra.skyxplore.game.lobby.lobby.LobbyQueryService;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.GameMode;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.Lobby;

@RunWith(MockitoJUnitRunner.class)
public class FillFromLobbyServiceTest {
    @Mock
    private LobbyQueryService lobbyQueryService;

    @Mock
    private GameGroupingStorage gameGroupingStorage;

    @Mock
    private AddToGroupingStrategy addToGroupingStrategy;

    private FillFromLobbyService underTest;

    @Mock
    private Lobby lobby;

    @Before
    public void setUp() {
        underTest = FillFromLobbyService.builder()
            .addToGroupingStrategies(Arrays.asList(addToGroupingStrategy))
            .gameGroupingStorage(gameGroupingStorage)
            .lobbyQueryService(lobbyQueryService)
            .build();
    }

    @Test
    public void fillGroupingsWithLobbies() {
        //GIVEN
        given(lobbyQueryService.getLobbiesInQueue()).willReturn(Arrays.asList(lobby));
        given(lobby.getGameMode()).willReturn(GameMode.TEAMFIGHT);
        given(addToGroupingStrategy.canAdd(GameMode.TEAMFIGHT)).willReturn(true);
        //WHEN
        underTest.fillGroupingsWithLobbies();
        //THEN
        verify(addToGroupingStrategy).add(lobby);
    }
}