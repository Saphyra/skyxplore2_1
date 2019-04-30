package skyxplore.service.ship;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.controller.request.character.UnequipRequest;
import org.github.saphyra.skyxplore.character.repository.CharacterDao;
import org.github.saphyra.skyxplore.ship.repository.EquippedShipDao;
import org.github.saphyra.skyxplore.slot.repository.SlotDao;
import org.github.saphyra.skyxplore.gamedata.entity.Extender;
import org.github.saphyra.skyxplore.gamedata.subservice.ExtenderService;
import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import org.github.saphyra.skyxplore.ship.domain.EquippedShip;
import org.github.saphyra.skyxplore.slot.domain.EquippedSlot;
import skyxplore.exception.BadSlotNameException;
import org.github.saphyra.skyxplore.character.CharacterQueryService;

import javax.transaction.Transactional;

import static skyxplore.service.EquippedShipFacade.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class UnequipService {
    private final CharacterDao characterDao;
    private final CharacterQueryService characterQueryService;
    private final EquippedShipDao equippedShipDao;
    private final EquipUtil equipUtil;
    private final ExtenderService extenderService;
    private final ShipQueryService shipQueryService;
    private final SlotDao slotDao;


    @Transactional
    public void unequip(UnequipRequest request, String characterId) {
        SkyXpCharacter character = characterQueryService.findByCharacterId(characterId);
        EquippedShip ship = shipQueryService.getShipByCharacterId(characterId);

        if (request.getSlot().contains(CONNECTOR_SLOT_NAME)) {
            unequipConnector(request, character, ship);
        } else {
            unequipFromSlot(request, ship);
        }

        character.addEquipment(request.getItemId());
        characterDao.save(character);
    }

    private void unequipConnector(UnequipRequest request, SkyXpCharacter character, EquippedShip ship) {
        ship.removeConnector(request.getItemId());

        if (equipUtil.isExtender(request.getItemId())) {
            log.info("Unequipping extender...");
            unequipExtender(request, character, ship);
        }

        equippedShipDao.save(ship);
    }

    private void unequipExtender(UnequipRequest request, SkyXpCharacter character, EquippedShip ship) {
        Extender extender = extenderService.get(request.getItemId());
        if (extender.getExtendedSlot().contains(CONNECTOR_SLOT_NAME)) {
            ship.removeConnectorSlot(extender.getExtendedNum(), character, extenderService);
        } else {
            EquippedSlot slot = equipUtil.getSlotByName(ship, extender.getExtendedSlot());
            slot.removeSlot(character, extender.getExtendedNum());
            slotDao.save(slot);
        }
    }

    private void unequipFromSlot(UnequipRequest request, EquippedShip ship) {
        EquippedSlot slot = equipUtil.getSlotByName(ship, request.getSlot());
        removeElementFromSlot(slot, request);
        slotDao.save(slot);
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
