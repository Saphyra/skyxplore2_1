package com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.fill.fromlobby;

import com.github.saphyra.skyxplore.game.gamecreator.GameGroupingQueryService;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroup;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGrouping;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.GameMode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class AvailableGroupingProviderTest {
    private static final int LOBBY_SIZE = 1;
    private static final int EXPECTED_GAME_MEMBERS_AMOUNT = 2;

    @Mock
    private GameGroupingQueryService gameGroupingQueryService;

    @InjectMocks
    private AvailableGroupingProvider underTest;

    @Mock
    private GameGrouping gameGrouping;

    @Mock
    private GameGroup gameGroup;

    @Test
    public void getAvailableGrouping_found() {
        //GIVEN
        given(gameGroupingQueryService.getGroupingsByGameMode(GameMode.ARCADE)).willReturn(Arrays.asList(gameGrouping));
        given(gameGrouping.getGameGroups()).willReturn(Arrays.asList(gameGroup));
        //WHEN
        Optional<GameGrouping> result = underTest.getAvailableGrouping(GameMode.ARCADE, LOBBY_SIZE, EXPECTED_GAME_MEMBERS_AMOUNT);
        //THEN
        assertThat(result).contains(gameGrouping);
    }

    @Test
    public void getAvailableGrouping_notFound() {
        //GIVEN
        given(gameGroupingQueryService.getGroupingsByGameMode(GameMode.ARCADE)).willReturn(Arrays.asList(gameGrouping));
        given(gameGrouping.getGameGroups()).willReturn(Arrays.asList(gameGroup, gameGroup));
        //WHEN
        Optional<GameGrouping> result = underTest.getAvailableGrouping(GameMode.ARCADE, LOBBY_SIZE, EXPECTED_GAME_MEMBERS_AMOUNT);
        //THEN
        assertThat(result).isEmpty();
    }

}