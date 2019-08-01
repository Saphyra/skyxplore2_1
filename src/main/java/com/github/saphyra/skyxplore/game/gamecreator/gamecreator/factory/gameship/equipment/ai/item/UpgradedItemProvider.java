package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.item;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.data.GameDataFacade;
import com.github.saphyra.skyxplore.data.entity.EquipmentDescription;
import com.github.saphyra.skyxplore.data.entity.GeneralDescription;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
//TODO unit test
class UpgradedItemProvider {
    private static final String ITEM_ID_DELIMITER = "-0";

    private final GameDataFacade gameDataFacade;

    Optional<String> getUpgradedVersionOf(String itemId) {
        EquipmentDescription equipmentDescription = gameDataFacade.findEquipmentDescription(itemId);
        String[] splitted = itemId.split(ITEM_ID_DELIMITER);
        String newItemId = splitted[0] + ITEM_ID_DELIMITER + (equipmentDescription.getLevel() + 1);
        return Optional.ofNullable(gameDataFacade.findEquipmentDescription(newItemId))
            .map(GeneralDescription::getId);
    }
}
