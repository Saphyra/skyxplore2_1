package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai;


import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.game.game.domain.Game;
import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.domain.PointRange;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.domain.UpgradableItem;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.item.ItemProvider;
import com.github.saphyra.util.Random;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
//TODO unit test
public class AiShipEquipmentGenerator {
    private final ItemProvider itemProvider;
    private final PointRangeCalculator pointRangeCalculator;
    private final Random random;
    private final UpgradableItemProvider upgradableItemProvider;

    public ShipEquipments generateEquipments(String characterId, Game game) {
        int targetPoint = getTargetPoints(game);

        return fillWithEquipments(targetPoint);
    }

    private int getTargetPoints(Game game) {
        PointRange pointRange = pointRangeCalculator.calculate(game.getShips());
        return random.randInt(pointRange.getMinPoints(), pointRange.getMaxPoints());
    }

    private ShipEquipments fillWithEquipments(int targetPoint) {
        ShipEquipments shipEquipments = ShipEquipments.builder()
            .shipId(itemProvider.getRandomShip())
            .build();

        while (pointRangeCalculator.countPointsOfEquipments(shipEquipments) < targetPoint) {
            UpgradableItem upgradableItem = upgradableItemProvider.getUpgradableItem(shipEquipments);
            if (upgradableItem.getUpgradableSlot().isPresent()) {
                if (upgradableItem.getUpgradeableItemId().isPresent()) {
                    //TODO Upgrade item
                } else {
                    //TODO add new random item
                }
            } else {
                //TODO update ship
            }
        }

        return shipEquipments;
    }
}
