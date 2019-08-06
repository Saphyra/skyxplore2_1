package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment;

import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;
import com.github.saphyra.skyxplore.userdata.ship.ShipQueryService;
import com.github.saphyra.skyxplore.userdata.ship.domain.EquippedShip;
import com.github.saphyra.skyxplore.userdata.slot.SlotQueryService;
import com.github.saphyra.skyxplore.userdata.slot.domain.EquippedSlot;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ShipEquipmentAggregatorTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String DEFENSE_SLOT_ID = "defense_slot_id";
    private static final String WEAPON_SLOT_ID = "weapon_slot_id";
    private static final String SHIP_ID = "ship_id";
    private static final String CONNECTOR_ID = "connector_id";
    private static final String FRONT_DEFENSE_ID = "front_defense_id";
    private static final String LEFT_DEFENSE_ID = "left_defense_id";
    private static final String RIGHT_DEFENSE_ID = "right_defense_id";
    private static final String BACK_DEFENSE_ID = "back-defense-id";
    private static final String FRONT_WEAPON_ID = "front-weapon-id";
    private static final String LEFT_WEAPON_ID = "left-weapon-id";
    private static final String RIGHT_WEAPON_ID = "right-weapon-id";
    private static final String BACK_WEAPON_ID = "back-weapon-id";

    @Mock
    private ShipQueryService shipQueryService;

    @Mock
    private SlotQueryService slotQueryService;

    @InjectMocks
    private ShipEquipmentAggregator underTest;

    @Mock
    private EquippedShip ship;

    @Mock
    private EquippedSlot defenseSlot;

    @Mock
    private EquippedSlot weaponSlot;

    @Test
    public void aggregateEquipments() {
        //GIVEN
        given(shipQueryService.findShipbyCharacterIdValidated(CHARACTER_ID)).willReturn(ship);
        given(ship.getDefenseSlotId()).willReturn(DEFENSE_SLOT_ID);
        given(ship.getWeaponSlotId()).willReturn(WEAPON_SLOT_ID);

        given(slotQueryService.findSlotByIdValidated(DEFENSE_SLOT_ID)).willReturn(defenseSlot);
        given(slotQueryService.findSlotByIdValidated(WEAPON_SLOT_ID)).willReturn(weaponSlot);

        given(ship.getShipType()).willReturn(SHIP_ID);
        given(ship.getConnectorEquipped()).willReturn(Arrays.asList(CONNECTOR_ID));

        given(defenseSlot.getFrontEquipped()).willReturn(Arrays.asList(FRONT_DEFENSE_ID));
        given(defenseSlot.getLeftEquipped()).willReturn(Arrays.asList(LEFT_DEFENSE_ID));
        given(defenseSlot.getRightEquipped()).willReturn(Arrays.asList(RIGHT_DEFENSE_ID));
        given(defenseSlot.getBackEquipped()).willReturn(Arrays.asList(BACK_DEFENSE_ID));

        given(weaponSlot.getFrontEquipped()).willReturn(Arrays.asList(FRONT_WEAPON_ID));
        given(weaponSlot.getLeftEquipped()).willReturn(Arrays.asList(LEFT_WEAPON_ID));
        given(weaponSlot.getRightEquipped()).willReturn(Arrays.asList(RIGHT_WEAPON_ID));
        given(weaponSlot.getBackEquipped()).willReturn(Arrays.asList(BACK_WEAPON_ID));
        //WHEN
        ShipEquipments result = underTest.aggregateEquipments(CHARACTER_ID);
        //THEN
        assertThat(result.getShipId()).isEqualTo(SHIP_ID);

        assertThat(result.getFrontDefense()).containsExactly(FRONT_DEFENSE_ID);
        assertThat(result.getLeftDefense()).containsExactly(LEFT_DEFENSE_ID);
        assertThat(result.getRightDefense()).containsExactly(RIGHT_DEFENSE_ID);
        assertThat(result.getBackDefense()).containsExactly(BACK_DEFENSE_ID);

        assertThat(result.getFrontWeapon()).containsExactly(FRONT_WEAPON_ID);
        assertThat(result.getLeftWeapon()).containsExactly(LEFT_WEAPON_ID);
        assertThat(result.getRightWeapon()).containsExactly(RIGHT_WEAPON_ID);
        assertThat(result.getBackWeapon()).containsExactly(BACK_WEAPON_ID);
    }
}