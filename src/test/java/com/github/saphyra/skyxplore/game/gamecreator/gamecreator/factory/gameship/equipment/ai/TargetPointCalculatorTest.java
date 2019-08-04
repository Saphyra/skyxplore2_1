package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai;

import com.github.saphyra.skyxplore.game.game.domain.ship.GameShip;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.domain.PointRange;
import com.github.saphyra.util.Random;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class TargetPointCalculatorTest {
    private static final int MIN_POINTS = 1;
    private static final int MAX_POINTS = 3;
    private static final Integer TARGET_POINTS = 2;
    @Mock
    private PointRangeCalculator pointRangeCalculator;

    @Mock
    private Random random;

    @InjectMocks
    private TargetPointCalculator underTest;

    @Mock
    private GameShip gameShip;

    @Test
    public void getTargetPoints() {
        //GIVEN
        PointRange pointRange = new PointRange(MIN_POINTS, MAX_POINTS);
        given(pointRangeCalculator.calculatePointRange(Arrays.asList(gameShip))).willReturn(pointRange);
        given(random.randInt(MIN_POINTS, MAX_POINTS)).willReturn(TARGET_POINTS);
        //WHEN
        int result = underTest.getTargetPoints(Arrays.asList(gameShip));
        //THEN
        assertThat(result).isEqualTo(TARGET_POINTS);
    }
}