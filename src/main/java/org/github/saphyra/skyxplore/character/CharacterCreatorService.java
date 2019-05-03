package org.github.saphyra.skyxplore.character;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.github.saphyra.skyxplore.character.cache.CharacterNameCache;
import org.github.saphyra.skyxplore.character.domain.CreateCharacterRequest;
import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import org.github.saphyra.skyxplore.common.exception.CharacterNameAlreadyExistsException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
class CharacterCreatorService {
    private final CharacterNameCache characterNameCache;
    private final CharacterQueryService characterQueryService;
    private final NewCharacterGenerator newCharacterGenerator;

    @Transactional
    SkyXpCharacter createCharacter(CreateCharacterRequest request, String userId) {
        if (characterQueryService.isCharNameExists(request.getCharacterName())) {
            throw new CharacterNameAlreadyExistsException("Character already exists with name " + request.getCharacterName());
        }

        SkyXpCharacter character = newCharacterGenerator.createCharacter(userId, request.getCharacterName());
        characterNameCache.invalidate(request.getCharacterName());
        return character;
    }
}
