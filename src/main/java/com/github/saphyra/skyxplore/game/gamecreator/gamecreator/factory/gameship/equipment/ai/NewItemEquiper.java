package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai;

import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.domain.UpgradableSlot;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.item.ItemProviderFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
class NewItemEquiper {
    private final ItemProviderFacade itemProviderFacade;

    void equipNewItem(ShipEquipments shipEquipments, UpgradableSlot upgradableSlot) {
        String randomItem = itemProviderFacade.getRandomItem(upgradableSlot, shipEquipments);
        List<String> equipmentsOfSlot = upgradableSlot.getEquipmentsOfSlot(shipEquipments);
        equipmentsOfSlot.add(randomItem);
    }
}
