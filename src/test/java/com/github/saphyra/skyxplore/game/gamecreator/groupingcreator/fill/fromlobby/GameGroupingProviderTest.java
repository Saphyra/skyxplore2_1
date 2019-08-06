package com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.fill.fromlobby;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.game.gamecreator.GameGroupingStorage;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroup;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroupSizeRange;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGrouping;
import com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.GameGroupFactory;
import com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.GameGroupSizeRangeProvider;
import com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.GameGroupingFactory;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.GameMode;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.Lobby;

@RunWith(MockitoJUnitRunner.class)
public class GameGroupingProviderTest {
    private static final int MAX_GROUP_SIZE = 245;
    private static final int EXPECTED_GAME_MEMBERS_AMOUNT = 3265;
    private static final UUID GAME_GROUPING_ID = UUID.randomUUID();

    @Mock
    private GameGroupingStorage gameGroupingStorage;

    @Mock
    private GameGroupFactory gameGroupFactory;

    @Mock
    private GameGroupingFactory gameGroupingFactory;

    @Mock
    private GameGroupSizeRangeProvider gameGroupSizeRangeProvider;

    @InjectMocks
    private GameGroupingCreator underTest;

    @Mock
    private Lobby lobby;

    @Mock
    private GameGroupSizeRange gameGroupSizeRange;

    @Mock
    private GameGroup gameGroup;

    @Mock
    private GameGrouping gameGrouping;

    @Test
    public void createGrouping() {
        //GIVEN
        given(lobby.getGameMode()).willReturn(GameMode.TEAMFIGHT);
        given(gameGroupSizeRangeProvider.getGameModeSizeRange(GameMode.TEAMFIGHT)).willReturn(gameGroupSizeRange);
        given(gameGroupFactory.createGroups(lobby, MAX_GROUP_SIZE)).willReturn(Arrays.asList(gameGroup));
        given(gameGroupingFactory.create(lobby, Arrays.asList(gameGroup), EXPECTED_GAME_MEMBERS_AMOUNT, gameGroupSizeRange)).willReturn(gameGrouping);
        given(gameGrouping.getGameGroupingId()).willReturn(GAME_GROUPING_ID);
        //WHEN
        underTest.createGrouping(lobby, MAX_GROUP_SIZE, EXPECTED_GAME_MEMBERS_AMOUNT);
        //THEN
        verify(gameGroupingStorage).put(GAME_GROUPING_ID, gameGrouping);
    }
}