package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.upgradeableitem;

import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.domain.UpgradableItem;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.domain.UpgradableSlot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class UpgradeableItemProvider {
    private final UpgradeableItemIdProvider upgradeableItemIdProvider;
    private final UpgradeableSlotProvider upgradeableSlotProvider;

    public UpgradableItem getUpgradableItem(ShipEquipments equipments) {
        Optional<UpgradableSlot> upgradableSlot = upgradeableSlotProvider.getUpgradableSlot(equipments);
        Optional<String> upgradableItemId = upgradeableItemIdProvider.getUpgradableItemId(upgradableSlot, equipments);
        UpgradableItem result = UpgradableItem.builder()
            .upgradableSlot(upgradableSlot)
            .upgradeableItemId(upgradableItemId)
            .build();
        log.debug("UpgradeableItem: {}", result);
        return result;
    }
}
