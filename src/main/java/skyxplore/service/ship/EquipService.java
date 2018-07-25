package skyxplore.service.ship;

import static skyxplore.service.EquippedShipFacade.BACK_SLOT_NAME;
import static skyxplore.service.EquippedShipFacade.CONNECTOR_SLOT_NAME;
import static skyxplore.service.EquippedShipFacade.FRONT_SLOT_NAME;
import static skyxplore.service.EquippedShipFacade.LEFT_SLOT_NAME;
import static skyxplore.service.EquippedShipFacade.RIGHT_SLOT_NAME;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.controller.request.EquipRequest;
import skyxplore.dataaccess.db.CharacterDao;
import skyxplore.dataaccess.db.EquippedShipDao;
import skyxplore.dataaccess.db.SlotDao;
import skyxplore.dataaccess.gamedata.entity.Extender;
import skyxplore.dataaccess.gamedata.subservice.ExtenderService;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.domain.ship.EquippedShip;
import skyxplore.domain.slot.EquippedSlot;
import skyxplore.exception.BadSlotNameException;
import skyxplore.exception.base.BadRequestException;
import skyxplore.service.character.CharacterQueryService;

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
    public void equip(EquipRequest request, String userId, String characterId) {
        SkyXpCharacter character = characterQueryService.findCharacterByIdAuthorized(characterId, userId);
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
        checkExtenderEquipable(ship.getConnectorEquipped(), request.getItemId());
        Extender extender = extenderService.get(request.getItemId());

        if (extender.getExtendedSlot().contains(CONNECTOR_SLOT_NAME)) {
            ship.addConnectorSlot(extender.getExtendedNum());
        } else {
            EquippedSlot slot = equipUtil.getSlotByName(ship, extender.getExtendedSlot());
            slot.addSlot(extender.getExtendedNum());
            slotDao.save(slot);
        }
    }

    private void checkExtenderEquipable(List<String> connectors, String itemId) {
        Extender extender = extenderService.get(itemId);
        boolean notEquipable = connectors.stream().anyMatch(i -> {
            Extender e = extenderService.get(i);
            if (e == null) {
                return true;
            } else {
                return e.getExtendedSlot().equals(extender.getExtendedSlot());
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
