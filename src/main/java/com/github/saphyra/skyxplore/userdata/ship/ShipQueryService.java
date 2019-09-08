package com.github.saphyra.skyxplore.userdata.ship;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.userdata.ship.domain.EquippedShip;
import com.github.saphyra.skyxplore.userdata.ship.domain.ShipView;
import com.github.saphyra.skyxplore.userdata.ship.repository.EquippedShipDao;
import com.github.saphyra.skyxplore.userdata.slot.SlotQueryService;
import com.github.saphyra.skyxplore.userdata.slot.domain.EquippedSlot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShipQueryService {
    private final EquippedShipDao equippedShipDao;
    private final ShipViewConverter shipViewConverter;
    private final SlotQueryService slotQueryService;

    public EquippedShip findShipByCharacterIdValidated(String characterId) {
        return equippedShipDao.findShipByCharacterId(characterId)
            .orElseThrow(() -> ExceptionFactory.shipNotFound(characterId));
    }

    ShipView getShipData(String characterId) {
        EquippedShip ship = findShipByCharacterIdValidated(characterId);

        EquippedSlot defenseSlot = slotQueryService.findSlotByIdValidated(ship.getDefenseSlotId());
        EquippedSlot weaponSlot = slotQueryService.findSlotByIdValidated(ship.getWeaponSlotId());

        return shipViewConverter.convertDomain(ship, defenseSlot, weaponSlot);
    }
}
