package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.domain;

import java.util.List;

import com.github.saphyra.skyxplore.data.domain.SlotType;
import com.github.saphyra.skyxplore.game.gamecreator.GameCreatorContext;
import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;

public interface UpgradableSlotMethods {
    boolean hasEmptySlot(ShipEquipments equipments, GameCreatorContext gameCreatorContext);

    List<String> getEquipmentsOfSlot(ShipEquipments shipEquipments);

    SlotType getSlotType();
}
