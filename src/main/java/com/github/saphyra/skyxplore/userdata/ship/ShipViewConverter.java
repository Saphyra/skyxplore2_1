package com.github.saphyra.skyxplore.userdata.ship;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.data.gamedata.subservice.ShipService;
import com.github.saphyra.skyxplore.userdata.ship.domain.EquippedShip;
import com.github.saphyra.skyxplore.userdata.ship.domain.ShipView;
import com.github.saphyra.skyxplore.userdata.slot.domain.EquippedSlot;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class ShipViewConverter {
    private final SlotViewConverter slotViewConverter;
    private final ShipService shipService;

    ShipView convertDomain(EquippedShip domain, EquippedSlot defenseSlot, EquippedSlot weaponSlot) {
        return ShipView.builder()
            .shipType(domain.getShipType())
            .coreHull(domain.getCoreHull())
            .connectorSlot(domain.getConnectorSlot())
            .connectorEquipped(domain.getConnectorEquipped())
            .defenseSlot(slotViewConverter.convertDomain(defenseSlot))
            .weaponSlot(slotViewConverter.convertDomain(weaponSlot))
            .ability(shipService.get(domain.getShipType()).getAbility())
            .build();
    }
}
