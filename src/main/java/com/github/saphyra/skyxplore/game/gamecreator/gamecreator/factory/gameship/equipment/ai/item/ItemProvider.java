package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.item;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.domain.UpgradableSlot;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
//TODO unit test
public class ItemProvider {
    private final RandomItemProvider randomItemProvider;
    private final ShipProvider shipProvider;
    private final UpgradedItemProvider upgradedItemProvider;

    public String getRandomShip() {
        return shipProvider.getRandomShip();
    }

    public String getRandomItem(UpgradableSlot slot, ShipEquipments shipEquipments){
        return randomItemProvider.getRandomItem(slot, shipEquipments);
    }

    public Optional<String> getUpgradedVersionOf(String itemId) {
        return upgradedItemProvider.getUpgradedVersionOf(itemId);
    }
}
