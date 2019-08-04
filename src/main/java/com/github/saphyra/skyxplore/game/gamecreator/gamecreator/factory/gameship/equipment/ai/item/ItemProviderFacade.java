package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.item;

import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.domain.UpgradableSlot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ItemProviderFacade {
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
