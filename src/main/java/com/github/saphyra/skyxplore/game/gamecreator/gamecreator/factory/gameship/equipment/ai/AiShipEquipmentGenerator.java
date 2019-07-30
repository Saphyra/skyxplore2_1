package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai;


import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.game.game.domain.Game;
import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.domain.PointRange;
import com.github.saphyra.util.Random;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
//TODO unit test
public class AiShipEquipmentGenerator {
    private final PointRangeCalculator pointRangeCalculator;
    private final Random random;

    public ShipEquipments generateEquipments(String characterId, Game game) {
        int targetPoint = getTargetPoints(game);

        throw new UnsupportedOperationException("AI equipment generator is not implemented yet");
    }

    private int getTargetPoints(Game game) {
        PointRange pointRange = pointRangeCalculator.calculate(game.getShips());
        return random.randInt(pointRange.getMinPoints(), pointRange.getMaxPoints());
    }
}
