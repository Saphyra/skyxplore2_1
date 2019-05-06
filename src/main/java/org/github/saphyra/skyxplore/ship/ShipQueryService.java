package org.github.saphyra.skyxplore.ship;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.github.saphyra.skyxplore.common.exception.ShipNotFoundException;
import org.github.saphyra.skyxplore.ship.domain.EquippedShip;
import org.github.saphyra.skyxplore.ship.domain.ShipView;
import org.github.saphyra.skyxplore.ship.repository.EquippedShipDao;
import org.github.saphyra.skyxplore.slot.SlotQueryService;
import org.github.saphyra.skyxplore.slot.domain.EquippedSlot;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShipQueryService {
    private final EquippedShipDao equippedShipDao;
    private final ShipViewConverter shipViewConverter;
    private final SlotQueryService slotQueryService;

    EquippedShip getShipByCharacterId(String characterId) {
        return equippedShipDao.findShipByCharacterId(characterId)
            .orElseThrow(() -> new ShipNotFoundException("No ship found with characterId " + characterId));
    }

    ShipView getShipData(String characterId) {
        EquippedShip ship = getShipByCharacterId(characterId);

        EquippedSlot defenseSlot = slotQueryService.findSlotById(ship.getDefenseSlotId());
        EquippedSlot weaponSlot = slotQueryService.findSlotById(ship.getWeaponSlotId());

        return shipViewConverter.convertDomain(ship, defenseSlot, weaponSlot);
    }
}
