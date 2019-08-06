package com.github.saphyra.skyxplore.userdata.ship;

import static com.github.saphyra.skyxplore.data.DataConstants.CONNECTOR_SLOT_NAME;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.data.entity.Extender;
import com.github.saphyra.skyxplore.data.subservice.ExtenderService;
import com.github.saphyra.skyxplore.userdata.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.userdata.ship.domain.EquippedShip;
import com.github.saphyra.skyxplore.userdata.slot.domain.EquippedSlot;
import com.github.saphyra.skyxplore.userdata.slot.repository.SlotDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
