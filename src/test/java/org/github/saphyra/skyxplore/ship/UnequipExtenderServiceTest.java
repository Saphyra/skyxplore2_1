package org.github.saphyra.skyxplore.ship;

import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import org.github.saphyra.skyxplore.gamedata.entity.Extender;
import org.github.saphyra.skyxplore.gamedata.subservice.ExtenderService;
import org.github.saphyra.skyxplore.ship.domain.EquippedShip;
import org.github.saphyra.skyxplore.slot.domain.EquippedSlot;
import org.github.saphyra.skyxplore.slot.repository.SlotDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.github.saphyra.skyxplore.ship.EquippedShipConstants.CONNECTOR_SLOT_NAME;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UnequipExtenderServiceTest {
    private static final String EQUIPMENT_ID = "equipment_id";
    private static final Integer EXTENDED_NUM = 3;
    private static final String EXTENDED_SLOT = "extended_slot";

    @Mock
    private EquipUtil equipUtil;

    @Mock
    private ExtenderService extenderService;

    @Mock
    private SlotDao slotDao;

    @Mock
    private SkyXpCharacter character;

    @Mock
    private EquippedShip ship;

    @Mock
    private EquippedSlot slot;

    @Mock
    private Extender extender;

    @InjectMocks
    private UnequipExtenderService underTest;

    @Test
    public void unequipExtender_connectorExtended() {
        //GIVEN
        given(extenderService.get(EQUIPMENT_ID)).willReturn(extender);
        given(extender.getExtendedSlot()).willReturn(CONNECTOR_SLOT_NAME);
        given(extender.getExtendedNum()).willReturn(EXTENDED_NUM);
        //WHEN
        underTest.unequipExtender(EQUIPMENT_ID, character, ship);
        //THEN
        verify(ship).removeConnectorSlot(EXTENDED_NUM, character, extenderService);
    }

    @Test
    public void unequipExtender_slotExtended() {
        //GIVEN
        given(extenderService.get(EQUIPMENT_ID)).willReturn(extender);
        given(extender.getExtendedSlot()).willReturn(EXTENDED_SLOT);
        given(extender.getExtendedNum()).willReturn(EXTENDED_NUM);
        given(equipUtil.getSlotByName(ship, EXTENDED_SLOT)).willReturn(slot);
        //WHEN
        underTest.unequipExtender(EQUIPMENT_ID, character, ship);
        //THEN
        verify(slot).removeSlot(character, EXTENDED_NUM);
        verify(slotDao).save(slot);
    }
}