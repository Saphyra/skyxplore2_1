package com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.fill.fromlobby;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGrouping;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.GameMode;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.Lobby;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.LobbyMember;

@RunWith(MockitoJUnitRunner.class)
public class DefaultAddToGroupingStrategyTest {
    @Mock
    private AddToExistingGroupingService addToExistingGroupingService;

    @Mock
    private AvailableGroupingProvider availableGroupingProvider;

    @Mock
    private GameGroupingCreator gameGroupingCreator;

    @InjectMocks
    private DefaultAddToGroupingStrategy underTest;

    @Mock
    private Lobby lobby;

    @Mock
    private LobbyMember lobbyMember;

    @Mock
    private GameGrouping gameGrouping;

    @Test
    public void add_toAvailable() {
        //GIVEN
        given(lobby.getGameMode()).willReturn(GameMode.TEAMFIGHT);
        given(lobby.getMembers()).willReturn(Arrays.asList(lobbyMember));
        given(availableGroupingProvider.getAvailableGrouping(GameMode.TEAMFIGHT, 1, 2)).willReturn(Optional.of(gameGrouping));
        //WHEN
        underTest.add(lobby);
        //THEN
        verify(addToExistingGroupingService).addToExistingGrouping(lobby, gameGrouping, 1);
        verifyZeroInteractions(gameGroupingCreator);
    }

    @Test
    public void add_createNew() {
        //GIVEN
        given(lobby.getGameMode()).willReturn(GameMode.TEAMFIGHT);
        given(lobby.getMembers()).willReturn(Arrays.asList(lobbyMember));
        given(availableGroupingProvider.getAvailableGrouping(GameMode.TEAMFIGHT, 1, 2)).willReturn(Optional.empty());
        //WHEN
        underTest.add(lobby);
        //THEN
        verify(gameGroupingCreator).createGrouping(lobby, 1, 2);
        verifyZeroInteractions(addToExistingGroupingService);
    }
}