package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.data.GameDataFacade;
import com.github.saphyra.skyxplore.data.entity.EquipmentDescription;
import com.github.saphyra.skyxplore.game.game.domain.ship.GameShip;
import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.domain.PointRange;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
//TODO unit test
public class PointRangeCalculator {
    private final GameDataFacade gameDataFacade;
    private final PointRangeConfiguration pointRangeConfiguration;

    PointRange calculate(List<GameShip> gameShips) {
        List<Integer> points = gameShips.stream()
            .map(GameShip::getShipEquipments)
            .map(this::countPointsOfEquipments)
            .collect(Collectors.toList());
        log.debug("Points: {}", points);

        int min = points.stream()
            .mapToInt(Integer::intValue)
            .min()
            .getAsInt();
        log.debug("Lowest point: {}", min);

        int max = points.stream()
            .mapToInt(Integer::intValue)
            .max()
            .getAsInt();
        log.debug("Highest point: {}", max);

        PointRange pointRange = PointRange.builder()
            .minPoints(convertToInt(min * pointRangeConfiguration.getMinMultiplier()))
            .maxPoints(convertToInt(max * pointRangeConfiguration.getMaxMultiplier()))
            .build();
        log.debug("PointRange: {}", pointRange);
        return pointRange;
    }

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

    private int convertToInt(Double value) {
        return ((Long) Math.round(value)).intValue();
    }
}
