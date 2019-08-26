package com.github.saphyra.skyxplore.userdata.ship;


import com.github.saphyra.skyxplore.userdata.character.CharacterQueryService;
import com.github.saphyra.skyxplore.userdata.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.userdata.character.repository.CharacterDao;
import com.github.saphyra.skyxplore.userdata.ship.domain.EquipRequest;
import com.github.saphyra.skyxplore.userdata.ship.domain.EquippedShip;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.github.saphyra.skyxplore.data.DataConstants.CONNECTOR_SLOT_NAME;

@Service
@Slf4j
@RequiredArgsConstructor
class EquipService {
    private final CharacterDao characterDao;
    private final CharacterQueryService characterQueryService;
    private final EquipConnectorService equipConnectorService;
    private final EquipToSlotService equipToSlotService;
    private final ShipQueryService shipQueryService;

    @SuppressWarnings("WeakerAccess")
    @Transactional
    public void equip(EquipRequest request, String characterId) {
        SkyXpCharacter character = characterQueryService.findByCharacterIdValidated(characterId);
        EquippedShip ship = shipQueryService.findShipByCharacterIdValidated(characterId);

        character.removeEquipment(request.getItemId());

        if (request.getEquipTo().contains(CONNECTOR_SLOT_NAME)) {
            equipConnectorService.equipConnector(request, ship);
        } else {
            equipToSlotService.equipToSlot(request, ship);
        }

        characterDao.save(character);
    }
}
