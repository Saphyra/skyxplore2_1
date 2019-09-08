package com.github.saphyra.skyxplore.userdata.character;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.userdata.character.cache.CharacterNameExistsCache;
import com.github.saphyra.skyxplore.userdata.character.domain.RenameCharacterRequest;
import com.github.saphyra.skyxplore.userdata.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.userdata.character.repository.CharacterDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
class CharacterRenameService {
    private final CharacterDao characterDao;
    private final CharacterNameExistsCache characterNameExistsCache;
    private final CharacterQueryService characterQueryService;

    SkyXpCharacter renameCharacter(RenameCharacterRequest request, String userId) {
        if (characterQueryService.isCharNameExists(request.getNewCharacterName())) {
            throw ExceptionFactory.characterNameAlreadyExists(request.getNewCharacterName());
        }
        SkyXpCharacter character = characterQueryService.findCharacterByIdAuthorized(request.getCharacterId(), userId);
        character.setCharacterName(request.getNewCharacterName());
        characterDao.save(character);
        characterNameExistsCache.invalidate(request.getNewCharacterName());
        return character;
    }
}
