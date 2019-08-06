package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai;


import com.github.saphyra.skyxplore.game.game.domain.ship.GameShip;
import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.domain.UpgradableItem;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.item.ItemProviderFacade;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.upgradeableitem.UpgradeableItemProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class AiShipEquipmentGenerator {
    private final ExistingItemUpgrader existingItemUpgrader;
    private final ItemProviderFacade itemProviderFacade;
    private final NewItemEquiper newItemEquiper;
    private final PointCalculator pointCalculator;
    private final TargetPointCalculator targetPointCalculator;
    private final UpgradeableItemProvider upgradeableItemProvider;

    public ShipEquipments generateEquipments(List<GameShip> gameShips) {
        int targetPoint = targetPointCalculator.getTargetPoints(gameShips);

        ShipEquipments shipEquipments = fillWithEquipments(targetPoint);
        log.debug("Generated shipEquipments: {}", shipEquipments);
        return shipEquipments;
    }

    private ShipEquipments fillWithEquipments(int targetPoint) {
        ShipEquipments shipEquipments = ShipEquipments.builder()
            .shipId(itemProviderFacade.getRandomShip())
            .build();

        while (pointCalculator.countPointsOfEquipments(shipEquipments) < targetPoint) {
            UpgradableItem upgradableItem = upgradeableItemProvider.getUpgradableItem(shipEquipments);
            if (hasUpgradableSlot(upgradableItem)) {
                if (hasUpgradableItem(upgradableItem)) {
                    existingItemUpgrader.upgradeExistingItem(shipEquipments, upgradableItem);
                } else {
                    newItemEquiper.equipNewItem(shipEquipments, upgradableItem.getUpgradableSlot().get());
                }
            } else {
                Optional<String> upgradedItem = itemProviderFacade.getUpgradedVersionOf(shipEquipments.getShipId());
                log.debug("Upgrading ship to {}", upgradedItem);
                if (!upgradedItem.isPresent()) {
                    log.debug("Ship cannot be upgraded.");
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
}
