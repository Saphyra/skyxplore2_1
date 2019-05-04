package org.github.saphyra.skyxplore.ship;

import org.github.saphyra.skyxplore.character.CharacterQueryService;
import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import org.github.saphyra.skyxplore.character.repository.CharacterDao;
import org.github.saphyra.skyxplore.common.exception.BadSlotNameException;
import org.github.saphyra.skyxplore.gamedata.entity.Extender;
import org.github.saphyra.skyxplore.gamedata.subservice.ExtenderService;
import org.github.saphyra.skyxplore.ship.domain.EquippedShip;
import org.github.saphyra.skyxplore.ship.domain.UnequipRequest;
import org.github.saphyra.skyxplore.ship.repository.EquippedShipDao;
import org.github.saphyra.skyxplore.slot.domain.EquippedSlot;
import org.github.saphyra.skyxplore.slot.repository.SlotDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.github.saphyra.skyxplore.ship.EquippedShipConstants.CONNECTOR_SLOT_NAME;
import static org.github.saphyra.skyxplore.ship.EquippedShipConstants.DEFENSE_SLOT_NAME;
import static org.github.saphyra.skyxplore.ship.EquippedShipConstants.FRONT_SLOT_NAME;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UnequipServiceTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String ITEM_ID = "item_id";
    private static final String SLOT = "slot";

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
    private EquippedShip equippedShip;

    @Mock
    private EquippedSlot equippedSlot;

    @InjectMocks
    private UnequipService underTest;

    @Before
    public void init() {
        when(characterQueryService.findByCharacterId(CHARACTER_ID)).thenReturn(character);
        when(shipQueryService.getShipByCharacterId(CHARACTER_ID)).thenReturn(equippedShip);
    }

    @Test
    public void testUnequipExtenderShouldUnequipConnector() {
        //GIVEN
        UnequipRequest request = new UnequipRequest(CONNECTOR_SLOT_NAME, ITEM_ID);

        when(equipUtil.isExtender(ITEM_ID)).thenReturn(true);

        Extender extender = new Extender();
        extender.setExtendedSlot(CONNECTOR_SLOT_NAME);
        extender.setExtendedNum(2);
        when(extenderService.get(ITEM_ID)).thenReturn(extender);
        //WHEN
        underTest.unequip(request, CHARACTER_ID);
        //THEN
        verify(equippedShip).removeConnector(ITEM_ID);
        verify(equippedShip).removeConnectorSlot(2, character, extenderService);
        verify(equippedShipDao).save(equippedShip);
        verify(character).addEquipment(ITEM_ID);
        verify(characterDao).save(character);
    }

    @Test
    public void testUnequipExtenderShouldUnequipExtender() {
        //GIVEN
        UnequipRequest request = new UnequipRequest(CONNECTOR_SLOT_NAME, ITEM_ID);

        when(equipUtil.isExtender(ITEM_ID)).thenReturn(true);

        Extender extender = new Extender();
        extender.setExtendedSlot(SLOT);
        extender.setExtendedNum(2);
        when(extenderService.get(ITEM_ID)).thenReturn(extender);

        when(equipUtil.getSlotByName(equippedShip, SLOT)).thenReturn(equippedSlot);
        //WHEN
        underTest.unequip(request, CHARACTER_ID);
        //THEN
        verify(equippedShip).removeConnector(ITEM_ID);
        verify(equippedSlot).removeSlot(character, 2);
        verify(slotDao).save(equippedSlot);
        verify(equippedShipDao).save(equippedShip);
        verify(character).addEquipment(ITEM_ID);
        verify(characterDao).save(character);
    }

    @Test
    public void testUnequipConnectorShouldUnequip() {
        //GIVEN
        UnequipRequest request = new UnequipRequest(CONNECTOR_SLOT_NAME, ITEM_ID);

        when(equipUtil.isExtender(ITEM_ID)).thenReturn(false);
        //WHEN
        underTest.unequip(request, CHARACTER_ID);
        //THEN
        verify(equippedShip).removeConnector(ITEM_ID);
        verifyZeroInteractions(slotDao);
        verifyZeroInteractions(extenderService);
        verify(equippedShipDao).save(equippedShip);
        verify(character).addEquipment(ITEM_ID);
        verify(characterDao).save(character);
    }

    @Test(expected = BadSlotNameException.class)
    public void testUnequipEquipmentShouldThrowExceptionWhenBadSlotName() {
        //GIVEN
        UnequipRequest request = new UnequipRequest(DEFENSE_SLOT_NAME, ITEM_ID);

        when(equipUtil.getSlotByName(equippedShip, DEFENSE_SLOT_NAME)).thenReturn(equippedSlot);
        //WHEN
        underTest.unequip(request, CHARACTER_ID);
    }

    @Test
    public void testUnequipEquipmentShouldUnequip() {
        //GIVEN
        UnequipRequest request = new UnequipRequest(FRONT_SLOT_NAME + DEFENSE_SLOT_NAME, ITEM_ID);

        when(equipUtil.getSlotByName(equippedShip, FRONT_SLOT_NAME + DEFENSE_SLOT_NAME)).thenReturn(equippedSlot);
        //WHEN
        underTest.unequip(request, CHARACTER_ID);
        //THEN
        equippedSlot.removeFront(ITEM_ID);
        verify(slotDao).save(equippedSlot);
        verify(character).addEquipment(ITEM_ID);
        verify(characterDao).save(character);
    }
}