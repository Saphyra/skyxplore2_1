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
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.domain.PointRange;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
//TODO unit test
public class PointRangeCalculator {
    private final GameDataFacade gameDataFacade;
    private final PointRangeConfiguration pointRangeConfiguration;

    PointRange calculate(List<GameShip> gameShips) {
        List<Integer> points = gameShips.stream()
            .map(GameShip::getShipEquipments)
            .map(this::countPointsOfeEquipments)
            .collect(Collectors.toList());

        int min = points.stream()
            .mapToInt(Integer::intValue)
            .min()
            .getAsInt();
        int max = points.stream()
            .mapToInt(Integer::intValue)
            .max()
            .getAsInt();

        return PointRange.builder()
            .minPoints(convertToInt(min * pointRangeConfiguration.getMinMultiplier()))
            .maxPoints(convertToInt(max * pointRangeConfiguration.getMaxMultiplier()))
            .build();
    }

    private Integer countPointsOfeEquipments(ShipEquipments shipEquipments) {
        return Stream.of(
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
    }

    private int convertToInt(Double value) {
        return ((Long) Math.round(value)).intValue();
    }
}
