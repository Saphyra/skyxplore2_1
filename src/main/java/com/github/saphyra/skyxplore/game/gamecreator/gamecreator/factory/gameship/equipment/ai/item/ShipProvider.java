package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.item;

import com.github.saphyra.skyxplore.data.gamedata.entity.Ship;
import com.github.saphyra.skyxplore.data.gamedata.subservice.ShipService;
import com.github.saphyra.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
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
