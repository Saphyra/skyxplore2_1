package org.github.saphyra.skyxplore.ship;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.github.saphyra.skyxplore.character.CharacterQueryService;
import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import org.github.saphyra.skyxplore.character.repository.CharacterDao;
import org.github.saphyra.skyxplore.ship.domain.EquippedShip;
import org.github.saphyra.skyxplore.ship.domain.UnequipRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static org.github.saphyra.skyxplore.ship.EquippedShipConstants.CONNECTOR_SLOT_NAME;


@Service
@Slf4j
@RequiredArgsConstructor
class UnequipService {
    private final CharacterDao characterDao;
    private final CharacterQueryService characterQueryService;
    private final ShipQueryService shipQueryService;
    private final UnequipConnectorService unequipConnectorService;
    private final UnequipFromSlotService unequipFromSlotService;

    @Transactional
    void unequip(UnequipRequest request, String characterId) {
        SkyXpCharacter character = characterQueryService.findByCharacterId(characterId);
        EquippedShip ship = shipQueryService.getShipByCharacterId(characterId);

        if (request.getSlot().contains(CONNECTOR_SLOT_NAME)) {
            unequipConnectorService.unequipConnector(request.getItemId(), character, ship);
        } else {
            unequipFromSlotService.unequipFromSlot(request, ship);
        }

        character.addEquipment(request.getItemId());
        characterDao.save(character);
    }
}
