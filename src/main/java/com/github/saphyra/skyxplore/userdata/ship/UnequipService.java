package com.github.saphyra.skyxplore.userdata.ship;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.github.saphyra.skyxplore.userdata.character.CharacterQueryService;
import com.github.saphyra.skyxplore.userdata.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.userdata.character.repository.CharacterDao;
import com.github.saphyra.skyxplore.userdata.ship.domain.EquippedShip;
import com.github.saphyra.skyxplore.userdata.ship.domain.UnequipRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


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
        EquippedShip ship = shipQueryService.findShipbyCharacterIdValidated(characterId);

        if (request.getSlot().contains(EquippedShipConstants.CONNECTOR_SLOT_NAME)) {
            unequipConnectorService.unequipConnector(request.getItemId(), character, ship);
        } else {
            unequipFromSlotService.unequipFromSlot(request, ship);
        }

        character.addEquipment(request.getItemId());
        characterDao.save(character);
    }
}
