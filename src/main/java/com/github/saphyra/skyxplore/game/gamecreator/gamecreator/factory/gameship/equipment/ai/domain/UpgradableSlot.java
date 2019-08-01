package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.domain;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import com.github.saphyra.skyxplore.data.DataConstants;
import com.github.saphyra.skyxplore.data.domain.SlotType;
import com.github.saphyra.skyxplore.data.entity.Extender;
import com.github.saphyra.skyxplore.data.entity.Ship;
import com.github.saphyra.skyxplore.game.game.GameContext;
import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
//TODO unit test
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
        (shipEquipments, gameContext) -> getSideDefenseSlots(shipEquipments, gameContext) > shipEquipments.getFrontDefense().size(),
        ShipEquipments::getLeftDefense,
        () -> SlotType.DEFENSE
    ),
    RIGHT_DEFENSE(
        (shipEquipments, gameContext) -> getSideDefenseSlots(shipEquipments, gameContext) > shipEquipments.getFrontDefense().size(),
        ShipEquipments::getRightDefense,
        () -> SlotType.DEFENSE
    ),
    BACK_DEFENSE(
        (shipEquipments, gameContext) -> getBackDefenseSlots(shipEquipments, gameContext) > shipEquipments.getFrontDefense().size(),
        ShipEquipments::getBackDefense,
        () -> SlotType.DEFENSE
    ),
    FRONT_WEAPON(
        (shipEquipments, gameContext) -> getFrontWeaponSlots(shipEquipments, gameContext) > shipEquipments.getFrontDefense().size(),
        ShipEquipments::getFrontWeapon,
        () -> SlotType.WEAPON
    ),
    LEFT_WEAPON(
        (shipEquipments, gameContext) -> getSideWeaponSlots(shipEquipments, gameContext) > shipEquipments.getFrontDefense().size(),
        ShipEquipments::getLeftWeapon,
        () -> SlotType.WEAPON
    ),
    RIGHT_WEAPON(
        (shipEquipments, gameContext) -> getSideWeaponSlots(shipEquipments, gameContext) > shipEquipments.getFrontDefense().size(),
        ShipEquipments::getRightWeapon,
        () -> SlotType.WEAPON
    ),
    BACK_WEAPON(
        (shipEquipments, gameContext) -> getBackWeaponSlots(shipEquipments, gameContext) > shipEquipments.getFrontDefense().size(),
        ShipEquipments::getBackWeapon,
        () -> SlotType.WEAPON
    );

    @Getter(value = AccessLevel.PRIVATE)
    private final BiFunction<ShipEquipments, GameContext, Boolean> hasEmptySlot;

    @Getter(value = AccessLevel.PRIVATE)
    private final Function<ShipEquipments, List<String>> getItems;

    @Getter(value = AccessLevel.PRIVATE)
    private final Supplier<SlotType> getSlotTypes;

    @Override
    public boolean hasEmptySlot(ShipEquipments equipments, GameContext gameContext) {
        return getHasEmptySlot().apply(equipments, gameContext);
    }

    @Override
    public List<String> getEquipmentsOfSlot(ShipEquipments shipEquipments) {
        return getGetItems().apply(shipEquipments);
    }

    @Override
    public SlotType getSlotType() {
        return getSlotTypes.get();
    }

    private static int getConnectorSlots(ShipEquipments shipEquipments, GameContext gameContext) {
        Ship ship = getShip(shipEquipments.getShipId(), gameContext);
        return getExtendedSlots(shipEquipments, gameContext, ship.getConnector(), DataConstants.CONNECTOR_SLOT_NAME);
    }

    private static int getFrontDefenseSlots(ShipEquipments shipEquipments, GameContext gameContext) {
        Ship ship = getShip(shipEquipments.getShipId(), gameContext);
        return getExtendedSlots(shipEquipments, gameContext, ship.getDefense().getFront(), DataConstants.DEFENSE_SLOT_NAME);
    }

    private static int getSideDefenseSlots(ShipEquipments shipEquipments, GameContext gameContext) {
        Ship ship = getShip(shipEquipments.getShipId(), gameContext);
        return getExtendedSlots(shipEquipments, gameContext, ship.getDefense().getSide(), DataConstants.DEFENSE_SLOT_NAME);
    }

    private static int getBackDefenseSlots(ShipEquipments shipEquipments, GameContext gameContext) {
        Ship ship = getShip(shipEquipments.getShipId(), gameContext);
        return getExtendedSlots(shipEquipments, gameContext, ship.getDefense().getBack(), DataConstants.DEFENSE_SLOT_NAME);
    }

    private static int getFrontWeaponSlots(ShipEquipments shipEquipments, GameContext gameContext) {
        Ship ship = getShip(shipEquipments.getShipId(), gameContext);
        return getExtendedSlots(shipEquipments, gameContext, ship.getWeapon().getFront(), DataConstants.WEAPON_SLOT_NAME);
    }

    private static int getSideWeaponSlots(ShipEquipments shipEquipments, GameContext gameContext) {
        Ship ship = getShip(shipEquipments.getShipId(), gameContext);
        return getExtendedSlots(shipEquipments, gameContext, ship.getWeapon().getSide(), DataConstants.WEAPON_SLOT_NAME);
    }

    private static int getBackWeaponSlots(ShipEquipments shipEquipments, GameContext gameContext) {
        Ship ship = getShip(shipEquipments.getShipId(), gameContext);
        return getExtendedSlots(shipEquipments, gameContext, ship.getWeapon().getBack(), DataConstants.WEAPON_SLOT_NAME);
    }

    private static Ship getShip(String shipId, GameContext gameContext) {
        return gameContext.getShipService().get(shipId);
    }

    private static int getExtendedSlots(ShipEquipments shipEquipments, GameContext gameContext, int connectorSlots, String extendedSlot) {
        for (String itemId : shipEquipments.getConnectorEquipped()) {
            Extender extender = gameContext.getExtenderService().get(itemId);
            if (extender != null && extender.getExtendedSlot().equals(extendedSlot)) {
                connectorSlots += extender.getExtendedNum();
            }
        }

        return connectorSlots;
    }
}
