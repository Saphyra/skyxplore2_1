package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.domain;

import com.github.saphyra.skyxplore.data.domain.SlotType;
import com.github.saphyra.skyxplore.data.entity.Extender;
import com.github.saphyra.skyxplore.data.entity.Ship;
import com.github.saphyra.skyxplore.data.entity.Slot;
import com.github.saphyra.skyxplore.data.subservice.ExtenderService;
import com.github.saphyra.skyxplore.data.subservice.ShipService;
import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;
import com.github.saphyra.skyxplore.game.gamecreator.GameCreatorContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static com.github.saphyra.skyxplore.data.DataConstants.CONNECTOR_SLOT_NAME;
import static com.github.saphyra.skyxplore.data.DataConstants.DEFENSE_SLOT_NAME;
import static com.github.saphyra.skyxplore.data.DataConstants.WEAPON_SLOT_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class UpgradableSlotTest {
    private static final String SHIP_ID = "ship_id";
    private static final String ITEM_ID = "item_id";
    private static final String EXTENDER_ID = "extender_id";

    @Mock
    private ExtenderService extenderService;

    @Mock
    private ShipService shipService;

    @Mock
    private GameCreatorContext gameCreatorContext;

    @Mock
    private Extender extender;

    @Mock
    private Ship ship;

    @Mock
    private Slot slot;

    @Mock
    private ShipEquipments shipEquipments;

    @Before
    public void setUp() {
        given(shipEquipments.getShipId()).willReturn(SHIP_ID);

        given(gameCreatorContext.getShipService()).willReturn(shipService);
        given(gameCreatorContext.getExtenderService()).willReturn(extenderService);

        given(extenderService.get(EXTENDER_ID)).willReturn(extender);
        given(shipService.get(SHIP_ID)).willReturn(ship);
    }

    @Test
    public void connector_hasEmptySlot_true() {
        //GIVEN
        given(ship.getConnector()).willReturn(2);
        given(shipEquipments.getConnectorEquipped()).willReturn(Arrays.asList(ITEM_ID, EXTENDER_ID));
        given(extender.getExtendedSlot()).willReturn(CONNECTOR_SLOT_NAME);
        given(extender.getExtendedNum()).willReturn(1);
        //WHEN
        boolean result = UpgradableSlot.CONNECTOR.hasEmptySlot(shipEquipments, gameCreatorContext);
        //THEN
        assertThat(result).isTrue();
    }

    @Test
    public void connector_hasEmptySlot_false() {
        //GIVEN
        given(ship.getConnector()).willReturn(1);
        given(shipEquipments.getConnectorEquipped()).willReturn(Arrays.asList(ITEM_ID, EXTENDER_ID));
        given(extender.getExtendedSlot()).willReturn(CONNECTOR_SLOT_NAME);
        given(extender.getExtendedNum()).willReturn(1);
        //WHEN
        boolean result = UpgradableSlot.CONNECTOR.hasEmptySlot(shipEquipments, gameCreatorContext);
        //THEN
        assertThat(result).isFalse();
    }

    @Test
    public void connector_getEquipmentsOfSlot() {
        //GIVEN
        given(shipEquipments.getConnectorEquipped()).willReturn(Arrays.asList(ITEM_ID));
        //WHEN
        List<String> result = UpgradableSlot.CONNECTOR.getEquipmentsOfSlot(shipEquipments);
        //THEN
        assertThat(result).containsExactly(ITEM_ID);
    }

    @Test
    public void connector_getSlotType() {
        //WHEN
        SlotType result = UpgradableSlot.CONNECTOR.getSlotType();
        //THEN
        assertThat(result).isEqualTo(SlotType.CONNECTOR);
    }

    /////
    @Test
    public void frontDefense_hasEmptySlot_true() {
        //GIVEN
        given(ship.getDefense()).willReturn(slot);
        given(slot.getFront()).willReturn(1);
        given(shipEquipments.getConnectorEquipped()).willReturn(Arrays.asList(EXTENDER_ID));
        given(shipEquipments.getFrontDefense()).willReturn(Arrays.asList(ITEM_ID));
        given(extender.getExtendedSlot()).willReturn(DEFENSE_SLOT_NAME);
        given(extender.getExtendedNum()).willReturn(1);
        //WHEN
        boolean result = UpgradableSlot.FRONT_DEFENSE.hasEmptySlot(shipEquipments, gameCreatorContext);
        //THEN
        assertThat(result).isTrue();
    }

    @Test
    public void frontDefense_hasEmptySlot_false() {
        //GIVEN
        given(ship.getDefense()).willReturn(slot);
        given(slot.getFront()).willReturn(1);
        given(shipEquipments.getConnectorEquipped()).willReturn(Arrays.asList(EXTENDER_ID));
        given(shipEquipments.getFrontDefense()).willReturn(Arrays.asList(ITEM_ID, ITEM_ID));
        given(extender.getExtendedSlot()).willReturn(DEFENSE_SLOT_NAME);
        given(extender.getExtendedNum()).willReturn(1);
        //WHEN
        boolean result = UpgradableSlot.FRONT_DEFENSE.hasEmptySlot(shipEquipments, gameCreatorContext);
        //THEN
        assertThat(result).isFalse();
    }

    @Test
    public void frontDefense_getEquipmentsOfSlot() {
        //GIVEN
        given(shipEquipments.getFrontDefense()).willReturn(Arrays.asList(ITEM_ID));
        //WHEN
        List<String> result = UpgradableSlot.FRONT_DEFENSE.getEquipmentsOfSlot(shipEquipments);
        //THEN
        assertThat(result).containsExactly(ITEM_ID);
    }

    @Test
    public void frontDefense_getSlotType() {
        //WHEN
        SlotType result = UpgradableSlot.FRONT_DEFENSE.getSlotType();
        //THEN
        assertThat(result).isEqualTo(SlotType.DEFENSE);
    }

    /////
    @Test
    public void leftDefense_hasEmptySlot_true() {
        //GIVEN
        given(ship.getDefense()).willReturn(slot);
        given(slot.getSide()).willReturn(1);
        given(shipEquipments.getConnectorEquipped()).willReturn(Arrays.asList(EXTENDER_ID));
        given(shipEquipments.getLeftDefense()).willReturn(Arrays.asList(ITEM_ID));
        given(extender.getExtendedSlot()).willReturn(DEFENSE_SLOT_NAME);
        given(extender.getExtendedNum()).willReturn(1);
        //WHEN
        boolean result = UpgradableSlot.LEFT_DEFENSE.hasEmptySlot(shipEquipments, gameCreatorContext);
        //THEN
        assertThat(result).isTrue();
    }

    @Test
    public void leftDefense_hasEmptySlot_false() {
        //GIVEN
        given(ship.getDefense()).willReturn(slot);
        given(slot.getSide()).willReturn(1);
        given(shipEquipments.getConnectorEquipped()).willReturn(Arrays.asList(EXTENDER_ID));
        given(shipEquipments.getLeftDefense()).willReturn(Arrays.asList(ITEM_ID, ITEM_ID));
        given(extender.getExtendedSlot()).willReturn(DEFENSE_SLOT_NAME);
        given(extender.getExtendedNum()).willReturn(1);
        //WHEN
        boolean result = UpgradableSlot.LEFT_DEFENSE.hasEmptySlot(shipEquipments, gameCreatorContext);
        //THEN
        assertThat(result).isFalse();
    }

    @Test
    public void leftDefense_getEquipmentsOfSlot() {
        //GIVEN
        given(shipEquipments.getLeftDefense()).willReturn(Arrays.asList(ITEM_ID));
        //WHEN
        List<String> result = UpgradableSlot.LEFT_DEFENSE.getEquipmentsOfSlot(shipEquipments);
        //THEN
        assertThat(result).containsExactly(ITEM_ID);
    }

    @Test
    public void leftDefense_getSlotType() {
        //WHEN
        SlotType result = UpgradableSlot.LEFT_DEFENSE.getSlotType();
        //THEN
        assertThat(result).isEqualTo(SlotType.DEFENSE);
    }

    /////
    @Test
    public void rightDefense_hasEmptySlot_true() {
        //GIVEN
        given(ship.getDefense()).willReturn(slot);
        given(slot.getSide()).willReturn(1);
        given(shipEquipments.getConnectorEquipped()).willReturn(Arrays.asList(EXTENDER_ID));
        given(shipEquipments.getRightDefense()).willReturn(Arrays.asList(ITEM_ID));
        given(extender.getExtendedSlot()).willReturn(DEFENSE_SLOT_NAME);
        given(extender.getExtendedNum()).willReturn(1);
        //WHEN
        boolean result = UpgradableSlot.RIGHT_DEFENSE.hasEmptySlot(shipEquipments, gameCreatorContext);
        //THEN
        assertThat(result).isTrue();
    }

    @Test
    public void rightDefense_hasEmptySlot_false() {
        //GIVEN
        given(ship.getDefense()).willReturn(slot);
        given(slot.getSide()).willReturn(1);
        given(shipEquipments.getConnectorEquipped()).willReturn(Arrays.asList(EXTENDER_ID));
        given(shipEquipments.getRightDefense()).willReturn(Arrays.asList(ITEM_ID, ITEM_ID));
        given(extender.getExtendedSlot()).willReturn(DEFENSE_SLOT_NAME);
        given(extender.getExtendedNum()).willReturn(1);
        //WHEN
        boolean result = UpgradableSlot.RIGHT_DEFENSE.hasEmptySlot(shipEquipments, gameCreatorContext);
        //THEN
        assertThat(result).isFalse();
    }

    @Test
    public void rightDefense_getEquipmentsOfSlot() {
        //GIVEN
        given(shipEquipments.getRightDefense()).willReturn(Arrays.asList(ITEM_ID));
        //WHEN
        List<String> result = UpgradableSlot.RIGHT_DEFENSE.getEquipmentsOfSlot(shipEquipments);
        //THEN
        assertThat(result).containsExactly(ITEM_ID);
    }

    @Test
    public void rightDefense_getSlotType() {
        //WHEN
        SlotType result = UpgradableSlot.RIGHT_DEFENSE.getSlotType();
        //THEN
        assertThat(result).isEqualTo(SlotType.DEFENSE);
    }

    /////
    @Test
    public void backDefense_hasEmptySlot_true() {
        //GIVEN
        given(ship.getDefense()).willReturn(slot);
        given(slot.getBack()).willReturn(1);
        given(shipEquipments.getConnectorEquipped()).willReturn(Arrays.asList(EXTENDER_ID));
        given(shipEquipments.getBackDefense()).willReturn(Arrays.asList(ITEM_ID));
        given(extender.getExtendedSlot()).willReturn(DEFENSE_SLOT_NAME);
        given(extender.getExtendedNum()).willReturn(1);
        //WHEN
        boolean result = UpgradableSlot.BACK_DEFENSE.hasEmptySlot(shipEquipments, gameCreatorContext);
        //THEN
        assertThat(result).isTrue();
    }

    @Test
    public void backDefense_hasEmptySlot_false() {
        //GIVEN
        given(ship.getDefense()).willReturn(slot);
        given(slot.getBack()).willReturn(1);
        given(shipEquipments.getConnectorEquipped()).willReturn(Arrays.asList(EXTENDER_ID));
        given(shipEquipments.getBackDefense()).willReturn(Arrays.asList(ITEM_ID, ITEM_ID));
        given(extender.getExtendedSlot()).willReturn(DEFENSE_SLOT_NAME);
        given(extender.getExtendedNum()).willReturn(1);
        //WHEN
        boolean result = UpgradableSlot.BACK_DEFENSE.hasEmptySlot(shipEquipments, gameCreatorContext);
        //THEN
        assertThat(result).isFalse();
    }

    @Test
    public void backDefense_getEquipmentsOfSlot() {
        //GIVEN
        given(shipEquipments.getBackDefense()).willReturn(Arrays.asList(ITEM_ID));
        //WHEN
        List<String> result = UpgradableSlot.BACK_DEFENSE.getEquipmentsOfSlot(shipEquipments);
        //THEN
        assertThat(result).containsExactly(ITEM_ID);
    }

    @Test
    public void backDefense_getSlotType() {
        //WHEN
        SlotType result = UpgradableSlot.BACK_DEFENSE.getSlotType();
        //THEN
        assertThat(result).isEqualTo(SlotType.DEFENSE);
    }

    /////
    @Test
    public void frontWeapon_hasEmptySlot_true() {
        //GIVEN
        given(ship.getWeapon()).willReturn(slot);
        given(slot.getFront()).willReturn(1);
        given(shipEquipments.getConnectorEquipped()).willReturn(Arrays.asList(EXTENDER_ID));
        given(shipEquipments.getFrontWeapon()).willReturn(Arrays.asList(ITEM_ID));
        given(extender.getExtendedSlot()).willReturn(WEAPON_SLOT_NAME);
        given(extender.getExtendedNum()).willReturn(1);
        //WHEN
        boolean result = UpgradableSlot.FRONT_WEAPON.hasEmptySlot(shipEquipments, gameCreatorContext);
        //THEN
        assertThat(result).isTrue();
    }

    @Test
    public void frontWeapon_hasEmptySlot_false() {
        //GIVEN
        given(ship.getWeapon()).willReturn(slot);
        given(slot.getFront()).willReturn(1);
        given(shipEquipments.getConnectorEquipped()).willReturn(Arrays.asList(EXTENDER_ID));
        given(shipEquipments.getFrontWeapon()).willReturn(Arrays.asList(ITEM_ID, ITEM_ID));
        given(extender.getExtendedSlot()).willReturn(WEAPON_SLOT_NAME);
        given(extender.getExtendedNum()).willReturn(1);
        //WHEN
        boolean result = UpgradableSlot.FRONT_WEAPON.hasEmptySlot(shipEquipments, gameCreatorContext);
        //THEN
        assertThat(result).isFalse();
    }

    @Test
    public void frontWeapon_getEquipmentsOfSlot() {
        //GIVEN
        given(shipEquipments.getFrontWeapon()).willReturn(Arrays.asList(ITEM_ID));
        //WHEN
        List<String> result = UpgradableSlot.FRONT_WEAPON.getEquipmentsOfSlot(shipEquipments);
        //THEN
        assertThat(result).containsExactly(ITEM_ID);
    }

    @Test
    public void frontWeapon_getSlotType() {
        //WHEN
        SlotType result = UpgradableSlot.FRONT_WEAPON.getSlotType();
        //THEN
        assertThat(result).isEqualTo(SlotType.WEAPON);
    }

    /////
    @Test
    public void leftWeapon_hasEmptySlot_true() {
        //GIVEN
        given(ship.getWeapon()).willReturn(slot);
        given(slot.getSide()).willReturn(1);
        given(shipEquipments.getConnectorEquipped()).willReturn(Arrays.asList(EXTENDER_ID));
        given(shipEquipments.getLeftWeapon()).willReturn(Arrays.asList(ITEM_ID));
        given(extender.getExtendedSlot()).willReturn(WEAPON_SLOT_NAME);
        given(extender.getExtendedNum()).willReturn(1);
        //WHEN
        boolean result = UpgradableSlot.LEFT_WEAPON.hasEmptySlot(shipEquipments, gameCreatorContext);
        //THEN
        assertThat(result).isTrue();
    }

    @Test
    public void leftWeapon_hasEmptySlot_false() {
        //GIVEN
        given(ship.getWeapon()).willReturn(slot);
        given(slot.getSide()).willReturn(1);
        given(shipEquipments.getConnectorEquipped()).willReturn(Arrays.asList(EXTENDER_ID));
        given(shipEquipments.getLeftWeapon()).willReturn(Arrays.asList(ITEM_ID, ITEM_ID));
        given(extender.getExtendedSlot()).willReturn(WEAPON_SLOT_NAME);
        given(extender.getExtendedNum()).willReturn(1);
        //WHEN
        boolean result = UpgradableSlot.LEFT_WEAPON.hasEmptySlot(shipEquipments, gameCreatorContext);
        //THEN
        assertThat(result).isFalse();
    }

    @Test
    public void leftWeapon_getEquipmentsOfSlot() {
        //GIVEN
        given(shipEquipments.getLeftWeapon()).willReturn(Arrays.asList(ITEM_ID));
        //WHEN
        List<String> result = UpgradableSlot.LEFT_WEAPON.getEquipmentsOfSlot(shipEquipments);
        //THEN
        assertThat(result).containsExactly(ITEM_ID);
    }

    @Test
    public void leftWeapon_getSlotType() {
        //WHEN
        SlotType result = UpgradableSlot.LEFT_WEAPON.getSlotType();
        //THEN
        assertThat(result).isEqualTo(SlotType.WEAPON);
    }

    /////
    @Test
    public void rightWeapon_hasEmptySlot_true() {
        //GIVEN
        given(ship.getWeapon()).willReturn(slot);
        given(slot.getSide()).willReturn(1);
        given(shipEquipments.getConnectorEquipped()).willReturn(Arrays.asList(EXTENDER_ID));
        given(shipEquipments.getRightWeapon()).willReturn(Arrays.asList(ITEM_ID));
        given(extender.getExtendedSlot()).willReturn(WEAPON_SLOT_NAME);
        given(extender.getExtendedNum()).willReturn(1);
        //WHEN
        boolean result = UpgradableSlot.RIGHT_WEAPON.hasEmptySlot(shipEquipments, gameCreatorContext);
        //THEN
        assertThat(result).isTrue();
    }

    @Test
    public void rightWeapon_hasEmptySlot_false() {
        //GIVEN
        given(ship.getWeapon()).willReturn(slot);
        given(slot.getSide()).willReturn(1);
        given(shipEquipments.getConnectorEquipped()).willReturn(Arrays.asList(EXTENDER_ID));
        given(shipEquipments.getRightWeapon()).willReturn(Arrays.asList(ITEM_ID, ITEM_ID));
        given(extender.getExtendedSlot()).willReturn(WEAPON_SLOT_NAME);
        given(extender.getExtendedNum()).willReturn(1);
        //WHEN
        boolean result = UpgradableSlot.RIGHT_WEAPON.hasEmptySlot(shipEquipments, gameCreatorContext);
        //THEN
        assertThat(result).isFalse();
    }

    @Test
    public void rightWeapon_getEquipmentsOfSlot() {
        //GIVEN
        given(shipEquipments.getRightWeapon()).willReturn(Arrays.asList(ITEM_ID));
        //WHEN
        List<String> result = UpgradableSlot.RIGHT_WEAPON.getEquipmentsOfSlot(shipEquipments);
        //THEN
        assertThat(result).containsExactly(ITEM_ID);
    }

    @Test
    public void rightWeapon_getSlotType() {
        //WHEN
        SlotType result = UpgradableSlot.RIGHT_WEAPON.getSlotType();
        //THEN
        assertThat(result).isEqualTo(SlotType.WEAPON);
    }

    /////
    @Test
    public void backWeapon_hasEmptySlot_true() {
        //GIVEN
        given(ship.getWeapon()).willReturn(slot);
        given(slot.getBack()).willReturn(1);
        given(shipEquipments.getConnectorEquipped()).willReturn(Arrays.asList(EXTENDER_ID));
        given(shipEquipments.getBackWeapon()).willReturn(Arrays.asList(ITEM_ID));
        given(extender.getExtendedSlot()).willReturn(WEAPON_SLOT_NAME);
        given(extender.getExtendedNum()).willReturn(1);
        //WHEN
        boolean result = UpgradableSlot.BACK_WEAPON.hasEmptySlot(shipEquipments, gameCreatorContext);
        //THEN
        assertThat(result).isTrue();
    }

    @Test
    public void backWeapon_hasEmptySlot_false() {
        //GIVEN
        given(ship.getWeapon()).willReturn(slot);
        given(slot.getBack()).willReturn(1);
        given(shipEquipments.getConnectorEquipped()).willReturn(Arrays.asList(EXTENDER_ID));
        given(shipEquipments.getBackWeapon()).willReturn(Arrays.asList(ITEM_ID, ITEM_ID));
        given(extender.getExtendedSlot()).willReturn(WEAPON_SLOT_NAME);
        given(extender.getExtendedNum()).willReturn(1);
        //WHEN
        boolean result = UpgradableSlot.BACK_WEAPON.hasEmptySlot(shipEquipments, gameCreatorContext);
        //THEN
        assertThat(result).isFalse();
    }

    @Test
    public void backWeapon_getEquipmentsOfSlot() {
        //GIVEN
        given(shipEquipments.getBackWeapon()).willReturn(Arrays.asList(ITEM_ID));
        //WHEN
        List<String> result = UpgradableSlot.BACK_WEAPON.getEquipmentsOfSlot(shipEquipments);
        //THEN
        assertThat(result).containsExactly(ITEM_ID);
    }

    @Test
    public void backWeapon_getSlotType() {
        //WHEN
        SlotType result = UpgradableSlot.BACK_WEAPON.getSlotType();
        //THEN
        assertThat(result).isEqualTo(SlotType.WEAPON);
    }
}