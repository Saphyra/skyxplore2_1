package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;
import com.github.saphyra.skyxplore.userdata.ship.ShipQueryService;
import com.github.saphyra.skyxplore.userdata.ship.domain.EquippedShip;
import com.github.saphyra.skyxplore.userdata.slot.SlotQueryService;
import com.github.saphyra.skyxplore.userdata.slot.domain.EquippedSlot;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
//TODO unit test
class ShipEquipmentAggregator {
    private final ShipQueryService shipQueryService;
    private final SlotQueryService slotQueryService;

    ShipEquipments aggregateEquipments(String characterId) {
        EquippedShip equippedShip = shipQueryService.findShipbyCharacterIdValidated(characterId);

        EquippedSlot defenseSlot = slotQueryService.findSlotByIdValidated(equippedShip.getDefenseSlotId());
        EquippedSlot weaponSlot = slotQueryService.findSlotByIdValidated(equippedShip.getWeaponSlotId());

        return ShipEquipments.builder()
            .shipId(equippedShip.getShipId())
            .connectorEquipped(equippedShip.getConnectorEquipped())
            .frontDefense(defenseSlot.getFrontEquipped())
            .leftDefense(defenseSlot.getLeftEquipped())
            .rightDefense(defenseSlot.getRightEquipped())
            .backDefense(defenseSlot.getBackEquipped())
            .frontWeapon(weaponSlot.getFrontEquipped())
            .leftWeapon(weaponSlot.getLeftEquipped())
            .rightWeapon(weaponSlot.getRightEquipped())
            .backWeapon(weaponSlot.getBackEquipped())
            .build();
    }
}
