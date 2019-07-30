package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.game.game.domain.Game;
import com.github.saphyra.skyxplore.game.game.domain.ship.GameShip;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroupCharacter;
import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ShipEquipmentsProvider;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
//TODO unit test
public class GameShipFactory {
    private final ShipEquipmentsProvider shipEquipmentsProvider;

    public GameShip create(GameGroupCharacter gameGroupCharacter, Game game) {
        ShipEquipments equipments = shipEquipmentsProvider.getEquipments(gameGroupCharacter, game);
        //TODO implement
        throw new UnsupportedOperationException("Creating GameShip is not implemented yet.");
    }
}
