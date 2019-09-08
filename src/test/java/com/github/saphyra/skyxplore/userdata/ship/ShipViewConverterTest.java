package com.github.saphyra.skyxplore.userdata.ship;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.data.gamedata.entity.Ship;
import com.github.saphyra.skyxplore.data.gamedata.subservice.ShipService;
import com.github.saphyra.skyxplore.userdata.ship.domain.EquippedShip;
import com.github.saphyra.skyxplore.userdata.ship.domain.ShipView;
import com.github.saphyra.skyxplore.userdata.ship.domain.SlotView;
import com.github.saphyra.skyxplore.userdata.slot.domain.EquippedSlot;

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

    @Mock
    private EquippedSlot defenseSlot;

    @Mock
    private EquippedSlot weaponSlot;

    @Mock
    private SlotView defenseSlotView;

    @Mock
    private SlotView weaponSlotView;

    @Mock
    private EquippedShip equippedShip;

    @Test
    public void testConvertShouldReturnView() {
        //GIVEN
        Ship ship = new Ship();
        ship.setAbility(Arrays.asList(ABILITY));

        when(slotViewConverter.convertDomain(defenseSlot)).thenReturn(defenseSlotView);
        when(slotViewConverter.convertDomain(weaponSlot)).thenReturn(weaponSlotView);
        when(shipService.get(SHIP_TYPE)).thenReturn(ship);


        given(equippedShip.getShipType()).willReturn(SHIP_TYPE);
        given(equippedShip.getCoreHull()).willReturn(COREHULL);
        given(equippedShip.getConnectorSlot()).willReturn(CONNECTOR_SLOT);
        given(equippedShip.getConnectorEquipped()).willReturn(Arrays.asList(CONNECTOR));
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
