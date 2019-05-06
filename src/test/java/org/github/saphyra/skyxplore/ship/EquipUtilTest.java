package org.github.saphyra.skyxplore.ship;

import org.github.saphyra.skyxplore.common.exception.BadSlotNameException;
import org.github.saphyra.skyxplore.gamedata.entity.Extender;
import org.github.saphyra.skyxplore.gamedata.subservice.ExtenderService;
import org.github.saphyra.skyxplore.ship.domain.EquippedShip;
import org.github.saphyra.skyxplore.slot.SlotQueryService;
import org.github.saphyra.skyxplore.slot.domain.EquippedSlot;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.github.saphyra.skyxplore.ship.EquippedShipConstants.DEFENSE_SLOT_NAME;
import static org.github.saphyra.skyxplore.ship.EquippedShipConstants.WEAPON_SLOT_NAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

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
        underTest.getSlotByName(EquippedShip.builder().build(), slotName);
    }

    @Test
    public void testSlotByNameShouldReturnDefenseSlotWhenDefense() {
        //GIVEN
        EquippedShip ship = EquippedShip.builder()
            .defenseSlotId(DEFENSE_SLOT_ID)
            .build();
        EquippedSlot slot = EquippedSlot.builder()
            .slotId(DEFENSE_SLOT_ID).build();
        when(slotQueryService.findSlotById(DEFENSE_SLOT_ID)).thenReturn(slot);
        //WHEN
        EquippedSlot result = underTest.getSlotByName(ship, DEFENSE_SLOT_NAME);
        //THEN
        assertEquals(slot, result);
    }

    @Test
    public void testSlotByNameShouldReturnWeaponSlotWhenWeapon() {
        //GIVEN
        EquippedShip ship = EquippedShip.builder()
            .weaponSlotId(WEAPON_SLOT_ID)
            .build();
        EquippedSlot slot = EquippedSlot.builder()
            .slotId(WEAPON_SLOT_ID)
            .build();
        when(slotQueryService.findSlotById(WEAPON_SLOT_ID)).thenReturn(slot);
        //WHEN
        EquippedSlot result = underTest.getSlotByName(ship, WEAPON_SLOT_NAME);
        //THEN
        assertEquals(slot, result);
    }
}
