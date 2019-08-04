package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai;

import com.github.saphyra.skyxplore.game.game.domain.ship.GameShip;
import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.domain.PointRange;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class PointRangeCalculatorTest {
    private static final Integer POINT_1 = 5;
    private static final Integer POINT_2 = 15;
    private static final Double MIN_MULTIPLIER = 2D;
    private static final Double MAX_MULTIPLIER = 3D;

    @Mock
    private PointCalculator pointCalculator;

    @Mock
    private PointRangeConfiguration configuration;

    @InjectMocks
    private PointRangeCalculator underTest;

    @Mock
    private GameShip gameShip;

    @Mock
    private ShipEquipments shipEquipments1;

    @Mock
    private ShipEquipments shipEquipments2;

    @Test
    public void calculatePointRange() {
        //GIVEN
        given(gameShip.getShipEquipments())
            .willReturn(shipEquipments1)
            .willReturn(shipEquipments2);

        given(pointCalculator.countPointsOfEquipments(shipEquipments1)).willReturn(POINT_1);
        given(pointCalculator.countPointsOfEquipments(shipEquipments2)).willReturn(POINT_2);

        given(configuration.getMinMultiplier()).willReturn(MIN_MULTIPLIER);
        given(configuration.getMaxMultiplier()).willReturn(MAX_MULTIPLIER);
        //WHEN
        PointRange result = underTest.calculatePointRange(Arrays.asList(gameShip, gameShip));
        //THEN
        assertThat(result.getMinPoints()).isEqualTo((int) (POINT_1 * MIN_MULTIPLIER));
        assertThat(result.getMaxPoints()).isEqualTo((int) (POINT_2 * MAX_MULTIPLIER));
    }
}