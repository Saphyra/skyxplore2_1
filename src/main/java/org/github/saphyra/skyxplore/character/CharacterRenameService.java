package org.github.saphyra.skyxplore.character;

import org.github.saphyra.skyxplore.character.cache.CharacterNameCache;
import org.github.saphyra.skyxplore.character.domain.RenameCharacterRequest;
import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import org.github.saphyra.skyxplore.character.repository.CharacterDao;
import org.github.saphyra.skyxplore.common.exception.CharacterNameAlreadyExistsException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
class CharacterRenameService {
    private final CharacterDao characterDao;
    private final CharacterNameCache characterNameCache;
    private final CharacterQueryService characterQueryService;

    SkyXpCharacter renameCharacter(RenameCharacterRequest request, String userId) {
        if (characterQueryService.isCharNameExists(request.getNewCharacterName())) {
            throw new CharacterNameAlreadyExistsException("Character name already exists: " + request.getNewCharacterName());
        }
        SkyXpCharacter character = characterQueryService.findCharacterByIdAuthorized(request.getCharacterId(), userId);
        character.setCharacterName(request.getNewCharacterName());
        characterDao.save(character);
        characterNameCache.invalidate(character.getCharacterName());
        return character;
    }
}
