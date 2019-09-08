package com.github.saphyra.skyxplore.userdata.character;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.userdata.character.cache.CharacterNameExistsCache;
import com.github.saphyra.skyxplore.userdata.character.domain.CreateCharacterRequest;
import com.github.saphyra.skyxplore.userdata.character.domain.SkyXpCharacter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
            throw ExceptionFactory.characterNameAlreadyExists(request.getCharacterName());
        }

        SkyXpCharacter character = newCharacterGenerator.createCharacter(userId, request.getCharacterName());
        characterNameExistsCache.invalidate(request.getCharacterName());
        return character;
    }
}
