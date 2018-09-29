package skyxplore.service.character;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.dataaccess.gamedata.entity.Ship;
import skyxplore.dataaccess.gamedata.entity.Slot;
import skyxplore.dataaccess.gamedata.subservice.ShipService;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.domain.ship.EquippedShip;
import skyxplore.domain.slot.EquippedSlot;
import skyxplore.util.IdGenerator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.service.character.NewCharacterGenerator.*;
import static skyxplore.testutil.TestUtils.*;

@RunWith(MockitoJUnitRunner.class)
public class NewCharacterGeneratorTest {
    @Mock
    private IdGenerator idGenerator;

    @Mock
    private ShipService shipService;

    @InjectMocks
    private NewCharacterGenerator underTest;

    @Test
    public void testCreateCharacterShouldCreate() {
        //GIVEN
        when(idGenerator.getRandomId()).thenReturn(CHARACTER_ID_1);
        //WHEN
        SkyXpCharacter result = underTest.createCharacter(USER_ID, CHARACTER_NAME);
        //THEN
        verify(idGenerator).getRandomId();
        assertEquals(CHARACTER_ID_1, result.getCharacterId());
        assertEquals(CHARACTER_NAME, result.getCharacterName());
        assertEquals(USER_ID, result.getUserId());
        assertEquals(START_MONEY, result.getMoney());
        assertEquals(CHARACTER_ID_1, result.getCharacterId());
    }

    @Test
    public void testCreateShip() {
        //GIVEN
        when(idGenerator.getRandomId()).thenReturn(EQUIPPED_SHIP_ID);

        Ship ship = createShip();
        ship.setConnector(6);
        when(shipService.get(STARTER_SHIP_ID)).thenReturn(ship);
        //WHEN
        EquippedShip result = underTest.createShip(CHARACTER_ID_1);
        //THEN
        verify(idGenerator).getRandomId();
        verify(shipService).get(STARTER_SHIP_ID);
        assertEquals(EQUIPPED_SHIP_ID, result.getShipId());
        assertEquals(CHARACTER_ID_1, result.getCharacterId());
        assertEquals(STARTER_SHIP_ID, result.getShipType());
        assertEquals((Integer) 6, result.getConnectorSlot());
        assertEquals(DATA_SHIP_COREHULL, result.getCoreHull());
        assertTrue(result.getConnectorEquipped().contains(GENERATOR_ID));
        assertTrue(result.getConnectorEquipped().contains(BATTERY_ID));
        assertTrue(result.getConnectorEquipped().contains(STORAGE_ID));
    }

    @Test
    public void testCreateDefenseSlot() {
        //GIVEN
        Ship ship = createShip();
        Slot defenseSlot = new Slot();
        defenseSlot.setFront(4);
        defenseSlot.setSide(2);
        defenseSlot.setBack(2);
        ship.setDefense(defenseSlot);
        when(shipService.get(STARTER_SHIP_ID)).thenReturn(ship);
        when(idGenerator.getRandomId()).thenReturn(DEFENSE_SLOT_ID);
        //WHEN
        EquippedSlot result = underTest.createDefenseSlot(EQUIPPED_SHIP_ID);
        //THEN
        verify(idGenerator).getRandomId();
        verify(shipService).get(STARTER_SHIP_ID);

        assertEquals((Integer) 4, result.getFrontSlot());
        assertEquals((Integer) 2, result.getLeftSlot());
        assertEquals((Integer) 2, result.getRightSlot());
        assertEquals((Integer) 2, result.getBackSlot());

        assertTrue(result.getFrontEquipped().contains(SHIELD_ID));
        assertTrue(result.getFrontEquipped().contains(ARMOR_ID));

        assertTrue(result.getLeftEquipped().contains(SHIELD_ID));
        assertTrue(result.getLeftEquipped().contains(ARMOR_ID));

        assertTrue(result.getRightEquipped().contains(SHIELD_ID));
        assertTrue(result.getRightEquipped().contains(ARMOR_ID));

        assertTrue(result.getBackEquipped().contains(SHIELD_ID));
        assertTrue(result.getBackEquipped().contains(ARMOR_ID));
    }

    @Test
    public void testCreateWeaponSlot() {
        //GIVEN
        Ship ship = createShip();
        Slot weaponSlot = new Slot();
        weaponSlot.setFront(3);
        weaponSlot.setSide(1);
        weaponSlot.setBack(1);
        ship.setWeapon(weaponSlot);
        when(shipService.get(STARTER_SHIP_ID)).thenReturn(ship);
        when(idGenerator.getRandomId()).thenReturn(WEAPON_SLOT_ID);
        //WHEN
        EquippedSlot result = underTest.createWeaponSlot(EQUIPPED_SHIP_ID);
        //THEN
        verify(idGenerator).getRandomId();
        verify(shipService).get(STARTER_SHIP_ID);

        assertEquals((Integer) 3, result.getFrontSlot());
        assertEquals((Integer) 1, result.getLeftSlot());
        assertEquals((Integer) 1, result.getRightSlot());
        assertEquals((Integer) 1, result.getBackSlot());

        assertTrue(result.getFrontEquipped().contains(LASER_ID));
        assertTrue(result.getFrontEquipped().contains(LAUNCHER_ID));

        assertTrue(result.getLeftEquipped().contains(RIFLE_ID));
        assertTrue(result.getRightEquipped().contains(RIFLE_ID));
        assertTrue(result.getBackEquipped().contains(RIFLE_ID));
    }
}