package com.github.saphyra.skyxplore.userdata.ship;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.common.exception.BadSlotNameException;
import com.github.saphyra.skyxplore.data.entity.Extender;
import com.github.saphyra.skyxplore.data.subservice.ExtenderService;
import com.github.saphyra.skyxplore.userdata.ship.domain.EquippedShip;
import com.github.saphyra.skyxplore.userdata.slot.SlotQueryService;
import com.github.saphyra.skyxplore.userdata.slot.domain.EquippedSlot;

@RunWith(MockitoJUnitRunner.class)
public class EquipUtilTest {
    private static final String DEFENSE_SLOT_ID = "defense_slot_id";
    private static final String WEAPON_SLOT_ID = "weapon_slot_id";

    @Mock
    private ExtenderService extenderService;

    @Mock
    private SlotQueryService slotQueryService;

    @InjectMocks
    private EquipUtil underTest;

    @Mock
    private EquippedShip ship;

    @Mock
    private EquippedSlot slot;

    @Test
    public void testIsExtenderShouldReturnTrue() {
        //GIVEN
        String testId = "id";
        when(extenderService.get("id")).thenReturn(new Extender());
        //WHEN
        boolean result = underTest.isExtender(testId);
        //THEN
        assertTrue(result);
    }

    @Test
    public void testIsExtenderShouldReturnFalse() {
        //GIVEN
        String testId = "id";
        when(extenderService.get("id")).thenReturn(null);
        //WHEN
        boolean result = underTest.isExtender(testId);
        //THEN
        assertFalse(result);
    }

    @Test(expected = BadSlotNameException.class)
    public void testGetSlotByNameShouldThrowExceptionWhenBadSlotName() {
        //GIVEN
        String slotName = "imnotaslotname";
        //WHEN
        underTest.getSlotByName(ship, slotName);
    }

    @Test
    public void testSlotByNameShouldReturnDefenseSlotWhenDefense() {
        //GIVEN
        given(ship.getDefenseSlotId()).willReturn(DEFENSE_SLOT_ID);
        when(slotQueryService.findSlotById(DEFENSE_SLOT_ID)).thenReturn(slot);
        //WHEN
        EquippedSlot result = underTest.getSlotByName(ship, EquippedShipConstants.DEFENSE_SLOT_NAME);
        //THEN
        assertEquals(slot, result);
    }

    @Test
    public void testSlotByNameShouldReturnWeaponSlotWhenWeapon() {
        //GIVEN
        given(ship.getWeaponSlotId()).willReturn(WEAPON_SLOT_ID);
        when(slotQueryService.findSlotById(WEAPON_SLOT_ID)).thenReturn(slot);
        //WHEN
        EquippedSlot result = underTest.getSlotByName(ship, EquippedShipConstants.WEAPON_SLOT_NAME);
        //THEN
        assertEquals(slot, result);
    }
}
