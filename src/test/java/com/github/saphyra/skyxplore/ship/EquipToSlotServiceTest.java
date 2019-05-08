package com.github.saphyra.skyxplore.ship;

import com.github.saphyra.skyxplore.common.exception.BadSlotNameException;
import com.github.saphyra.skyxplore.ship.domain.EquipRequest;
import com.github.saphyra.skyxplore.ship.domain.EquippedShip;
import com.github.saphyra.skyxplore.slot.domain.EquippedSlot;
import com.github.saphyra.skyxplore.slot.repository.SlotDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

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

    @Test(expected = BadSlotNameException.class)
    public void equipToSlot_badSlotName() {
        //GIVEN
        EquipRequest request = new EquipRequest(EQUIPMENT_ID, "");
        //WHEN
        underTest.equipToSlot(request, ship);
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