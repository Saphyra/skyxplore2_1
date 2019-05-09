package com.github.saphyra.skyxplore.ship;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.gamedata.entity.Extender;
import com.github.saphyra.skyxplore.gamedata.subservice.ExtenderService;
import com.github.saphyra.skyxplore.ship.domain.EquippedShip;
import com.github.saphyra.skyxplore.slot.domain.EquippedSlot;
import com.github.saphyra.skyxplore.slot.repository.SlotDao;
import org.springframework.stereotype.Service;

import static com.github.saphyra.skyxplore.ship.EquippedShipConstants.CONNECTOR_SLOT_NAME;

@Service
@Slf4j
@RequiredArgsConstructor
class UnequipExtenderService {
    private final EquipUtil equipUtil;
    private final ExtenderService extenderService;
    private final SlotDao slotDao;

    void unequipExtender(String itemId, SkyXpCharacter character, EquippedShip ship) {
        Extender extender = extenderService.get(itemId);
        if (extender.getExtendedSlot().contains(CONNECTOR_SLOT_NAME)) {
            ship.removeConnectorSlot(extender.getExtendedNum(), character, extenderService);
        } else {
            EquippedSlot slot = equipUtil.getSlotByName(ship, extender.getExtendedSlot());
            slot.removeSlot(character, extender.getExtendedNum());
            slotDao.save(slot);
        }
    }
}