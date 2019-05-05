package org.github.saphyra.skyxplore.ship;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.github.saphyra.skyxplore.gamedata.entity.Extender;
import org.github.saphyra.skyxplore.gamedata.subservice.ExtenderService;
import org.github.saphyra.skyxplore.ship.domain.EquipRequest;
import org.github.saphyra.skyxplore.ship.domain.EquippedShip;
import org.github.saphyra.skyxplore.slot.domain.EquippedSlot;
import org.github.saphyra.skyxplore.slot.repository.SlotDao;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.github.saphyra.skyxplore.ship.EquippedShipConstants.CONNECTOR_SLOT_NAME;

@Service
@Slf4j
@RequiredArgsConstructor
//TODO unit test
class EquipExtenderService {
    private final EquipUtil equipUtil;
    private final ExtenderService extenderService;
    private final SlotDao slotDao;

    void equipExtender(EquipRequest request, EquippedShip ship) {
        log.info("Equipped item is extender.");
        Extender extender = extenderService.get(request.getItemId());
        checkExtenderEquipable(ship.getConnectorEquipped(), request.getItemId(), extender);

        if (extender.getExtendedSlot().contains(CONNECTOR_SLOT_NAME)) {
            ship.addConnectorSlot(extender.getExtendedNum());
        } else {
            EquippedSlot slot = equipUtil.getSlotByName(ship, extender.getExtendedSlot());
            slot.addSlot(extender.getExtendedNum());
            slotDao.save(slot);
        }
    }

    private void checkExtenderEquipable(List<String> connectors, String itemId, Extender extender) {
        boolean notEquipable = connectors.stream().anyMatch(i -> {
            Extender equippedConnector = extenderService.get(i);
            if (equippedConnector == null) {
                return false;
            } else {
                return equippedConnector.getExtendedSlot().equals(extender.getExtendedSlot());
            }
        });

        if (notEquipable) {
            throw new BadRequestException(itemId + " is not equipable. There is already extender equipped for slot " + extender.getExtendedSlot());
        }
    }
}