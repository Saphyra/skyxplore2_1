package skyxplore.service.ship;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.controller.view.ship.ShipView;
import skyxplore.controller.view.ship.ShipViewConverter;
import skyxplore.dataaccess.db.EquippedShipDao;
import skyxplore.dataaccess.db.SlotDao;
import skyxplore.domain.ship.EquippedShip;
import skyxplore.domain.slot.EquippedSlot;
import skyxplore.exception.ShipNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.CHARACTER_ID_1;
import static skyxplore.testutil.TestUtils.DEFENSE_SLOT_ID;
import static skyxplore.testutil.TestUtils.EQUIPPED_SHIP_ID;
import static skyxplore.testutil.TestUtils.WEAPON_SLOT_ID;
import static skyxplore.testutil.TestUtils.createEquippedDefenseSlot;
import static skyxplore.testutil.TestUtils.createEquippedShip;
import static skyxplore.testutil.TestUtils.createEquippedWeaponSlot;

@RunWith(MockitoJUnitRunner.class)
public class ShipQueryServiceTest {
    @Mock
    private EquippedShipDao equippedShipDao;

    @Mock
    private ShipViewConverter shipViewConverter;

    @Mock
    private SlotDao slotDao;

    @InjectMocks
    private ShipQueryService underTest;

    @Test(expected = ShipNotFoundException.class)
    public void testGetShipByCharacterIdShouldThrowExceptionWhenNull() {
        //GIVEN
        when(equippedShipDao.getShipByCharacterId(CHARACTER_ID_1)).thenReturn(null);
        //WHEN
        underTest.getShipByCharacterId(CHARACTER_ID_1);
    }

    @Test
    public void testGetShipByCharacterIdShouldQueryAndReturn() {
        //GIVEN
        EquippedShip ship = createEquippedShip();
        when(equippedShipDao.getShipByCharacterId(CHARACTER_ID_1)).thenReturn(ship);
        //WHEN
        EquippedShip result = underTest.getShipByCharacterId(CHARACTER_ID_1);
        //THEN
        verify(equippedShipDao).getShipByCharacterId(CHARACTER_ID_1);
        assertEquals(ship, result);
    }

    @Test
    public void testGetShipDataShouldReturn(){
        //GIVEN
        EquippedShip ship = createEquippedShip();
        when(equippedShipDao.getShipByCharacterId(CHARACTER_ID_1)).thenReturn(ship);

        EquippedSlot defenseSlot = createEquippedDefenseSlot();
        EquippedSlot weaponSlot = createEquippedWeaponSlot();
        when(slotDao.getById(DEFENSE_SLOT_ID)).thenReturn(defenseSlot);
        when(slotDao.getById(WEAPON_SLOT_ID)).thenReturn(weaponSlot);

        ShipView shipView = new ShipView();
        shipView.setShipId(EQUIPPED_SHIP_ID);
        when(shipViewConverter.convertDomain(ship, defenseSlot, weaponSlot)).thenReturn(shipView);

        //WHEN
        ShipView result = underTest.getShipData(CHARACTER_ID_1);
        //THEN
        verify(slotDao).getById(DEFENSE_SLOT_ID);
        verify(slotDao).getById(WEAPON_SLOT_ID);
        verify(shipViewConverter).convertDomain(ship, defenseSlot, weaponSlot);
        assertEquals(shipView, result);
    }
}