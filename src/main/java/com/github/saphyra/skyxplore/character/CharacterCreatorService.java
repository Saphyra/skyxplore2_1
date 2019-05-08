package com.github.saphyra.skyxplore.character;

import com.github.saphyra.skyxplore.common.exception.CharacterNameAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.github.saphyra.skyxplore.character.cache.CharacterNameExistsCache;
import com.github.saphyra.skyxplore.character.domain.CreateCharacterRequest;
import com.github.saphyra.skyxplore.character.domain.SkyXpCharacter;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
class CharacterCreatorService {
    private final CharacterNameExistsCache characterNameExistsCache;
    private final CharacterQueryService characterQueryService;
    private final NewCharacterGenerator newCharacterGenerator;

    @Transactional
    SkyXpCharacter createCharacter(CreateCharacterRequest request, String userId) {
        if (characterQueryService.isCharNameExists(request.getCharacterName())) {
            throw new CharacterNameAlreadyExistsException("Character already exists with name " + request.getCharacterName());
        }

        SkyXpCharacter character = newCharacterGenerator.createCharacter(userId, request.getCharacterName());
        characterNameExistsCache.invalidate(request.getCharacterName());
        return character;
    }
}
