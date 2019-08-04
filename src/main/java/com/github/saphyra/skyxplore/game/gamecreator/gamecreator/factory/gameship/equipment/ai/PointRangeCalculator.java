package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai;

import com.github.saphyra.skyxplore.game.game.domain.ship.GameShip;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.domain.PointRange;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
class PointRangeCalculator {
    private final PointCalculator pointCalculator;
    private final PointRangeConfiguration pointRangeConfiguration;

    PointRange calculatePointRange(List<GameShip> gameShips) {
        List<Integer> points = gameShips.stream()
            .map(GameShip::getShipEquipments)
            .map(pointCalculator::countPointsOfEquipments)
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

    private int convertToInt(Double value) {
        return ((Long) Math.round(value)).intValue();
    }
}
