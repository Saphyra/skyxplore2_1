package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.item;

import static java.util.Objects.isNull;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.data.GameDataFacade;
import com.github.saphyra.skyxplore.data.domain.SlotType;
import com.github.saphyra.skyxplore.data.entity.Extender;
import com.github.saphyra.skyxplore.data.entity.GeneralDescription;
import com.github.saphyra.skyxplore.data.subservice.ExtenderService;
import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.domain.UpgradableSlot;
import com.github.saphyra.util.Random;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
//TODO unit test
class RandomItemProvider {
    private final ExtenderService extenderService;
    private final GameDataFacade gameDataFacade;
    private final Random random;

    String getRandomItem(UpgradableSlot slot, ShipEquipments shipEquipments) {
        SlotType slotType = slot.getSlotType();
        List<String> itemIds = gameDataFacade.getEquipmentDescriptionBySlotAndLevel(slotType, 1).stream()
            .map(GeneralDescription::getId)
            .filter(itemId -> slot != UpgradableSlot.CONNECTOR || extenderNotEquipped(itemId, shipEquipments.getConnectorEquipped()))
            .collect(Collectors.toList());

        return itemIds.get(random.randInt(0, itemIds.size()));
    }

    private boolean extenderNotEquipped(String itemId, List<String> connectorEquipped) {
        Extender extender = extenderService.get(itemId);
        if (isNull(extender)) {
            return true;
        }

        return connectorEquipped.stream()
            .filter(i -> !isNull(extenderService.get(i)))
            .map(extenderService::get)
            .anyMatch(e -> e.getExtendedSlot().equals(extender.getExtendedSlot()));
    }
}
