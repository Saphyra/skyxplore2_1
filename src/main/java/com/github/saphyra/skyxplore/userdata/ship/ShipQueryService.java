package com.github.saphyra.skyxplore.userdata.ship;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.userdata.ship.domain.EquippedShip;
import com.github.saphyra.skyxplore.userdata.ship.domain.ShipView;
import com.github.saphyra.skyxplore.userdata.ship.repository.EquippedShipDao;
import com.github.saphyra.skyxplore.userdata.slot.SlotQueryService;
import com.github.saphyra.skyxplore.userdata.slot.domain.EquippedSlot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShipQueryService {
    private final EquippedShipDao equippedShipDao;
    private final ShipViewConverter shipViewConverter;
    private final SlotQueryService slotQueryService;

    public EquippedShip findShipbyCharacterIdValidated(String characterId) {
        return equippedShipDao.findShipByCharacterId(characterId)
            .orElseThrow(() -> new ShipNotFoundException("No ship found with characterId " + characterId));
    }

    ShipView getShipData(String characterId) {
        EquippedShip ship = findShipbyCharacterIdValidated(characterId);

        EquippedSlot defenseSlot = slotQueryService.findSlotByIdValidated(ship.getDefenseSlotId());
        EquippedSlot weaponSlot = slotQueryService.findSlotByIdValidated(ship.getWeaponSlotId());

        return shipViewConverter.convertDomain(ship, defenseSlot, weaponSlot);
    }
}
