package skyxplore.service.ship;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.dataaccess.db.CharacterDao;
import skyxplore.dataaccess.db.EquippedShipDao;
import skyxplore.dataaccess.db.SlotDao;
import skyxplore.dataaccess.gamedata.entity.Ship;
import skyxplore.dataaccess.gamedata.entity.Slot;
import skyxplore.dataaccess.gamedata.subservice.ShipService;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.domain.ship.EquippedShip;
import skyxplore.domain.slot.EquippedSlot;
import skyxplore.exception.base.BadRequestException;
import skyxplore.service.character.CharacterQueryService;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static skyxplore.testutil.TestUtils.*;

@RunWith(MockitoJUnitRunner.class)
public class EquipShipServiceTest {
    @Mock
    private CharacterDao characterDao;

    @Mock
    private CharacterQueryService characterQueryService;

    @Mock
    private EquippedShipDao equippedShipDao;

    @Mock
    private ShipService shipService;

    @Mock
    private ShipQueryService shipQueryService;

    @Mock
    private SlotDao slotDao;

    @Mock
    private SkyXpCharacter character;

    @Mock
    private EquippedShip equippedShip;

    @Mock
    private EquippedSlot equippedSlot;

    @InjectMocks
    private EquipShipService underTest;

    @Before
    public void init(){
        when(characterQueryService.findByCharacterId(CHARACTER_ID_1)).thenReturn(character);
        when(shipQueryService.getShipByCharacterId(CHARACTER_ID_1)).thenReturn(equippedShip);
    }

    @Test(expected = BadRequestException.class)
    public void testEquipShipShouldThrowExceptionWhenInvalidItemId(){
        //GIVEN
        when(shipService.get(DATA_ELEMENT)).thenReturn(null);
        //WHEN
        underTest.equipShip(CHARACTER_ID_1, DATA_ELEMENT);
    }

    @Test
    public void testEquipShipShouldTEquip(){
        //GIVEN
        Ship ship = new Ship();
        ship.setId(EQUIPPED_SHIP_TYPE);
        ship.setConnector(DATA_SHIP_CONNECTOR_SLOT);

        Slot slot = new Slot();
        slot.setFront(DATA_SHIP_CONNECTOR_SLOT);
        slot.setSide(DATA_SHIP_CONNECTOR_SLOT);
        slot.setBack(DATA_SHIP_CONNECTOR_SLOT);
        ship.setDefense(slot);
        ship.setWeapon(slot);
        when(shipService.get(DATA_ELEMENT)).thenReturn(ship);

        when(equippedShip.getShipType()).thenReturn(DATA_NAME);
        when(equippedShip.getWeaponSlotId()).thenReturn(WEAPON_SLOT_ID);
        when(equippedShip.getDefenseSlotId()).thenReturn(DEFENSE_SLOT_ID);

        when(slotDao.getById(WEAPON_SLOT_ID)).thenReturn(equippedSlot);
        when(slotDao.getById(DEFENSE_SLOT_ID)).thenReturn(equippedSlot);
        when(equippedSlot.getFrontEquipped()).thenReturn(new ArrayList<>(Arrays.asList(DATA_ELEMENT)));
        when(equippedSlot.getLeftEquipped()).thenReturn(new ArrayList<>(Arrays.asList(DATA_ELEMENT)));
        when(equippedSlot.getRightEquipped()).thenReturn(new ArrayList<>(Arrays.asList(DATA_ELEMENT)));
        when(equippedSlot.getBackEquipped()).thenReturn(new ArrayList<>(Arrays.asList(DATA_ELEMENT)));

        when(equippedShip.getConnectorEquipped()).thenReturn(new ArrayList<>(Arrays.asList(DATA_CONNECTOR)));
        //WHEN
        underTest.equipShip(CHARACTER_ID_1, DATA_ELEMENT);
        //THEN
        verify(character).removeEquipment(DATA_ELEMENT);
        verify(character).addEquipment(DATA_NAME);
        verify(equippedShip).setShipType(EQUIPPED_SHIP_TYPE);
        verify(character).addEquipment(DATA_CONNECTOR);
        verify(equippedShip).removeConnector(DATA_CONNECTOR);
        verify(equippedShip).setConnectorSlot(DATA_SHIP_CONNECTOR_SLOT);

        verify(equippedShipDao).save(equippedShip);
        verify(characterDao).save(character);
        verify(character, times(8)).addEquipment(DATA_ELEMENT);
        verify(equippedSlot, times(2)).removeFront(DATA_ELEMENT);
        verify(equippedSlot, times(2)).removeLeft(DATA_ELEMENT);
        verify(equippedSlot, times(2)).removeRight(DATA_ELEMENT);
        verify(equippedSlot, times(2)).removeBack(DATA_ELEMENT);

        verify(equippedSlot, times(2)).setFrontSlot(DATA_SHIP_CONNECTOR_SLOT);
        verify(equippedSlot, times(2)).setBackSlot(DATA_SHIP_CONNECTOR_SLOT);
        verify(equippedSlot, times(2)).setLeftSlot(DATA_SHIP_CONNECTOR_SLOT);
        verify(equippedSlot, times(2)).setRightSlot(DATA_SHIP_CONNECTOR_SLOT);
    }
}