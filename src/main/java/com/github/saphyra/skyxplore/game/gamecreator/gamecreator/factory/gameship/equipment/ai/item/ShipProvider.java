package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.item;

import java.util.List;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.data.entity.Ship;
import com.github.saphyra.skyxplore.data.subservice.ShipService;
import com.github.saphyra.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
//TODO unit test
class ShipProvider {
    private final Random random;
    private final ShipService shipService;

    String getRandomShip() {
        List<Ship> ships = shipService.getShipsByLevel(1);
        String result = ships.get(random.randInt(0, ships.size() - 1)).getId();
        log.debug("Generated random ship: {}", result);
        return result;
    }
}
