package com.github.saphyra.skyxplore.userdata.ship;

import static com.github.saphyra.skyxplore.data.DataConstants.CONNECTOR_SLOT_NAME;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.userdata.character.CharacterQueryService;
import com.github.saphyra.skyxplore.userdata.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.userdata.character.repository.CharacterDao;
import com.github.saphyra.skyxplore.userdata.ship.domain.EquippedShip;
import com.github.saphyra.skyxplore.userdata.ship.domain.UnequipRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


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
        SkyXpCharacter character = characterQueryService.findByCharacterIdValidated(characterId);
        EquippedShip ship = shipQueryService.findShipByCharacterIdValidated(characterId);

        if (request.getSlot().contains(CONNECTOR_SLOT_NAME)) {
            unequipConnectorService.unequipConnector(request.getItemId(), character, ship);
        } else {
            unequipFromSlotService.unequipFromSlot(request, ship);
        }

        character.addEquipment(request.getItemId());
        characterDao.save(character);
    }
}
