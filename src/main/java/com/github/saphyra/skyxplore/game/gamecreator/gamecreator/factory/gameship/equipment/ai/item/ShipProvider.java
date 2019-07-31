package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.item;

import java.util.List;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.data.entity.Ship;
import com.github.saphyra.skyxplore.data.subservice.ShipService;
import com.github.saphyra.util.Random;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
//TODO unit test
class ShipProvider {
    private final Random random;
    private final ShipService shipService;

    String getRandomShip() {
        List<Ship> ships = shipService.getShips(1);
        return ships.get(random.randInt(0, ships.size() - 1)).getId();
    }
}
