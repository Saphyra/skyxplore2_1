package org.github.saphyra.skyxplore.ship;

import static org.github.saphyra.skyxplore.ship.EquippedShipFacade.CONNECTOR_SLOT_NAME;
import static org.github.saphyra.skyxplore.ship.EquippedShipFacade.DEFENSE_SLOT_NAME;
import static org.github.saphyra.skyxplore.ship.EquippedShipFacade.FRONT_SLOT_NAME;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;

import org.github.saphyra.skyxplore.character.CharacterQueryService;
import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import org.github.saphyra.skyxplore.character.repository.CharacterDao;
import org.github.saphyra.skyxplore.common.exception.BadSlotNameException;
import org.github.saphyra.skyxplore.gamedata.entity.Extender;
import org.github.saphyra.skyxplore.gamedata.subservice.ExtenderService;
import org.github.saphyra.skyxplore.ship.domain.EquipRequest;
import org.github.saphyra.skyxplore.ship.domain.EquippedShip;
import org.github.saphyra.skyxplore.ship.repository.EquippedShipDao;
import org.github.saphyra.skyxplore.slot.domain.EquippedSlot;
import org.github.saphyra.skyxplore.slot.repository.SlotDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;

@RunWith(MockitoJUnitRunner.class)
public class EquipServiceTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String ITEM_ID = "item_id";
    private static final String EXTENDED_SLOT = "extended_slot";
    private static final String EQUIPPED_CONNECTOR = "equipped_connector";
    public static final String UNKNOWN_SLOT_NAME = "asd";

    @Mock
    private CharacterDao characterDao;

    @Mock
    private CharacterQueryService characterQueryService;

    @Mock
    private EquippedShipDao equippedShipDao;

    @Mock
    private EquipUtil equipUtil;

    @Mock
    private ExtenderService extenderService;

    @Mock
    private ShipQueryService shipQueryService;

    @Mock
    private SlotDao slotDao;

    @Mock
    private SkyXpCharacter character;

    @Mock
    private EquippedShip ship;

    @Mock
    private EquippedSlot equippedSlot;

    @InjectMocks
    private EquipService underTest;

    @Before
    public void init() {
        when(characterQueryService.findByCharacterId(CHARACTER_ID)).thenReturn(character);
        when(shipQueryService.getShipByCharacterId(CHARACTER_ID)).thenReturn(ship);
    }

    @After
    public void after() {
        verify(characterQueryService).findByCharacterId(CHARACTER_ID);
        verify(shipQueryService).getShipByCharacterId(CHARACTER_ID);
        verify(character).removeEquipment(ITEM_ID);
    }

    @Test(expected = BadRequestException.class)
    public void testEquipExtenderShouldThrowExceptionWhenAlreadyEquipped() {
        //GIVEN
        EquipRequest equipRequest = new EquipRequest(ITEM_ID, CONNECTOR_SLOT_NAME);

        when(equipUtil.isExtender(ITEM_ID)).thenReturn(true);

        when(ship.getConnectorEquipped()).thenReturn(new ArrayList<>(Arrays.asList(ITEM_ID)));

        Extender extender = new Extender();
        extender.setExtendedSlot(EXTENDED_SLOT);
        when(extenderService.get(ITEM_ID)).thenReturn(extender);
        //WHEN
        underTest.equip(equipRequest, CHARACTER_ID);
    }

    @Test
    public void testEquipExtenderShouldExtendConnector() {
        //GIVEN
        EquipRequest equipRequest = new EquipRequest(ITEM_ID, CONNECTOR_SLOT_NAME);

        when(equipUtil.isExtender(ITEM_ID)).thenReturn(true);

        when(ship.getConnectorEquipped()).thenReturn(new ArrayList<>(Arrays.asList(EQUIPPED_CONNECTOR)));

        Extender extender = new Extender();
        extender.setExtendedNum(2);
        extender.setExtendedSlot(CONNECTOR_SLOT_NAME);
        when(extenderService.get(ITEM_ID)).thenReturn(extender);
        //WHEN
        underTest.equip(equipRequest, CHARACTER_ID);
        //THEN
        verify(ship).addConnector(ITEM_ID);
        verify(ship).addConnectorSlot(2);
        verify(ship).addConnector(ITEM_ID);
        verify(equippedShipDao).save(ship);
        verify(characterDao).save(character);
    }

    @Test
    public void testEquipExtenderShouldExtend() {
        //GIVEN
        EquipRequest equipRequest = new EquipRequest(ITEM_ID, CONNECTOR_SLOT_NAME);
        equipRequest.setEquipTo(CONNECTOR_SLOT_NAME);

        when(equipUtil.isExtender(ITEM_ID)).thenReturn(true);

        when(ship.getConnectorEquipped()).thenReturn(new ArrayList<>(Arrays.asList(EQUIPPED_CONNECTOR)));

        Extender extender = new Extender();
        extender.setExtendedNum(2);
        extender.setExtendedSlot(DEFENSE_SLOT_NAME);
        when(extenderService.get(ITEM_ID)).thenReturn(extender);

        when(equipUtil.getSlotByName(ship, DEFENSE_SLOT_NAME)).thenReturn(equippedSlot);
        //WHEN
        underTest.equip(equipRequest, CHARACTER_ID);
        //THEN
        verify(ship).addConnector(ITEM_ID);
        verify(equipUtil).getSlotByName(ship, DEFENSE_SLOT_NAME);
        verify(equippedSlot).addSlot(2);
        verify(slotDao).save(equippedSlot);
        verify(equippedShipDao).save(ship);
        verify(characterDao).save(character);
    }

    @Test
    public void testEquipConnector() {
        //GIVEN
        EquipRequest equipRequest = new EquipRequest(ITEM_ID, CONNECTOR_SLOT_NAME);
        equipRequest.setEquipTo(CONNECTOR_SLOT_NAME);

        when(equipUtil.isExtender(ITEM_ID)).thenReturn(false);
        //WHEN
        underTest.equip(equipRequest, CHARACTER_ID);
        //THEN
        verify(ship).addConnector(ITEM_ID);
        verify(equippedShipDao).save(ship);
        verify(characterDao).save(character);
    }

    @Test(expected = BadSlotNameException.class)
    public void testEquipShouldThrowExceptionWhenBadSlotName() {
        EquipRequest equipRequest = new EquipRequest(ITEM_ID, UNKNOWN_SLOT_NAME);
        equipRequest.setEquipTo(DEFENSE_SLOT_NAME);

        when(equipUtil.getSlotByName(ship, DEFENSE_SLOT_NAME)).thenReturn(equippedSlot);
        //WHEN
        underTest.equip(equipRequest, CHARACTER_ID);
    }

    @Test
    public void testEquipShouldEquipToSlot() {
        //GIVEN
        EquipRequest equipRequest = new EquipRequest(ITEM_ID, FRONT_SLOT_NAME + DEFENSE_SLOT_NAME);

        when(equipUtil.getSlotByName(ship, FRONT_SLOT_NAME + DEFENSE_SLOT_NAME)).thenReturn(equippedSlot);
        //WHEN
        underTest.equip(equipRequest, CHARACTER_ID);
        //THEN
        verify(equippedSlot).addFront(ITEM_ID);
        verify(slotDao).save(equippedSlot);
        verify(characterDao).save(character);
    }
}