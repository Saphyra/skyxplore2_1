package skyxplore.service.ship;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.controller.request.character.UnequipRequest;
import skyxplore.dataaccess.db.CharacterDao;
import skyxplore.dataaccess.db.EquippedShipDao;
import skyxplore.dataaccess.db.SlotDao;
import skyxplore.dataaccess.gamedata.entity.Extender;
import skyxplore.dataaccess.gamedata.subservice.ExtenderService;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.domain.ship.EquippedShip;
import skyxplore.domain.slot.EquippedSlot;
import skyxplore.exception.BadSlotNameException;
import skyxplore.service.character.CharacterQueryService;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static skyxplore.service.EquippedShipFacade.CONNECTOR_SLOT_NAME;
import static skyxplore.service.EquippedShipFacade.DEFENSE_SLOT_NAME;
import static skyxplore.service.EquippedShipFacade.FRONT_SLOT_NAME;
import static skyxplore.testutil.TestUtils.*;

@RunWith(MockitoJUnitRunner.class)
public class UnequipServiceTest {
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
    public void init(){
        when(characterQueryService.findByCharacterId(CHARACTER_ID_1)).thenReturn(character);
        when(shipQueryService.getShipByCharacterId(CHARACTER_ID_1)).thenReturn(equippedShip);
    }

    @Test
    public void testUnequipExtenderShouldUnequipConnector(){
        //GIVEN
        UnequipRequest request = createUnequipRequest();
        request.setSlot(CONNECTOR_SLOT_NAME);

        when(equipUtil.isExtender(EQUIP_ITEM_ID)).thenReturn(true);

        Extender extender = new Extender();
        extender.setExtendedSlot(CONNECTOR_SLOT_NAME);
        extender.setExtendedNum(2);
        when(extenderService.get(EQUIP_ITEM_ID)).thenReturn(extender);
        //WHEN
        underTest.unequip(request, CHARACTER_ID_1);
        //THEN
        verify(equippedShip).removeConnector(EQUIP_ITEM_ID);
        verify(equippedShip).removeConnectorSlot(2, character, extenderService);
        verify(equippedShipDao).save(equippedShip);
        verify(character).addEquipment(EQUIP_ITEM_ID);
        verify(characterDao).save(character);
    }

    @Test
    public void testUnequipExtenderShouldUnequipExtender(){
        //GIVEN
        UnequipRequest request = createUnequipRequest();
        request.setSlot(CONNECTOR_SLOT_NAME);

        when(equipUtil.isExtender(EQUIP_ITEM_ID)).thenReturn(true);

        Extender extender = new Extender();
        extender.setExtendedSlot(DATA_SLOT);
        extender.setExtendedNum(2);
        when(extenderService.get(EQUIP_ITEM_ID)).thenReturn(extender);

        when(equipUtil.getSlotByName(equippedShip, DATA_SLOT)).thenReturn(equippedSlot);
        //WHEN
        underTest.unequip(request, CHARACTER_ID_1);
        //THEN
        verify(equippedShip).removeConnector(EQUIP_ITEM_ID);
        verify(equippedSlot).removeSlot(character, 2);
        verify(slotDao).save(equippedSlot);
        verify(equippedShipDao).save(equippedShip);
        verify(character).addEquipment(EQUIP_ITEM_ID);
        verify(characterDao).save(character);
    }

    @Test
    public void testUnequipConnectorShouldUnequip(){
        //GIVEN
        UnequipRequest request = createUnequipRequest();
        request.setSlot(CONNECTOR_SLOT_NAME);

        when(equipUtil.isExtender(EQUIP_ITEM_ID)).thenReturn(false);
        //WHEN
        underTest.unequip(request, CHARACTER_ID_1);
        //THEN
        verify(equippedShip).removeConnector(EQUIP_ITEM_ID);
        verifyZeroInteractions(slotDao);
        verifyZeroInteractions(extenderService);
        verify(equippedShipDao).save(equippedShip);
        verify(character).addEquipment(EQUIP_ITEM_ID);
        verify(characterDao).save(character);
    }

    @Test(expected = BadSlotNameException.class)
    public void testUnequipEquipmentShouldThrowExceptionWhenBadSlotName(){
        //GIVEN
        UnequipRequest request = createUnequipRequest();
        request.setSlot(DEFENSE_SLOT_NAME);

        when(equipUtil.getSlotByName(equippedShip, DEFENSE_SLOT_NAME)).thenReturn(equippedSlot);
        //WHEN
        underTest.unequip(request, CHARACTER_ID_1);
    }

    @Test
    public void testUnequipEquipmentShouldUnequip(){
        //GIVEN
        UnequipRequest request = createUnequipRequest();
        request.setSlot(FRONT_SLOT_NAME + DEFENSE_SLOT_NAME);

        when(equipUtil.getSlotByName(equippedShip, FRONT_SLOT_NAME + DEFENSE_SLOT_NAME)).thenReturn(equippedSlot);
        //WHEN
        underTest.unequip(request, CHARACTER_ID_1);
        //THEN
        equippedSlot.removeFront(EQUIP_ITEM_ID);
        verify(slotDao).save(equippedSlot);
        verify(character).addEquipment(EQUIP_ITEM_ID);
        verify(characterDao).save(character);
    }
}