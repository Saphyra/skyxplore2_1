package skyxplore.controller.view.ship;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static testutil.TestUtils.CHARACTER_ID;
import static testutil.TestUtils.DATA_ABILITY;
import static testutil.TestUtils.DATA_CONNECTOR;
import static testutil.TestUtils.DEFENSE_SLOT_ID;
import static testutil.TestUtils.DARA_SHIP_CONNECTOR_SLOT;
import static testutil.TestUtils.DATA_SHIP_COREHULL;
import static testutil.TestUtils.EQUIPPED_SHIP_ID;
import static testutil.TestUtils.EQUIPPED_SHIP_TYPE;
import static testutil.TestUtils.WEAPON_SLOT_ID;
import static testutil.TestUtils.createEquippedDefenseSlot;
import static testutil.TestUtils.createEquippedShip;
import static testutil.TestUtils.createEquippedWeaponSlot;
import static testutil.TestUtils.createShip;
import static testutil.TestUtils.createSlotView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import skyxplore.controller.view.slot.SlotView;
import skyxplore.controller.view.slot.SlotViewConverter;
import skyxplore.dataaccess.gamedata.entity.Ship;
import skyxplore.dataaccess.gamedata.subservice.ShipService;
import skyxplore.domain.slot.EquippedSlot;

@RunWith(MockitoJUnitRunner.class)
public class ShipViewConverterTest {
    @Mock
    private SlotViewConverter slotViewConverter;

    @Mock
    private ShipService shipService;

    @InjectMocks
    private ShipViewConverter underTest;

    @Test
    public void testConvertShouldReturnView() {
        //GIVEN
        EquippedSlot defenseSlot = createEquippedDefenseSlot();
        EquippedSlot weaponSlot = createEquippedWeaponSlot();

        SlotView defenseSlotView = createSlotView(defenseSlot);
        SlotView weaponSlotView = createSlotView(weaponSlot);

        Ship ship = createShip();

        when(slotViewConverter.convertDomain(defenseSlot)).thenReturn(defenseSlotView);
        when(slotViewConverter.convertDomain(weaponSlot)).thenReturn(weaponSlotView);
        when(shipService.get(EQUIPPED_SHIP_TYPE)).thenReturn(ship);
        //WHEN
        ShipView result = underTest.convertDomain(
            createEquippedShip(),
            defenseSlot,
            weaponSlot
        );
        //THEN
        assertEquals(EQUIPPED_SHIP_ID, result.getShipId());
        assertEquals(CHARACTER_ID, result.getCharacterId());
        assertEquals(EQUIPPED_SHIP_TYPE, result.getShipType());
        assertEquals(DATA_SHIP_COREHULL, result.getCoreHull());
        assertEquals(DARA_SHIP_CONNECTOR_SLOT, result.getConnectorSlot());
        assertEquals(1, result.getConnectorEquipped().size());
        assertEquals(DATA_CONNECTOR, result.getConnectorEquipped().get(0));
        assertEquals(DEFENSE_SLOT_ID, result.getDefenseSlot().getSlotId());
        assertEquals(WEAPON_SLOT_ID, result.getWeaponSlot().getSlotId());
        assertEquals(1, result.getAbility().size());
        assertEquals(DATA_ABILITY, result.getAbility().get(0));
    }
}
