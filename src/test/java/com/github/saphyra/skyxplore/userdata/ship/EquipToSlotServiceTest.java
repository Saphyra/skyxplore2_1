package com.github.saphyra.skyxplore.userdata.ship;

import static com.github.saphyra.testing.ExceptionValidator.verifyException;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.skyxplore.common.ErrorCode;
import com.github.saphyra.skyxplore.userdata.ship.domain.EquipRequest;
import com.github.saphyra.skyxplore.userdata.ship.domain.EquippedShip;
import com.github.saphyra.skyxplore.userdata.slot.domain.EquippedSlot;
import com.github.saphyra.skyxplore.userdata.slot.repository.SlotDao;

@RunWith(MockitoJUnitRunner.class)
public class EquipToSlotServiceTest {
    private static final String EQUIPMENT_ID = "equipment_id";

    @Mock
    private EquipUtil equipUtil;

    @Mock
    private SlotDao slotDao;

    @Mock
    private EquippedShip ship;

    @Mock
    private EquippedSlot equippedSlot;

    @InjectMocks
    private EquipToSlotService underTest;

    @Before
    public void setUp() {
        given(equipUtil.getSlotByName(eq(ship), anyString())).willReturn(equippedSlot);
    }

    @Test
    public void equipToSlot_badSlotName() {
        //GIVEN
        EquipRequest request = new EquipRequest(EQUIPMENT_ID, "");
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.equipToSlot(request, ship));
        //THEN
        verifyException(ex, BadRequestException.class, ErrorCode.INVALID_SLOT_NAME);
    }

    @Test
    public void equipToSlot_front() {
        //GIVEN
        EquipRequest request = new EquipRequest(EQUIPMENT_ID, EquippedShipConstants.FRONT_SLOT_NAME);
        //WHEN
        underTest.equipToSlot(request, ship);
        //THEN
        verify(equippedSlot).addFront(EQUIPMENT_ID);
        verify(slotDao).save(equippedSlot);
    }

    @Test
    public void equipToSlot_back() {
        //GIVEN
        EquipRequest request = new EquipRequest(EQUIPMENT_ID, EquippedShipConstants.BACK_SLOT_NAME);
        //WHEN
        underTest.equipToSlot(request, ship);
        //THEN
        verify(equippedSlot).addBack(EQUIPMENT_ID);
        verify(slotDao).save(equippedSlot);
    }

    @Test
    public void equipToSlot_left() {
        //GIVEN
        EquipRequest request = new EquipRequest(EQUIPMENT_ID, EquippedShipConstants.LEFT_SLOT_NAME);
        //WHEN
        underTest.equipToSlot(request, ship);
        //THEN
        verify(equippedSlot).addLeft(EQUIPMENT_ID);
        verify(slotDao).save(equippedSlot);
    }

    @Test
    public void equipToSlot_right() {
        //GIVEN
        EquipRequest request = new EquipRequest(EQUIPMENT_ID, EquippedShipConstants.RIGHT_SLOT_NAME);
        //WHEN
        underTest.equipToSlot(request, ship);
        //THEN
        verify(equippedSlot).addRight(EQUIPMENT_ID);
        verify(slotDao).save(equippedSlot);
    }
}