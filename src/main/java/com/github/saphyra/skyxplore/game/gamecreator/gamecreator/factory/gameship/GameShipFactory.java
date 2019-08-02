package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.game.game.domain.Game;
import com.github.saphyra.skyxplore.game.game.domain.ship.Coordinates;
import com.github.saphyra.skyxplore.game.game.domain.ship.GameShip;
import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroupCharacter;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ShipEquipmentsProvider;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
//TODO unit test
public class GameShipFactory {
    private final GameShipDetailsFactory gameShipDetailsFactory;
    private final IdGenerator idGenerator;
    private final ShipEquipmentsProvider shipEquipmentsProvider;

    public GameShip create(GameGroupCharacter gameGroupCharacter, Game game) {
        ShipEquipments equipments = shipEquipmentsProvider.getEquipments(gameGroupCharacter, game);

        GameShip gameShip = GameShip.builder()
            .gameShipId(idGenerator.randomUUID())
            .position(Coordinates.createDefault())
            .shipEquipments(equipments)
            .gameShipDetails(gameShipDetailsFactory.create(equipments))
            .build();
        log.debug("Created GameShip: {}", gameShip);
        return gameShip;
    }
}
