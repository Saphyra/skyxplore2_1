package com.github.saphyra.skyxplore.userdata.slot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class EquippedSlotFacadeTest {
    private static final String SHIP_ID = "ship_id";
    private static final String DEFENSE_SLOT_ID = "defense_slot_id";
    private static final String WEAPON_SLOT_ID = "weapon_slot_id";

    @Mock
    private DefenseSlotCreatorService defenseSlotCreatorService;

    @Mock
    private WeaponSlotCreatorService weaponSlotCreatorService;

    @InjectMocks
    private EquippedSlotFacade underTest;

    @Test
    public void createDefenseSlot() {
        //GIVEN
        given(defenseSlotCreatorService.createSlot(SHIP_ID)).willReturn(DEFENSE_SLOT_ID);
        //WHEN
        String result = underTest.createDefenseSlot(SHIP_ID);
        //THEN
        assertThat(result).isEqualTo(DEFENSE_SLOT_ID);
    }

    @Test
    public void createWeaponSlot() {
        //GIVEN
        given(weaponSlotCreatorService.createSlot(SHIP_ID)).willReturn(WEAPON_SLOT_ID);
        //WHEN
        String result = underTest.createWeaponSlot(SHIP_ID);
        //THEN
        assertThat(result).isEqualTo(WEAPON_SLOT_ID);
    }
}