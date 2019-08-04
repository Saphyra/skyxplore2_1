package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai;

import com.github.saphyra.skyxplore.game.game.domain.ship.GameShip;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.domain.PointRange;
import com.github.saphyra.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
class TargetPointCalculator {
    private final PointRangeCalculator pointRangeCalculator;
    private final Random random;

    int getTargetPoints(List<GameShip> gameShips) {
        PointRange pointRange = pointRangeCalculator.calculatePointRange(gameShips);
        int targetPoints = random.randInt(pointRange.getMinPoints(), pointRange.getMaxPoints());
        log.debug("targetPoints: {}", targetPoints);
        return targetPoints;
    }
}
