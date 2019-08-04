package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment;

import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;
import com.github.saphyra.skyxplore.userdata.ship.ShipQueryService;
import com.github.saphyra.skyxplore.userdata.ship.domain.EquippedShip;
import com.github.saphyra.skyxplore.userdata.slot.SlotQueryService;
import com.github.saphyra.skyxplore.userdata.slot.domain.EquippedSlot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
//TODO unit test
class ShipEquipmentAggregator {
    private final ShipQueryService shipQueryService;
    private final SlotQueryService slotQueryService;

    ShipEquipments aggregateEquipments(String characterId) {
        EquippedShip equippedShip = shipQueryService.findShipbyCharacterIdValidated(characterId);

        EquippedSlot defenseSlot = slotQueryService.findSlotByIdValidated(equippedShip.getDefenseSlotId());
        EquippedSlot weaponSlot = slotQueryService.findSlotByIdValidated(equippedShip.getWeaponSlotId());

        ShipEquipments shipEquipments = ShipEquipments.builder()
            .shipId(equippedShip.getShipType())
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
        log.debug("Aggregated ShipEquipments: {}", shipEquipments);
        return shipEquipments;
    }
}
