package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.game.game.domain.TeamColor;
import com.github.saphyra.util.Random;

@RunWith(MockitoJUnitRunner.class)
public class TeamColorPickerTest {
    @Mock
    private Random random;

    private TeamColorPicker underTest;

    @Before
    public void setUp() {
        underTest = TeamColorPicker.builder()
            .random(random)
            .teamColors(Arrays.asList(TeamColor.BLUE, TeamColor.RED))
            .build();
    }

    @Test
    public void getRandomColor() {
        //GIVEN
        given(random.randInt(0, 0)).willReturn(0);
        //WHEN
        TeamColor result = underTest.getRandomColor(Arrays.asList(TeamColor.RED));
        //THEN
        assertThat(result).isEqualTo(TeamColor.BLUE);
    }
}