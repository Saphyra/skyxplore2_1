package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.item;

import com.github.saphyra.skyxplore.data.GameDataFacade;
import com.github.saphyra.skyxplore.data.entity.EquipmentDescription;
import com.github.saphyra.skyxplore.data.entity.GeneralDescription;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class UpgradedItemProvider {
    private static final String ITEM_ID_DELIMITER = "-0";

    private final GameDataFacade gameDataFacade;

    Optional<String> getUpgradedVersionOf(String itemId) {
        EquipmentDescription equipmentDescription = gameDataFacade.findEquipmentDescription(itemId);
        String[] splitted = itemId.split(ITEM_ID_DELIMITER);
        String newItemId = splitted[0] + ITEM_ID_DELIMITER + (equipmentDescription.getLevel() + 1);
        Optional<String> result = Optional.ofNullable(gameDataFacade.findEquipmentDescription(newItemId))
            .map(GeneralDescription::getId);
        log.debug("Upgraded item of {} is: {}", itemId, result);
        return result;
    }
}
