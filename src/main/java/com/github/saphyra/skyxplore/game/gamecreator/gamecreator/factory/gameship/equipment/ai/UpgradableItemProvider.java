package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.data.GameDataFacade;
import com.github.saphyra.skyxplore.game.game.GameContext;
import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.domain.UpgradableItem;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.domain.UpgradableSlot;
import com.github.saphyra.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SuppressWarnings({"ComparatorMethodParameterNotUsed", "SimplifyStreamApiCallChains"})
@Component
@RequiredArgsConstructor
@Slf4j
//TODO unit test
class UpgradableItemProvider {
    private final GameContext gameContext;
    private final GameDataFacade gameDataFacade;
    private final Random random;

    UpgradableItem getUpgradableItem(ShipEquipments equipments) {
        Optional<UpgradableSlot> upgradableSlot = getUpgradableSlot(equipments);
        Optional<String> upgradableItemId = upgradableSlot.map(slot -> slot.getEquipmentsOfSlot(equipments))
            .flatMap(this::getRandomUpgradableItemOfSlot);
        UpgradableItem result = UpgradableItem.builder()
            .upgradableSlot(upgradableSlot)
            .upgradeableItemId(upgradableItemId)
            .build();
        log.debug("UpgradeableItem: {}", result);
        return result;
    }

    private Optional<UpgradableSlot> getUpgradableSlot(ShipEquipments equipments) {
        Optional<UpgradableSlot> randomEmptySlot = getRandomEmptySlot(equipments);
        if (!randomEmptySlot.isPresent()) {
            return getRandomUpgradeableSlot(equipments);
        }
        return randomEmptySlot;
    }


    private Optional<UpgradableSlot> getRandomEmptySlot(ShipEquipments equipments) {
        Optional<UpgradableSlot> result = Arrays.stream(UpgradableSlot.values())
            .filter(upgradableSlot -> upgradableSlot.hasEmptySlot(equipments, gameContext))
            .sorted((o1, o2) -> random.randInt(-1, 1))
            .findFirst();
        log.debug("Random empty slot: {}", result);
        return result;
    }

    private Optional<UpgradableSlot> getRandomUpgradeableSlot(ShipEquipments equipments) {
        Optional<UpgradableSlot> result = Arrays.stream(UpgradableSlot.values())
            .filter(upgradableSlot -> getRandomUpgradableItemOfSlot(upgradableSlot.getEquipmentsOfSlot(equipments)).isPresent())
            .sorted((o1, o2) -> random.randInt(-1, 1))
            .findFirst();
        log.debug("Random Upgradeable slot: {}", result);
        return result;
    }

    private Optional<String> getRandomUpgradableItemOfSlot(List<String> equipmentsOfSlot) {
        log.debug("Equipments of slot: {}", equipmentsOfSlot);
        Optional<String> result = equipmentsOfSlot.stream()
            .filter(gameDataFacade::isUpgradable)
            .sorted((o1, o2) -> random.randInt(-1, 1))
            .findFirst();
        log.debug("Random upgradeable item of slot {}", result);
        return result;
    }
}
