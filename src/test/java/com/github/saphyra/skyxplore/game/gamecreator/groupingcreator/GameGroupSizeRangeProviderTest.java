package com.github.saphyra.skyxplore.game.gamecreator.groupingcreator;

import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroupSizeRange;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.GameMode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class GameGroupSizeRangeProviderTest {
    @InjectMocks
    private GameGroupSizeRangeProvider underTest;

    @Test
    public void getGameModeSizeRange_vs() {
        //WHEN
        GameGroupSizeRange result = underTest.getGameModeSizeRange(GameMode.VS);
        //THEN
        assertThat(result.getMinGroupSize()).isEqualTo(1);
        assertThat(result.getMaxGroupSize()).isEqualTo(1);
    }
}