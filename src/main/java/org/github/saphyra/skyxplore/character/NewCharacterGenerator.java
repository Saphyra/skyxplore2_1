package org.github.saphyra.skyxplore.character;

import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import org.github.saphyra.skyxplore.character.repository.CharacterDao;
import org.github.saphyra.skyxplore.factory.FactoryFacade;
import org.github.saphyra.skyxplore.ship.EquippedShipFacade;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
//TODO unit test
class NewCharacterGenerator {
    private final CharacterDao characterDao;
    private final CharacterConfig config;
    private final EquippedShipFacade equippedShipFacade;
    private final FactoryFacade factoryFacade;
    private final IdGenerator idGenerator;

    SkyXpCharacter createCharacter(String userId, String characterName) {
        SkyXpCharacter character = SkyXpCharacter.builder()
            .characterId(idGenerator.generateRandomId())
            .userId(userId).characterName(characterName)
            .money(config.getStartMoney())
            .build();

        characterDao.save(character);
        log.info("Character created: {}", character);

        factoryFacade.createFactory(character.getCharacterId());
        equippedShipFacade.createShip(character.getCharacterId());
        return character;
    }
}
