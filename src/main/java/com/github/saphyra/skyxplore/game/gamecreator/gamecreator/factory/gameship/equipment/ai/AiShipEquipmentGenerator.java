package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.game.game.domain.Game;
import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.domain.PointRange;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.domain.UpgradableItem;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.domain.UpgradableSlot;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.item.ItemProvider;
import com.github.saphyra.util.Random;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
//TODO unit test
public class AiShipEquipmentGenerator {
    private final ItemProvider itemProvider;
    private final PointRangeCalculator pointRangeCalculator;
    private final Random random;
    private final UpgradableItemProvider upgradableItemProvider;

    public ShipEquipments generateEquipments(String characterId, Game game) {
        int targetPoint = getTargetPoints(game);

        return fillWithEquipments(targetPoint);
    }

    private int getTargetPoints(Game game) {
        PointRange pointRange = pointRangeCalculator.calculate(game.getShips());
        return random.randInt(pointRange.getMinPoints(), pointRange.getMaxPoints());
    }

    private ShipEquipments fillWithEquipments(int targetPoint) {
        ShipEquipments shipEquipments = ShipEquipments.builder()
            .shipId(itemProvider.getRandomShip())
            .build();

        while (pointRangeCalculator.countPointsOfEquipments(shipEquipments) < targetPoint) {
            UpgradableItem upgradableItem = upgradableItemProvider.getUpgradableItem(shipEquipments);
            if (hasUpgradableSlot(upgradableItem)) {
                if (hasUpgradableItem(upgradableItem)) {
                    upgradeExistingItem(shipEquipments, upgradableItem);
                } else {
                    equipNewItem(shipEquipments, upgradableItem);
                }
            } else {
                Optional<String> upgradedItem = itemProvider.getUpgradedVersionOf(shipEquipments.getShipId());
                if (!upgradedItem.isPresent()) {
                    break;
                }
                shipEquipments = shipEquipments.toBuilder()
                    .shipId(upgradedItem.get())
                    .build();
            }
        }

        return shipEquipments;
    }

    private boolean hasUpgradableSlot(UpgradableItem upgradableItem) {
        return upgradableItem.getUpgradableSlot().isPresent();
    }

    private boolean hasUpgradableItem(UpgradableItem upgradableItem) {
        return upgradableItem.getUpgradeableItemId().isPresent();
    }

    private void upgradeExistingItem(ShipEquipments shipEquipments, UpgradableItem upgradableItem) {
        String itemId = upgradableItem.getUpgradeableItemId().get();
        Optional<String> upgradedItem = itemProvider.getUpgradedVersionOf(itemId);
        List<String> equippedItems = upgradableItem.getUpgradableSlot().get().getEquipmentsOfSlot(shipEquipments);
        equippedItems.remove(itemId);
        equippedItems.add(upgradedItem.orElseThrow(() -> new RuntimeException(itemId + " cannot be updated.")));
    }

    private void equipNewItem(ShipEquipments shipEquipments, UpgradableItem upgradableItem) {
        UpgradableSlot slot = upgradableItem.getUpgradableSlot().get();
        slot.getEquipmentsOfSlot(shipEquipments).add(itemProvider.getRandomItem(slot, shipEquipments));
    }
}
