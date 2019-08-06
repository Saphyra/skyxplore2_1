package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.upgradeableitem;

import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.domain.UpgradableSlot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class UpgradeableSlotProvider {
    private final RandomEmptySlotProvider randomEmptySlotProvider;
    private final RandomUpgradeableSlotProvider randomUpgradeableSlotProvider;

    /**
     * @return UpgradableSlot if there is a slot where an item can be equipped, or an item can be updated.
     * Optional.empty otherwise.
     */
    Optional<UpgradableSlot> getUpgradableSlot(ShipEquipments equipments) {
        Optional<UpgradableSlot> slot = randomEmptySlotProvider.getRandomEmptySlot(equipments);
        if (!slot.isPresent()) {
            log.debug("Ship has no more empty slots. Searching for upgradable item...");
            return randomUpgradeableSlotProvider.getRandomUpgradeableSlot(equipments);
        }
        return slot;
    }
}
