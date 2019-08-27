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
import com.github.saphyra.skyxplore.userdata.ship.domain.EquippedShip;
import com.github.saphyra.skyxplore.userdata.ship.domain.UnequipRequest;
import com.github.saphyra.skyxplore.userdata.slot.domain.EquippedSlot;
import com.github.saphyra.skyxplore.userdata.slot.repository.SlotDao;

@RunWith(MockitoJUnitRunner.class)
public class UnequipFromSlotServiceTest {
    private static final String EQUIPMENT_ID = "equipment_id";
    @Mock
    private EquipUtil equipUtil;

    @Mock
    private SlotDao slotDao;

    @Mock
    private EquippedShip ship;

    @Mock
    private EquippedSlot slot;

    @InjectMocks
    private UnequipFromSlotService underTest;

    @Before
    public void setUp() {
        given(equipUtil.getSlotByName(eq(ship), anyString())).willReturn(slot);
    }

    @Test
    public void unequipFromSlot_badSlotName() {
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.unequipFromSlot(new UnequipRequest("asd", EQUIPMENT_ID), ship));
        //THEN
        verifyException(ex, BadRequestException.class, ErrorCode.INVALID_SLOT_NAME);
    }

    @Test
    public void unequipFromSlot_front() {
        //WHEN
        underTest.unequipFromSlot(new UnequipRequest(EquippedShipConstants.FRONT_SLOT_NAME, EQUIPMENT_ID), ship);
        //THEN
        verify(slot).removeFront(EQUIPMENT_ID);
        verify(slotDao).save(slot);
    }

    @Test
    public void unequipFromSlot_left() {
        //WHEN
        underTest.unequipFromSlot(new UnequipRequest(EquippedShipConstants.LEFT_SLOT_NAME, EQUIPMENT_ID), ship);
        //THEN
        verify(slot).removeLeft(EQUIPMENT_ID);
        verify(slotDao).save(slot);
    }

    @Test
    public void unequipFromSlot_right() {
        //WHEN
        underTest.unequipFromSlot(new UnequipRequest(EquippedShipConstants.RIGHT_SLOT_NAME, EQUIPMENT_ID), ship);
        //THEN
        verify(slot).removeRight(EQUIPMENT_ID);
        verify(slotDao).save(slot);
    }

    @Test
    public void unequipFromSlot_back() {
        //WHEN
        underTest.unequipFromSlot(new UnequipRequest(EquippedShipConstants.BACK_SLOT_NAME, EQUIPMENT_ID), ship);
        //THEN
        verify(slot).removeBack(EQUIPMENT_ID);
        verify(slotDao).save(slot);
    }
}