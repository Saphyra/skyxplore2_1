package org.github.saphyra.skyxplore.ship;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.github.saphyra.skyxplore.common.exception.ShipNotFoundException;
import org.github.saphyra.skyxplore.ship.domain.EquippedShip;
import org.github.saphyra.skyxplore.ship.domain.ShipView;
import org.github.saphyra.skyxplore.ship.repository.EquippedShipDao;
import org.github.saphyra.skyxplore.slot.domain.EquippedSlot;
import org.github.saphyra.skyxplore.slot.repository.SlotDao;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShipQueryService {
    private final EquippedShipDao equippedShipDao;
    private final ShipViewConverter shipViewConverter;
    private final SlotDao slotDao;

    public EquippedShip getShipByCharacterId(String characterId) {
        return equippedShipDao.getShipByCharacterId(characterId)
            .orElseThrow(() -> new ShipNotFoundException("No ship found with characterId " + characterId));
    }

    ShipView getShipData(String characterId) {
        EquippedShip ship = getShipByCharacterId(characterId);

        EquippedSlot defenseSlot = slotDao.getById(ship.getDefenseSlotId());
        EquippedSlot weaponSlot = slotDao.getById(ship.getWeaponSlotId());

        return shipViewConverter.convertDomain(ship, defenseSlot, weaponSlot);
    }
}
