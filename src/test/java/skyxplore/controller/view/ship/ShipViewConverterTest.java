package skyxplore.controller.view.ship;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.controller.view.slot.SlotView;
import skyxplore.controller.view.slot.SlotViewConverter;
import org.github.saphyra.skyxplore.gamedata.entity.Ship;
import org.github.saphyra.skyxplore.gamedata.subservice.ShipService;
import org.github.saphyra.skyxplore.slot.domain.EquippedSlot;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.DATA_ABILITY;
import static skyxplore.testutil.TestUtils.DATA_CONNECTOR;
import static skyxplore.testutil.TestUtils.DATA_SHIP_CONNECTOR_SLOT;
import static skyxplore.testutil.TestUtils.DATA_SHIP_COREHULL;
import static skyxplore.testutil.TestUtils.EQUIPPED_SHIP_TYPE;
import static skyxplore.testutil.TestUtils.createEquippedDefenseSlot;
import static skyxplore.testutil.TestUtils.createEquippedShip;
import static skyxplore.testutil.TestUtils.createEquippedWeaponSlot;
import static skyxplore.testutil.TestUtils.createShip;
import static skyxplore.testutil.TestUtils.createSlotView;

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
        assertEquals(EQUIPPED_SHIP_TYPE, result.getShipType());
        assertEquals(DATA_SHIP_COREHULL, result.getCoreHull());
        assertEquals(DATA_SHIP_CONNECTOR_SLOT, result.getConnectorSlot());
        assertEquals(1, result.getConnectorEquipped().size());
        assertEquals(DATA_CONNECTOR, result.getConnectorEquipped().get(0));
        assertEquals(1, result.getAbility().size());
        assertEquals(DATA_ABILITY, result.getAbility().get(0));
    }
}
