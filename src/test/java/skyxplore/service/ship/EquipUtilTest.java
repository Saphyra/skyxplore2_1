package skyxplore.service.ship;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.service.EquippedShipFacade.DEFENSE_SLOT_NAME;
import static skyxplore.service.EquippedShipFacade.WEAPON_SLOT_NAME;
import static skyxplore.testutil.TestUtils.DEFENSE_SLOT_ID;
import static skyxplore.testutil.TestUtils.WEAPON_SLOT_ID;
import static skyxplore.testutil.TestUtils.createEquippedShip;

import org.github.saphyra.skyxplore.gamedata.entity.Extender;
import org.github.saphyra.skyxplore.gamedata.subservice.ExtenderService;
import org.github.saphyra.skyxplore.ship.domain.EquippedShip;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.github.saphyra.skyxplore.slot.repository.SlotDao;
import org.github.saphyra.skyxplore.slot.domain.EquippedSlot;
import skyxplore.exception.BadSlotNameException;

@RunWith(MockitoJUnitRunner.class)
public class EquipUtilTest {
    @Mock
    private ExtenderService extenderService;

    @Mock
    private SlotDao slotDao;

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
        EquippedShip ship = createEquippedShip();
        EquippedSlot slot = EquippedSlot.builder()
            .slotId(DEFENSE_SLOT_ID).build();
        when(slotDao.getById(DEFENSE_SLOT_ID)).thenReturn(slot);
        //WHEN
        EquippedSlot result = underTest.getSlotByName(ship, DEFENSE_SLOT_NAME);
        //THEN
        verify(slotDao).getById(DEFENSE_SLOT_ID);
        assertEquals(slot, result);
    }

    @Test
    public void testSlotByNameShouldReturnWeaponSlotWhenWeapon() {
        //GIVEN
        EquippedShip ship = createEquippedShip();
        EquippedSlot slot = EquippedSlot.builder()
            .slotId(WEAPON_SLOT_ID)
            .build();
        when(slotDao.getById(WEAPON_SLOT_ID)).thenReturn(slot);
        //WHEN
        EquippedSlot result = underTest.getSlotByName(ship, WEAPON_SLOT_NAME);
        //THEN
        verify(slotDao).getById(WEAPON_SLOT_ID);
        assertEquals(slot, result);
    }
}
