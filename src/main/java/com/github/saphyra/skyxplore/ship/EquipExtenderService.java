package com.github.saphyra.skyxplore.ship;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.github.saphyra.skyxplore.gamedata.entity.Extender;
import com.github.saphyra.skyxplore.gamedata.subservice.ExtenderService;
import com.github.saphyra.skyxplore.ship.domain.EquippedShip;
import com.github.saphyra.skyxplore.slot.domain.EquippedSlot;
import com.github.saphyra.skyxplore.slot.repository.SlotDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
class EquipExtenderService {
    private final EquipUtil equipUtil;
    private final ExtenderService extenderService;
    private final SlotDao slotDao;

    void equipExtender(String itemId, EquippedShip ship) {
        log.info("Equipped item is extender.");
        Extender extender = extenderService.get(itemId);
        checkExtenderEquipable(ship.getConnectorEquipped(), itemId, extender);

        if (extender.getExtendedSlot().contains(EquippedShipConstants.CONNECTOR_SLOT_NAME)) {
            ship.addConnectorSlot(extender.getExtendedNum());
        } else {
            EquippedSlot slot = equipUtil.getSlotByName(ship, extender.getExtendedSlot());
            slot.addSlot(extender.getExtendedNum());
            slotDao.save(slot);
        }
    }

    private void checkExtenderEquipable(List<String> connectors, String itemId, Extender extender) {
        String extendedSlot = extender.getExtendedSlot();
        boolean alreadyEquipped = connectors.stream().anyMatch(i -> {
            Extender equippedConnector = extenderService.get(i);
            if (equippedConnector == null) {
                return false;
            } else {
                return equippedConnector.getExtendedSlot().equals(extendedSlot);
            }
        });

        if (alreadyEquipped) {
            throw new BadRequestException(itemId + " is not equipable. There is already extender equipped for slot " + extendedSlot);
        }
    }
}
