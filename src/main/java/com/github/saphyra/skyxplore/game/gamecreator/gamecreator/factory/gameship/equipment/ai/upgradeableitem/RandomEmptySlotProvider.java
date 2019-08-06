package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.upgradeableitem;

import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;
import com.github.saphyra.skyxplore.game.gamecreator.GameCreatorContext;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.domain.UpgradableSlot;
import com.github.saphyra.util.Random;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@SuppressWarnings({"ComparatorMethodParameterNotUsed", "SimplifyStreamApiCallChains"})
@Component
@RequiredArgsConstructor
@Slf4j
@Builder
class RandomEmptySlotProvider {
    private final GameCreatorContext gameCreatorContext;
    private final Random random;
    private final List<UpgradableSlot> upgradableSlots;

    /**
     * @return UpgradableSlot if exists with an empty slot. Optional.empty otherwise.
     */
    Optional<UpgradableSlot> getRandomEmptySlot(ShipEquipments equipments) {
        Optional<UpgradableSlot> result = upgradableSlots.stream()
            .filter(upgradableSlot -> upgradableSlot.hasEmptySlot(equipments, gameCreatorContext))
            .sorted((o1, o2) -> random.randInt(-1, 1))
            .findAny();
        log.debug("Random empty slot: {}", result);
        return result;
    }
}
