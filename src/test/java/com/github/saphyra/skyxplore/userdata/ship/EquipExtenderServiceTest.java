package com.github.saphyra.skyxplore.userdata.ship;

import static com.github.saphyra.skyxplore.data.DataConstants.CONNECTOR_SLOT_NAME;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.skyxplore.data.entity.Extender;
import com.github.saphyra.skyxplore.data.subservice.ExtenderService;
import com.github.saphyra.skyxplore.userdata.ship.domain.EquippedShip;
import com.github.saphyra.skyxplore.userdata.slot.domain.EquippedSlot;
import com.github.saphyra.skyxplore.userdata.slot.repository.SlotDao;

@RunWith(MockitoJUnitRunner.class)
public class EquipExtenderServiceTest {
    private static final String EQUIPMENT_ID = "equipment_id";
    private static final String EQUIPPED_EXTENDER = "equipped_extender";
    private static final String EXTENDED_SLOT = "extended_slot";
    private static final Integer EXTENDED_NUM = 3;

    @Mock
    private EquipUtil equipUtil;

    @Mock
    private ExtenderService extenderService;

    @Mock
    private SlotDao slotDao;

    @Mock
    private Extender extender;

    @Mock
    private Extender equippedExtender;

    @Mock
    private EquippedSlot equippedSlot;

    @Mock
    private EquippedShip ship;

    @InjectMocks
    private EquipExtenderService underTest;

    @Before
    public void setUp() {
        given(extenderService.get(EQUIPMENT_ID)).willReturn(extender);
        given(ship.getConnectorEquipped()).willReturn(Arrays.asList(EQUIPPED_EXTENDER));
        given(extenderService.get(EQUIPPED_EXTENDER)).willReturn(equippedExtender);
        given(equippedExtender.getExtendedSlot()).willReturn(EXTENDED_SLOT);
        given(extender.getExtendedNum()).willReturn(EXTENDED_NUM);
    }

    @Test(expected = BadRequestException.class)
    public void equipExtender_alreadyEquipped() {
        //GIVEN
        given(extender.getExtendedSlot()).willReturn(EXTENDED_SLOT);
        //WHEN
        underTest.equipExtender(EQUIPMENT_ID, ship);
    }

    @Test
    public void equipExtender_extendedConnector() {
        //GIVEN
        given(extender.getExtendedSlot()).willReturn(CONNECTOR_SLOT_NAME);
        //WHEN
        underTest.equipExtender(EQUIPMENT_ID, ship);
        //THEN
        verify(ship).addConnectorSlot(EXTENDED_NUM);
    }

    @Test
    public void equipExtender_extendSlot() {
        //GIVEN
        given(extender.getExtendedSlot()).willReturn(EXTENDED_SLOT);
        given(equippedExtender.getExtendedSlot()).willReturn(CONNECTOR_SLOT_NAME);
        given(equipUtil.getSlotByName(ship, EXTENDED_SLOT)).willReturn(equippedSlot);
        //WHEN
        underTest.equipExtender(EQUIPMENT_ID, ship);
        //THEN
        verify(equippedSlot).addSlot(EXTENDED_NUM);
        verify(slotDao).save(equippedSlot);
    }
}