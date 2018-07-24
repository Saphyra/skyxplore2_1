package skyxplore.service.ship;

import static skyxplore.service.EquippedShipFacade.BACK_SLOT_NAME;
import static skyxplore.service.EquippedShipFacade.CONNECTOR_SLOT_NAME;
import static skyxplore.service.EquippedShipFacade.FRONT_SLOT_NAME;
import static skyxplore.service.EquippedShipFacade.LEFT_SLOT_NAME;
import static skyxplore.service.EquippedShipFacade.RIGHT_SLOT_NAME;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.controller.request.UnequipRequest;
import skyxplore.dataaccess.db.CharacterDao;
import skyxplore.dataaccess.db.EquippedShipDao;
import skyxplore.dataaccess.db.SlotDao;
import skyxplore.dataaccess.gamedata.entity.Extender;
import skyxplore.dataaccess.gamedata.subservice.ExtenderService;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.domain.ship.EquippedShip;
import skyxplore.domain.slot.EquippedSlot;
import skyxplore.exception.BadSlotNameException;
import skyxplore.exception.ShipNotFoundException;
import skyxplore.service.character.CharacterQueryService;

@Service
@Slf4j
@RequiredArgsConstructor
public class UnequipService {
    private final CharacterDao characterDao;
    private final CharacterQueryService characterQueryService;
    private final EquippedShipDao equippedShipDao;
    private final EquipUtil equipUtil;
    private final ExtenderService extenderService;
    private final SlotDao slotDao;


    @Transactional
    public void unequip(UnequipRequest request, String userId, String characterId) {
        SkyXpCharacter character = characterQueryService.findCharacterByIdAuthorized(userId, characterId);
        EquippedShip ship = equippedShipDao.getShipByCharacterId(characterId);
        if (ship == null) {
            throw new ShipNotFoundException("No ship found with characterId " + characterId);
        }

        if (request.getSlot().contains(CONNECTOR_SLOT_NAME)) {
            ship.removeConnector(request.getItemId());

            if (equipUtil.isExtender(request.getItemId())) {
                log.info("Unequipping extender...");
                Extender extender = extenderService.get(request.getItemId());
                EquippedSlot slot = equipUtil.getSlotByName(ship, extender.getExtendedSlot());
                slot.removeSlot(character, extender.getExtendedNum());
                slotDao.save(slot);
            }

            equippedShipDao.save(ship);
        } else {
            EquippedSlot slot = equipUtil.getSlotByName(ship, request.getSlot());
            removeElementFromSlot(slot, request);
            slotDao.save(slot);
        }
        character.addEquipment(request.getItemId());
        characterDao.save(character);
    }

    private void removeElementFromSlot(EquippedSlot slot, UnequipRequest request) {
        if (request.getSlot().contains(FRONT_SLOT_NAME)) {
            slot.removeFront(request.getItemId());
        } else if (request.getSlot().contains(BACK_SLOT_NAME)) {
            slot.removeBack(request.getItemId());
        } else if (request.getSlot().contains(LEFT_SLOT_NAME)) {
            slot.removeLeft(request.getItemId());
        } else if (request.getSlot().contains(RIGHT_SLOT_NAME)) {
            slot.removeRight(request.getItemId());
        } else {
            throw new BadSlotNameException(request.getSlot());
        }
    }
}
