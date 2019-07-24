package com.github.saphyra.skyxplore.game.gamecreator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGrouping;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.GameMode;

@RunWith(MockitoJUnitRunner.class)
public class GameGroupingQueryServiceTest {
    @Mock
    private GameGroupingStorage gameGroupingStorage;

    @InjectMocks
    private GameGroupingQueryService underTest;

    @Mock
    private GameGrouping gameGrouping1;

    @Mock
    private GameGrouping gameGrouping2;

    @Test
    public void getGroupingsByGameMode() {
        //GIVEN
        given(gameGroupingStorage.values()).willReturn(Arrays.asList(gameGrouping1, gameGrouping2));
        given(gameGrouping1.getGameMode()).willReturn(GameMode.TEAMFIGHT);
        given(gameGrouping2.getGameMode()).willReturn(GameMode.VS);
        //WHEN
        List<GameGrouping> result = underTest.getGroupingsByGameMode(GameMode.VS);
        //THEN
        assertThat(result).containsExactly(gameGrouping2);
    }
}