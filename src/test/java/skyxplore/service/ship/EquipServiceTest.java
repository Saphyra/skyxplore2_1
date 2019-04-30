package skyxplore.service.ship;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.controller.request.character.EquipRequest;
import org.github.saphyra.skyxplore.character.repository.CharacterDao;
import org.github.saphyra.skyxplore.ship.repository.EquippedShipDao;
import org.github.saphyra.skyxplore.slot.repository.SlotDao;
import org.github.saphyra.skyxplore.gamedata.entity.Extender;
import org.github.saphyra.skyxplore.gamedata.subservice.ExtenderService;
import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import org.github.saphyra.skyxplore.ship.domain.EquippedShip;
import org.github.saphyra.skyxplore.slot.domain.EquippedSlot;
import org.github.saphyra.skyxplore.common.exception.BadSlotNameException;
import org.github.saphyra.skyxplore.character.CharacterQueryService;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.service.EquippedShipFacade.CONNECTOR_SLOT_NAME;
import static skyxplore.service.EquippedShipFacade.DEFENSE_SLOT_NAME;
import static skyxplore.service.EquippedShipFacade.FRONT_SLOT_NAME;
import static skyxplore.testutil.TestUtils.CHARACTER_ID_1;
import static skyxplore.testutil.TestUtils.DATA_ELEMENT;
import static skyxplore.testutil.TestUtils.DATA_SLOT;
import static skyxplore.testutil.TestUtils.EQUIP_ITEM_ID;
import static skyxplore.testutil.TestUtils.createEquipRequest;

@RunWith(MockitoJUnitRunner.class)
public class EquipServiceTest {
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
        when(characterQueryService.findByCharacterId(CHARACTER_ID_1)).thenReturn(character);
        when(shipQueryService.getShipByCharacterId(CHARACTER_ID_1)).thenReturn(ship);
    }

    @After
    public void after() {
        verify(characterQueryService).findByCharacterId(CHARACTER_ID_1);
        verify(shipQueryService).getShipByCharacterId(CHARACTER_ID_1);
        verify(character).removeEquipment(EQUIP_ITEM_ID);
    }

    @Test(expected = BadRequestException.class)
    public void testEquipExtenderShouldThrowExceptionWhenAlreadyEquipped() {
        //GIVEN
        EquipRequest equipRequest = createEquipRequest();
        equipRequest.setEquipTo(CONNECTOR_SLOT_NAME);

        when(equipUtil.isExtender(EQUIP_ITEM_ID)).thenReturn(true);

        when(ship.getConnectorEquipped()).thenReturn(new ArrayList<>(Arrays.asList(EQUIP_ITEM_ID)));

        Extender extender = new Extender();
        extender.setExtendedSlot(DATA_SLOT);
        when(extenderService.get(EQUIP_ITEM_ID)).thenReturn(extender);
        //WHEN
        underTest.equip(equipRequest, CHARACTER_ID_1);
    }

    @Test
    public void testEquipExtenderShouldExtendConnector() {
        //GIVEN
        EquipRequest equipRequest = createEquipRequest();
        equipRequest.setEquipTo(CONNECTOR_SLOT_NAME);

        when(equipUtil.isExtender(EQUIP_ITEM_ID)).thenReturn(true);

        when(ship.getConnectorEquipped()).thenReturn(new ArrayList<>(Arrays.asList(DATA_ELEMENT)));

        Extender extender = new Extender();
        extender.setExtendedSlot(DATA_SLOT);
        extender.setExtendedNum(2);
        extender.setExtendedSlot(CONNECTOR_SLOT_NAME);
        when(extenderService.get(EQUIP_ITEM_ID)).thenReturn(extender);
        //WHEN
        underTest.equip(equipRequest, CHARACTER_ID_1);
        //THEN
        verify(ship).addConnector(EQUIP_ITEM_ID);
        verify(ship).addConnectorSlot(2);
        verify(ship).addConnector(EQUIP_ITEM_ID);
        verify(equippedShipDao).save(ship);
        verify(characterDao).save(character);
    }

    @Test
    public void testEquipExtenderShouldExtend() {
        //GIVEN
        EquipRequest equipRequest = createEquipRequest();
        equipRequest.setEquipTo(CONNECTOR_SLOT_NAME);

        when(equipUtil.isExtender(EQUIP_ITEM_ID)).thenReturn(true);

        when(ship.getConnectorEquipped()).thenReturn(new ArrayList<>(Arrays.asList(DATA_ELEMENT)));

        Extender extender = new Extender();
        extender.setExtendedSlot(DATA_SLOT);
        extender.setExtendedNum(2);
        extender.setExtendedSlot(DEFENSE_SLOT_NAME);
        when(extenderService.get(EQUIP_ITEM_ID)).thenReturn(extender);

        when(equipUtil.getSlotByName(ship, DEFENSE_SLOT_NAME)).thenReturn(equippedSlot);
        //WHEN
        underTest.equip(equipRequest, CHARACTER_ID_1);
        //THEN
        verify(ship).addConnector(EQUIP_ITEM_ID);
        verify(equipUtil).getSlotByName(ship, DEFENSE_SLOT_NAME);
        verify(equippedSlot).addSlot(2);
        verify(slotDao).save(equippedSlot);
        verify(equippedShipDao).save(ship);
        verify(characterDao).save(character);
    }

    @Test
    public void testEquipConnector() {
        //GIVEN
        EquipRequest equipRequest = createEquipRequest();
        equipRequest.setEquipTo(CONNECTOR_SLOT_NAME);

        when(equipUtil.isExtender(EQUIP_ITEM_ID)).thenReturn(false);
        //WHEN
        underTest.equip(equipRequest, CHARACTER_ID_1);
        //THEN
        verify(ship).addConnector(EQUIP_ITEM_ID);
        verify(equippedShipDao).save(ship);
        verify(characterDao).save(character);
    }

    @Test(expected = BadSlotNameException.class)
    public void testEquipShouldThrowExceptionWhenBadSlotName() {
        EquipRequest equipRequest = createEquipRequest();
        equipRequest.setEquipTo(DEFENSE_SLOT_NAME);

        when(equipUtil.getSlotByName(ship, DEFENSE_SLOT_NAME)).thenReturn(equippedSlot);
        //WHEN
        underTest.equip(equipRequest, CHARACTER_ID_1);
    }

    @Test
    public void testEquipShouldEquipToSlot() {
        //GIVEN
        EquipRequest equipRequest = createEquipRequest();
        equipRequest.setEquipTo(FRONT_SLOT_NAME + DEFENSE_SLOT_NAME);

        when(equipUtil.getSlotByName(ship, FRONT_SLOT_NAME + DEFENSE_SLOT_NAME)).thenReturn(equippedSlot);
        //WHEN
        underTest.equip(equipRequest, CHARACTER_ID_1);
        //THEN
        verify(equippedSlot).addFront(EQUIP_ITEM_ID);
        verify(slotDao).save(equippedSlot);
        verify(characterDao).save(character);
    }
}