package org.github.saphyra.skyxplore.ship;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;

import org.github.saphyra.skyxplore.character.CharacterQueryService;
import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import org.github.saphyra.skyxplore.character.repository.CharacterDao;
import org.github.saphyra.skyxplore.gamedata.entity.Ship;
import org.github.saphyra.skyxplore.gamedata.entity.Slot;
import org.github.saphyra.skyxplore.gamedata.subservice.ShipService;
import org.github.saphyra.skyxplore.ship.domain.EquippedShip;
import org.github.saphyra.skyxplore.ship.repository.EquippedShipDao;
import org.github.saphyra.skyxplore.slot.domain.EquippedSlot;
import org.github.saphyra.skyxplore.slot.repository.SlotDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;

@RunWith(MockitoJUnitRunner.class)
public class EquipShipServiceTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String ITEM_ID = "item_id";
    private static final String SHIP_TYPE = "ship_type";
    private static final Integer SLOT = 5;
    private static final String WEAPON_SLOT_ID = "weapon_slot_id";
    private static final String DEFENSE_SLOT_ID = "defense_slot_id";
    private static final String CONNECTOR = "connector";
    private static final String NEW_SHIP_TYPE = "new_ship_type";

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
    public void init() {
        when(characterQueryService.findByCharacterId(CHARACTER_ID)).thenReturn(character);
        when(shipQueryService.getShipByCharacterId(CHARACTER_ID)).thenReturn(equippedShip);
    }

    @Test(expected = BadRequestException.class)
    public void testEquipShipShouldThrowExceptionWhenInvalidItemId() {
        //GIVEN
        when(shipService.get(ITEM_ID)).thenReturn(null);
        //WHEN
        underTest.equipShip(CHARACTER_ID, ITEM_ID);
    }

    @Test
    public void testEquipShipShouldEquip() {
        //GIVEN
        Ship ship = new Ship();
        ship.setId(SHIP_TYPE);
        ship.setConnector(SLOT);

        Slot slot = new Slot();
        slot.setFront(SLOT);
        slot.setSide(SLOT);
        slot.setBack(SLOT);
        ship.setDefense(slot);
        ship.setWeapon(slot);
        when(shipService.get(NEW_SHIP_TYPE)).thenReturn(ship);

        when(equippedShip.getShipType()).thenReturn(SHIP_TYPE);
        when(equippedShip.getWeaponSlotId()).thenReturn(WEAPON_SLOT_ID);
        when(equippedShip.getDefenseSlotId()).thenReturn(DEFENSE_SLOT_ID);

        when(slotDao.getById(WEAPON_SLOT_ID)).thenReturn(equippedSlot);
        when(slotDao.getById(DEFENSE_SLOT_ID)).thenReturn(equippedSlot);
        when(equippedSlot.getFrontEquipped()).thenReturn(new ArrayList<>(Arrays.asList(ITEM_ID)));
        when(equippedSlot.getLeftEquipped()).thenReturn(new ArrayList<>(Arrays.asList(ITEM_ID)));
        when(equippedSlot.getRightEquipped()).thenReturn(new ArrayList<>(Arrays.asList(ITEM_ID)));
        when(equippedSlot.getBackEquipped()).thenReturn(new ArrayList<>(Arrays.asList(ITEM_ID)));

        when(equippedShip.getConnectorEquipped()).thenReturn(new ArrayList<>(Arrays.asList(CONNECTOR)));
        //WHEN
        underTest.equipShip(CHARACTER_ID, NEW_SHIP_TYPE);
        //THEN
        verify(character).removeEquipment(NEW_SHIP_TYPE);
        verify(character).addEquipment(SHIP_TYPE);
        verify(equippedShip).setShipType(SHIP_TYPE);
        verify(character).addEquipment(CONNECTOR);
        verify(equippedShip).removeConnector(CONNECTOR);
        verify(equippedShip).setConnectorSlot(SLOT);

        verify(equippedShipDao).save(equippedShip);
        verify(characterDao).save(character);
        verify(character, times(8)).addEquipment(ITEM_ID);
        verify(equippedSlot, times(2)).removeFront(ITEM_ID);
        verify(equippedSlot, times(2)).removeLeft(ITEM_ID);
        verify(equippedSlot, times(2)).removeRight(ITEM_ID);
        verify(equippedSlot, times(2)).removeBack(ITEM_ID);

        verify(equippedSlot, times(2)).setFrontSlot(SLOT);
        verify(equippedSlot, times(2)).setBackSlot(SLOT);
        verify(equippedSlot, times(2)).setLeftSlot(SLOT);
        verify(equippedSlot, times(2)).setRightSlot(SLOT);
    }
}