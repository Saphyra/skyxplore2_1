package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.upgradeableitem;

import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.domain.UpgradableSlot;
import com.github.saphyra.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@SuppressWarnings({"ComparatorMethodParameterNotUsed", "SimplifyStreamApiCallChains"})
@Component
@RequiredArgsConstructor
@Slf4j
//TODO unit test
class RandomUpgradeableSlotProvider {
    private final Random random;
    private final RandomUpgradeableItemOfSlotProvider randomUpgradeableItemOfSlotProvider;

    /**
     * @return UpgradableSlot if exists with an upgradeable item. Optional.empty otherwise.
     */
    Optional<UpgradableSlot> getRandomUpgradeableSlot(ShipEquipments equipments) {
        Optional<UpgradableSlot> result = Arrays.stream(UpgradableSlot.values())
            .filter(upgradableSlot -> randomUpgradeableItemOfSlotProvider.getRandomUpgradableItemOfSlot(upgradableSlot.getEquipmentsOfSlot(equipments)).isPresent())
            .sorted((o1, o2) -> random.randInt(-1, 1))
            .findFirst();
        log.debug("Random Upgradeable slot: {}", result);
        return result;
    }
}
