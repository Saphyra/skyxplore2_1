package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.game.game.domain.Game;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroupCharacter;
import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.AiShipEquipmentGenerator;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ShipEquipmentsProvider {
    private final AiShipEquipmentGenerator aiShipEquipmentGenerator;
    private final ShipEquipmentAggregator shipEquipmentAggregator;

    public ShipEquipments getEquipments(GameGroupCharacter gameGroupCharacter, Game game) {
        return gameGroupCharacter.isAi()
            ? aiShipEquipmentGenerator.generateEquipments(game.getShips())
            : shipEquipmentAggregator.aggregateEquipments(gameGroupCharacter.getCharacterId());
    }
}
