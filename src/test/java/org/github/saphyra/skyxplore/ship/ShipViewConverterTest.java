package org.github.saphyra.skyxplore.ship;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.github.saphyra.skyxplore.gamedata.entity.Ship;
import org.github.saphyra.skyxplore.gamedata.subservice.ShipService;
import org.github.saphyra.skyxplore.ship.domain.EquippedShip;
import org.github.saphyra.skyxplore.ship.domain.ShipView;
import org.github.saphyra.skyxplore.ship.domain.SlotView;
import org.github.saphyra.skyxplore.slot.domain.EquippedSlot;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ShipViewConverterTest {
    private static final String SHIP_TYPE = "ship_type";
    private static final int COREHULL = 16684;
    private static final int CONNECTOR_SLOT = 54;
    private static final String CONNECTOR = "connector";
    private static final String ABILITY = "ability";
    @Mock
    private SlotViewConverter slotViewConverter;

    @Mock
    private ShipService shipService;

    @InjectMocks
    private ShipViewConverter underTest;

    @Test
    public void testConvertShouldReturnView() {
        //GIVEN
        EquippedSlot defenseSlot = EquippedSlot.builder().build();
        EquippedSlot weaponSlot = EquippedSlot.builder().build();

        SlotView defenseSlotView = SlotView.builder().build();
        SlotView weaponSlotView = SlotView.builder().build();

        Ship ship = new Ship();
        ship.setAbility(Arrays.asList(ABILITY));

        when(slotViewConverter.convertDomain(defenseSlot)).thenReturn(defenseSlotView);
        when(slotViewConverter.convertDomain(weaponSlot)).thenReturn(weaponSlotView);
        when(shipService.get(SHIP_TYPE)).thenReturn(ship);

        EquippedShip equippedShip = EquippedShip.builder()
            .shipType(SHIP_TYPE)
            .coreHull(COREHULL)
            .connectorSlot(CONNECTOR_SLOT)
            .connectorEquipped(Arrays.asList(CONNECTOR))
            .build();
        //WHEN
        ShipView result = underTest.convertDomain(equippedShip, defenseSlot, weaponSlot);
        //THEN
        assertThat(result.getShipType()).isEqualTo(SHIP_TYPE);
        assertThat(result.getCoreHull()).isEqualTo(COREHULL);
        assertThat(result.getConnectorSlot()).isEqualTo(CONNECTOR_SLOT);
        assertThat(result.getConnectorEquipped()).containsExactly(CONNECTOR);
        assertThat(result.getAbility()).containsExactly(ABILITY);
    }
}
