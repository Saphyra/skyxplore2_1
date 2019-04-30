package org.github.saphyra.skyxplore.character;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.github.saphyra.skyxplore.character.cache.CharacterNameCache;
import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import org.github.saphyra.skyxplore.character.domain.request.CreateCharacterRequest;
import org.github.saphyra.skyxplore.character.repository.CharacterDao;
import org.github.saphyra.skyxplore.factory.domain.Factory;
import org.github.saphyra.skyxplore.factory.repository.FactoryDao;
import org.github.saphyra.skyxplore.ship.domain.EquippedShip;
import org.github.saphyra.skyxplore.ship.repository.EquippedShipDao;
import org.github.saphyra.skyxplore.slot.domain.EquippedSlot;
import org.github.saphyra.skyxplore.slot.repository.SlotDao;
import org.springframework.stereotype.Service;
import org.github.saphyra.skyxplore.common.exception.CharacterNameAlreadyExistsException;

import javax.transaction.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
class CharacterCreatorService {
    private final CharacterDao characterDao;
    private final CharacterQueryService characterQueryService;
    private final EquippedShipDao equippedShipDao;
    private final FactoryDao factoryDao;
    private final NewCharacterGenerator newCharacterGenerator;
    private final SlotDao slotDao;
    private final CharacterNameCache characterNameCache;

    @Transactional
    //TODO event driven character generation
    SkyXpCharacter createCharacter(CreateCharacterRequest request, String userId) {
        if (characterQueryService.isCharNameExists(request.getCharacterName())) {
            throw new CharacterNameAlreadyExistsException("Character already exists with name " + request.getCharacterName());
        }
        SkyXpCharacter character = newCharacterGenerator.createCharacter(userId, request.getCharacterName());

        EquippedShip ship = newCharacterGenerator.createShip(character.getCharacterId());

        EquippedSlot defenseSlot = newCharacterGenerator.createDefenseSlot(ship.getShipId());
        ship.setDefenseSlotId(defenseSlot.getSlotId());

        EquippedSlot weaponSlot = newCharacterGenerator.createWeaponSlot(ship.getShipId());
        ship.setWeaponSlotId(weaponSlot.getSlotId());

        Factory factory = newCharacterGenerator.createFactory(character.getCharacterId());

        characterDao.save(character);
        equippedShipDao.save(ship);
        slotDao.save(defenseSlot);
        slotDao.save(weaponSlot);
        factoryDao.save(factory);

        characterNameCache.invalidate(request.getCharacterName());

        return character;
    }
}
