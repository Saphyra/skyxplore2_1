package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.upgradeableitem;

import com.github.saphyra.skyxplore.data.GameDataFacade;
import com.github.saphyra.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@SuppressWarnings({"ComparatorMethodParameterNotUsed", "SimplifyStreamApiCallChains"})
@Component
@RequiredArgsConstructor
@Slf4j
class RandomUpgradeableItemOfSlotProvider {
    private final GameDataFacade gameDataFacade;
    private final Random random;

    Optional<String> getRandomUpgradableItemOfSlot(List<String> equipmentsOfSlot) {
        log.debug("Equipments of slot: {}", equipmentsOfSlot);
        Optional<String> result = equipmentsOfSlot.stream()
            .filter(gameDataFacade::isUpgradable)
            .sorted((o1, o2) -> random.randInt(-1, 1))
            .findAny();
        log.debug("Random upgradeable item of slot {}", result);
        return result;
    }
}
