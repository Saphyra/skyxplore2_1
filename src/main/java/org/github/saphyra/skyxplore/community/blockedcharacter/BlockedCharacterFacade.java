package org.github.saphyra.skyxplore.community.blockedcharacter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.github.saphyra.skyxplore.character.CharacterQueryService;
import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class BlockedCharacterFacade {
    private final BlockCharacterService blockCharacterService;
    private final CharacterQueryService characterQueryService;

    public void allowBlockedCharacter(String blockedCharacterId, String characterId) {
        blockCharacterService.allowBlockedCharacter(blockedCharacterId, characterId);
    }

    public void blockCharacter(String blockedCharacterId, String characterId) {
        blockCharacterService.blockCharacter(blockedCharacterId, characterId);
    }

    public List<SkyXpCharacter> getBlockedCharacters(String characterId) {
        return characterQueryService.getBlockedCharacters(characterId);
    }

    public List<SkyXpCharacter> getCharactersCanBeBlocked(String name, String characterId) {
        return characterQueryService.getCharactersCanBeBlocked(name, characterId);
    }
}
