package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai;

import com.github.saphyra.skyxplore.data.GameDataFacade;
import com.github.saphyra.skyxplore.data.entity.EquipmentDescription;
import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class PointCalculatorTest {
    private static final String ITEM_ID = "item_id";
    @Mock
    private GameDataFacade gameDataFacade;

    @InjectMocks
    private PointCalculator underTest;

    @Mock
    private EquipmentDescription equipmentDescription;

    @Test
    public void countPointsOfEquipments() {
        //GIVEN
        given(gameDataFacade.findEquipmentDescription(ITEM_ID)).willReturn(equipmentDescription);
        given(equipmentDescription.getScore()).willReturn(1);

        ShipEquipments shipEquipments = ShipEquipments.builder()
            .shipId(ITEM_ID)
            .frontDefense(Arrays.asList(ITEM_ID))
            .leftDefense(Arrays.asList(ITEM_ID))
            .rightDefense(Arrays.asList(ITEM_ID))
            .backDefense(Arrays.asList(ITEM_ID))
            .frontWeapon(Arrays.asList(ITEM_ID))
            .leftWeapon(Arrays.asList(ITEM_ID))
            .rightWeapon(Arrays.asList(ITEM_ID))
            .backWeapon(Arrays.asList(ITEM_ID))
            .build();
        //WHEN
        Integer result = underTest.countPointsOfEquipments(shipEquipments);
        //THEN
        assertThat(result).isEqualTo(9);
    }
}