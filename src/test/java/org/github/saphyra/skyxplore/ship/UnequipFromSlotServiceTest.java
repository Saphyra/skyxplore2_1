package org.github.saphyra.skyxplore.ship;

import org.github.saphyra.skyxplore.common.exception.BadSlotNameException;
import org.github.saphyra.skyxplore.ship.domain.EquippedShip;
import org.github.saphyra.skyxplore.ship.domain.UnequipRequest;
import org.github.saphyra.skyxplore.slot.domain.EquippedSlot;
import org.github.saphyra.skyxplore.slot.repository.SlotDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.github.saphyra.skyxplore.ship.EquippedShipConstants.BACK_SLOT_NAME;
import static org.github.saphyra.skyxplore.ship.EquippedShipConstants.FRONT_SLOT_NAME;
import static org.github.saphyra.skyxplore.ship.EquippedShipConstants.LEFT_SLOT_NAME;
import static org.github.saphyra.skyxplore.ship.EquippedShipConstants.RIGHT_SLOT_NAME;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

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

    @Test(expected = BadSlotNameException.class)
    public void unequipFromSlot_badSlotName() {
        //WHEN
        underTest.unequipFromSlot(new UnequipRequest("asd", EQUIPMENT_ID), ship);
    }

    @Test
    public void unequipFromSlot_front() {
        //WHEN
        underTest.unequipFromSlot(new UnequipRequest(FRONT_SLOT_NAME, EQUIPMENT_ID), ship);
        //THEN
        verify(slot).removeFront(EQUIPMENT_ID);
        verify(slotDao).save(slot);
    }

    @Test
    public void unequipFromSlot_left() {
        //WHEN
        underTest.unequipFromSlot(new UnequipRequest(LEFT_SLOT_NAME, EQUIPMENT_ID), ship);
        //THEN
        verify(slot).removeLeft(EQUIPMENT_ID);
        verify(slotDao).save(slot);
    }

    @Test
    public void unequipFromSlot_right() {
        //WHEN
        underTest.unequipFromSlot(new UnequipRequest(RIGHT_SLOT_NAME, EQUIPMENT_ID), ship);
        //THEN
        verify(slot).removeRight(EQUIPMENT_ID);
        verify(slotDao).save(slot);
    }

    @Test
    public void unequipFromSlot_back() {
        //WHEN
        underTest.unequipFromSlot(new UnequipRequest(BACK_SLOT_NAME, EQUIPMENT_ID), ship);
        //THEN
        verify(slot).removeBack(EQUIPMENT_ID);
        verify(slotDao).save(slot);
    }
}