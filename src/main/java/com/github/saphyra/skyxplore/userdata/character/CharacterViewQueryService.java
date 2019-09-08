package com.github.saphyra.skyxplore.userdata.character;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.common.domain.character.CharacterView;
import com.github.saphyra.skyxplore.common.domain.character.CharacterViewConverter;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CharacterViewQueryService {
    private final CharacterQueryService characterQueryService;
    private final CharacterViewConverter characterViewConverter;

    public CharacterView findByCharacterId(String characterId) {
        return characterViewConverter.convertDomain(characterQueryService.findByCharacterIdValidated(characterId));
    }

    List<CharacterView> getActiveCharactersByName(String characterId, String name) {
        return characterViewConverter.convertDomain(characterQueryService.getActiveCharactersByName(characterId, name));
    }

    List<CharacterView> getCharactersByUserId(String userId) {
        return characterViewConverter.convertDomain(characterQueryService.getCharactersByUserId(userId));
    }
}
