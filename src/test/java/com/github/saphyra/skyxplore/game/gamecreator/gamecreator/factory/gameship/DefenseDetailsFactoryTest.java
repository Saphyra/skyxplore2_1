package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.game.game.domain.ship.DefenseDetails;
import com.github.saphyra.skyxplore.game.game.domain.ship.DefenseSideDetails;
import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;

@RunWith(MockitoJUnitRunner.class)
public class DefenseDetailsFactoryTest {
    private static final String FRONT_DEFENSE = "front_defense";
    private static final String LEFT_DEFENSE = "left_defense";
    private static final String RIGHT_DEFENSE = "right_defense";
    private static final String BACK_DEFENSE = "back_defense";

    @Mock
    private DefenseSideDetailsFactory defenseSideDetailsFactory;

    @InjectMocks
    private DefenseDetailsFactory underTest;

    @Mock
    private ShipEquipments shipEquipments;

    @Mock
    private DefenseSideDetails frontDefenseSideDetails;

    @Mock
    private DefenseSideDetails leftDefenseSideDetails;

    @Mock
    private DefenseSideDetails rightDefenseSideDetails;

    @Mock
    private DefenseSideDetails backDefenseSideDetails;

    @Test
    public void create() {
        //GIVEN
        given(shipEquipments.getFrontDefense()).willReturn(Arrays.asList(FRONT_DEFENSE));
        given(shipEquipments.getLeftDefense()).willReturn(Arrays.asList(LEFT_DEFENSE));
        given(shipEquipments.getRightDefense()).willReturn(Arrays.asList(RIGHT_DEFENSE));
        given(shipEquipments.getBackDefense()).willReturn(Arrays.asList(BACK_DEFENSE));

        given(defenseSideDetailsFactory.create(Arrays.asList(FRONT_DEFENSE))).willReturn(frontDefenseSideDetails);
        given(defenseSideDetailsFactory.create(Arrays.asList(LEFT_DEFENSE))).willReturn(leftDefenseSideDetails);
        given(defenseSideDetailsFactory.create(Arrays.asList(RIGHT_DEFENSE))).willReturn(rightDefenseSideDetails);
        given(defenseSideDetailsFactory.create(Arrays.asList(BACK_DEFENSE))).willReturn(backDefenseSideDetails);
        //WHEN
        DefenseDetails result = underTest.create(shipEquipments);
        //THEN
        assertThat(result.getFrontDefense()).isEqualTo(frontDefenseSideDetails);
        assertThat(result.getLeftDefense()).isEqualTo(leftDefenseSideDetails);
        assertThat(result.getRightDefense()).isEqualTo(rightDefenseSideDetails);
        assertThat(result.getBackDefense()).isEqualTo(backDefenseSideDetails);
    }
}