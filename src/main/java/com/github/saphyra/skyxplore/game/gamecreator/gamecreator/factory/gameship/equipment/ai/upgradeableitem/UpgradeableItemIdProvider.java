package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.upgradeableitem;

import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;
import com.github.saphyra.skyxplore.game.gamecreator.GameCreatorContext;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.domain.UpgradableSlot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class UpgradeableItemIdProvider {
    private final GameCreatorContext gameCreatorContext;
    private final RandomUpgradeableItemOfSlotProvider randomUpgradeableItemOfSlotProvider;

    /**
     * @return A random upgradeable item, if the UpgradeableSlot has no empty slots. Optional.empty otherwise.
     */
    Optional<String> getUpgradableItemId(Optional<UpgradableSlot> upgradableSlot, ShipEquipments equipments) {
        if (upgradableSlot.isPresent() && upgradableSlot.get().hasEmptySlot(equipments, gameCreatorContext)) {
            log.debug("UpgradableSlot {} has empty slot.", upgradableSlot.get());
            return Optional.empty();
        }
        log.debug("No more empty slots in UpgradableSlot {}", upgradableSlot);
        return upgradableSlot.map(slot -> slot.getEquipmentsOfSlot(equipments))
            .flatMap(randomUpgradeableItemOfSlotProvider::getRandomUpgradableItemOfSlot);
    }
}
