package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai;

import com.github.saphyra.skyxplore.data.GameDataFacade;
import com.github.saphyra.skyxplore.data.entity.EquipmentDescription;
import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
@Slf4j
class PointCalculator {
    private final GameDataFacade gameDataFacade;

    Integer countPointsOfEquipments(ShipEquipments shipEquipments) {
        int pointsOfShip = Stream.of(
            Arrays.asList(shipEquipments.getShipId()),
            shipEquipments.getConnectorEquipped(),
            shipEquipments.getFrontDefense(),
            shipEquipments.getLeftDefense(),
            shipEquipments.getRightDefense(),
            shipEquipments.getBackDefense(),
            shipEquipments.getFrontWeapon(),
            shipEquipments.getLeftWeapon(),
            shipEquipments.getRightWeapon(),
            shipEquipments.getBackWeapon()
        )
            .flatMap(Collection::stream)
            .map(gameDataFacade::findEquipmentDescription)
            .mapToInt(EquipmentDescription::getScore)
            .sum();
        log.debug("Calculated points of equipments: {}", pointsOfShip);
        return pointsOfShip;
    }
}
