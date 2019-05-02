package skyxplore.service.ship;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.github.saphyra.skyxplore.ship.ShipQueryService;
import org.springframework.stereotype.Service;
import org.github.saphyra.skyxplore.ship.domain.EquipRequest;
import org.github.saphyra.skyxplore.character.repository.CharacterDao;
import org.github.saphyra.skyxplore.ship.repository.EquippedShipDao;
import org.github.saphyra.skyxplore.slot.repository.SlotDao;
import org.github.saphyra.skyxplore.gamedata.entity.Extender;
import org.github.saphyra.skyxplore.gamedata.subservice.ExtenderService;
import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import org.github.saphyra.skyxplore.ship.domain.EquippedShip;
import org.github.saphyra.skyxplore.slot.domain.EquippedSlot;
import org.github.saphyra.skyxplore.common.exception.BadSlotNameException;
import org.github.saphyra.skyxplore.character.CharacterQueryService;

import javax.transaction.Transactional;
import java.util.List;

import static skyxplore.service.EquippedShipFacade.BACK_SLOT_NAME;
import static skyxplore.service.EquippedShipFacade.CONNECTOR_SLOT_NAME;
import static skyxplore.service.EquippedShipFacade.FRONT_SLOT_NAME;
import static skyxplore.service.EquippedShipFacade.LEFT_SLOT_NAME;
import static skyxplore.service.EquippedShipFacade.RIGHT_SLOT_NAME;

@Service
@Slf4j
@RequiredArgsConstructor
public class EquipService {
    private final CharacterDao characterDao;
    private final CharacterQueryService characterQueryService;
    private final EquippedShipDao equippedShipDao;
    private final EquipUtil equipUtil;
    private final ExtenderService extenderService;
    private final ShipQueryService shipQueryService;
    private final SlotDao slotDao;

    @Transactional
    public void equip(EquipRequest request, String characterId) {
        SkyXpCharacter character = characterQueryService.findByCharacterId(characterId);
        EquippedShip ship = shipQueryService.getShipByCharacterId(characterId);

        character.removeEquipment(request.getItemId());

        if (request.getEquipTo().contains(CONNECTOR_SLOT_NAME)) {
            equipConnector(request, ship);
        } else {
            equipToSlot(request, ship);
        }

        characterDao.save(character);
    }

    private void equipConnector(EquipRequest request, EquippedShip ship) {
        if (equipUtil.isExtender(request.getItemId())) {
            equipExtender(request, ship);
        }

        ship.addConnector(request.getItemId());
        equippedShipDao.save(ship);
    }

    private void equipExtender(EquipRequest request, EquippedShip ship) {
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

    private void equipToSlot(EquipRequest request, EquippedShip ship) {
        EquippedSlot slot = equipUtil.getSlotByName(ship, request.getEquipTo());
        addElementToSlot(slot, request);
        slotDao.save(slot);
    }

    private void addElementToSlot(EquippedSlot slot, EquipRequest request) {
        if (request.getEquipTo().contains(FRONT_SLOT_NAME)) {
            slot.addFront(request.getItemId());
        } else if (request.getEquipTo().contains(BACK_SLOT_NAME)) {
            slot.addBack(request.getItemId());
        } else if (request.getEquipTo().contains(LEFT_SLOT_NAME)) {
            slot.addLeft(request.getItemId());
        } else if (request.getEquipTo().contains(RIGHT_SLOT_NAME)) {
            slot.addRight(request.getItemId());
        } else {
            throw new BadSlotNameException(request.getEquipTo());
        }
    }
}
