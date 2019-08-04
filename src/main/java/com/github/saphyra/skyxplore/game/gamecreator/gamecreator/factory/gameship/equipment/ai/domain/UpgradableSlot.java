package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.domain;

import com.github.saphyra.skyxplore.data.DataConstants;
import com.github.saphyra.skyxplore.data.domain.SlotType;
import com.github.saphyra.skyxplore.data.entity.Extender;
import com.github.saphyra.skyxplore.data.entity.Ship;
import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;
import com.github.saphyra.skyxplore.game.gamecreator.GameCreatorContext;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

@RequiredArgsConstructor
public enum UpgradableSlot implements UpgradableSlotMethods {
    CONNECTOR(
        (shipEquipments, gameContext) -> getConnectorSlots(shipEquipments, gameContext) > shipEquipments.getConnectorEquipped().size(),
        ShipEquipments::getConnectorEquipped,
        () -> SlotType.CONNECTOR
    ),
    FRONT_DEFENSE(
        (shipEquipments, gameContext) -> getFrontDefenseSlots(shipEquipments, gameContext) > shipEquipments.getFrontDefense().size(),
        ShipEquipments::getFrontDefense,
        () -> SlotType.DEFENSE
    ),
    LEFT_DEFENSE(
        (shipEquipments, gameContext) -> getSideDefenseSlots(shipEquipments, gameContext) > shipEquipments.getLeftDefense().size(),
        ShipEquipments::getLeftDefense,
        () -> SlotType.DEFENSE
    ),
    RIGHT_DEFENSE(
        (shipEquipments, gameContext) -> getSideDefenseSlots(shipEquipments, gameContext) > shipEquipments.getRightDefense().size(),
        ShipEquipments::getRightDefense,
        () -> SlotType.DEFENSE
    ),
    BACK_DEFENSE(
        (shipEquipments, gameContext) -> getBackDefenseSlots(shipEquipments, gameContext) > shipEquipments.getBackDefense().size(),
        ShipEquipments::getBackDefense,
        () -> SlotType.DEFENSE
    ),
    FRONT_WEAPON(
        (shipEquipments, gameContext) -> getFrontWeaponSlots(shipEquipments, gameContext) > shipEquipments.getFrontWeapon().size(),
        ShipEquipments::getFrontWeapon,
        () -> SlotType.WEAPON
    ),
    LEFT_WEAPON(
        (shipEquipments, gameContext) -> getSideWeaponSlots(shipEquipments, gameContext) > shipEquipments.getLeftWeapon().size(),
        ShipEquipments::getLeftWeapon,
        () -> SlotType.WEAPON
    ),
    RIGHT_WEAPON(
        (shipEquipments, gameContext) -> getSideWeaponSlots(shipEquipments, gameContext) > shipEquipments.getRightWeapon().size(),
        ShipEquipments::getRightWeapon,
        () -> SlotType.WEAPON
    ),
    BACK_WEAPON(
        (shipEquipments, gameContext) -> getBackWeaponSlots(shipEquipments, gameContext) > shipEquipments.getBackWeapon().size(),
        ShipEquipments::getBackWeapon,
        () -> SlotType.WEAPON
    ),
    TEST_HAS_EMPTY_SLOT(
        (shipEquipments, gameCreatorContext) -> true,
        shipEquipments -> null,
        () -> null
    ),
    TEST_HAS_NOT_EMPTY_SLOT(
        (shipEquipments, gameCreatorContext) -> false,
        ShipEquipments::getConnectorEquipped,
        () -> null
    );

    @Getter(value = AccessLevel.PRIVATE)
    private final BiFunction<ShipEquipments, GameCreatorContext, Boolean> hasEmptySlot;

    @Getter(value = AccessLevel.PRIVATE)
    private final Function<ShipEquipments, List<String>> getItems;

    @Getter(value = AccessLevel.PRIVATE)
    private final Supplier<SlotType> getSlotTypes;

    @Override
    public boolean hasEmptySlot(ShipEquipments equipments, GameCreatorContext gameCreatorContext) {
        return getHasEmptySlot().apply(equipments, gameCreatorContext);
    }

    @Override
    public List<String> getEquipmentsOfSlot(ShipEquipments shipEquipments) {
        return getGetItems().apply(shipEquipments);
    }

    @Override
    public SlotType getSlotType() {
        return getSlotTypes.get();
    }

    private static int getConnectorSlots(ShipEquipments shipEquipments, GameCreatorContext gameCreatorContext) {
        Ship ship = getShip(shipEquipments.getShipId(), gameCreatorContext);
        return getExtendedSlots(shipEquipments, gameCreatorContext, ship.getConnector(), DataConstants.CONNECTOR_SLOT_NAME);
    }

    private static int getFrontDefenseSlots(ShipEquipments shipEquipments, GameCreatorContext gameCreatorContext) {
        Ship ship = getShip(shipEquipments.getShipId(), gameCreatorContext);
        return getExtendedSlots(shipEquipments, gameCreatorContext, ship.getDefense().getFront(), DataConstants.DEFENSE_SLOT_NAME);
    }

    private static int getSideDefenseSlots(ShipEquipments shipEquipments, GameCreatorContext gameCreatorContext) {
        Ship ship = getShip(shipEquipments.getShipId(), gameCreatorContext);
        return getExtendedSlots(shipEquipments, gameCreatorContext, ship.getDefense().getSide(), DataConstants.DEFENSE_SLOT_NAME);
    }

    private static int getBackDefenseSlots(ShipEquipments shipEquipments, GameCreatorContext gameCreatorContext) {
        Ship ship = getShip(shipEquipments.getShipId(), gameCreatorContext);
        return getExtendedSlots(shipEquipments, gameCreatorContext, ship.getDefense().getBack(), DataConstants.DEFENSE_SLOT_NAME);
    }

    private static int getFrontWeaponSlots(ShipEquipments shipEquipments, GameCreatorContext gameCreatorContext) {
        Ship ship = getShip(shipEquipments.getShipId(), gameCreatorContext);
        return getExtendedSlots(shipEquipments, gameCreatorContext, ship.getWeapon().getFront(), DataConstants.WEAPON_SLOT_NAME);
    }

    private static int getSideWeaponSlots(ShipEquipments shipEquipments, GameCreatorContext gameCreatorContext) {
        Ship ship = getShip(shipEquipments.getShipId(), gameCreatorContext);
        return getExtendedSlots(shipEquipments, gameCreatorContext, ship.getWeapon().getSide(), DataConstants.WEAPON_SLOT_NAME);
    }

    private static int getBackWeaponSlots(ShipEquipments shipEquipments, GameCreatorContext gameCreatorContext) {
        Ship ship = getShip(shipEquipments.getShipId(), gameCreatorContext);
        return getExtendedSlots(shipEquipments, gameCreatorContext, ship.getWeapon().getBack(), DataConstants.WEAPON_SLOT_NAME);
    }

    private static Ship getShip(String shipId, GameCreatorContext gameCreatorContext) {
        return gameCreatorContext.getShipService().get(shipId);
    }

    private static int getExtendedSlots(ShipEquipments shipEquipments, GameCreatorContext gameCreatorContext, int connectorSlots, String extendedSlot) {
        for (String itemId : shipEquipments.getConnectorEquipped()) {
            Extender extender = gameCreatorContext.getExtenderService().get(itemId);
            if (extender != null && extender.getExtendedSlot().equals(extendedSlot)) {
                connectorSlots += extender.getExtendedNum();
            }
        }

        return connectorSlots;
    }
}
