package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai;

import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.domain.UpgradableItem;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.item.ItemProviderFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class ExistingItemUpgrader {
    private final ItemProviderFacade itemProviderFacade;

    void upgradeExistingItem(ShipEquipments shipEquipments, UpgradableItem upgradableItem) {
        String itemId = upgradableItem.getUpgradeableItemId().get();
        log.debug("Upgrading existing item with itemId {}", itemId);
        Optional<String> upgradedItem = itemProviderFacade.getUpgradedVersionOf(itemId);
        log.debug("New itemId: {}", upgradedItem);
        List<String> equippedItems = upgradableItem.getUpgradableSlot().get().getEquipmentsOfSlot(shipEquipments);
        log.debug("EquippedItems before update: {}", equippedItems);
        equippedItems.remove(itemId);
        equippedItems.add(upgradedItem.orElseThrow(() -> new RuntimeException(itemId + " cannot be updated.")));
        log.debug("EquippedItems after update: {}", equippedItems);
    }
}
