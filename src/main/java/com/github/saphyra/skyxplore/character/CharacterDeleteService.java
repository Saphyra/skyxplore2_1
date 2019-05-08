package com.github.saphyra.skyxplore.character;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.character.repository.CharacterDao;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
class CharacterDeleteService {
    private final CharacterDao characterDao;
    private final CharacterQueryService characterQueryService;

    @Transactional
    void deleteCharacter(String characterId, String userId) {
        SkyXpCharacter character = characterQueryService.findCharacterByIdAuthorized(characterId, userId);
        characterDao.deleteById(character.getCharacterId());
    }
}
