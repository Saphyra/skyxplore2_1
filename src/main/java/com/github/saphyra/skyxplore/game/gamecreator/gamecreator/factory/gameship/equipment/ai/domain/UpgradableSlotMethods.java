package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.domain;

import java.util.List;

import com.github.saphyra.skyxplore.game.game.GameContext;
import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;

public interface UpgradableSlotMethods {
    boolean hasEmptySlot(ShipEquipments equipments, GameContext gameContext);

    List<String> getEquipmentsOfSlot(ShipEquipments shipEquipments);
}
