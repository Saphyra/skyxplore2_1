package org.github.saphyra.skyxplore.community.blockedcharacter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.github.saphyra.skyxplore.character.CharacterQueryService;
import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import org.github.saphyra.skyxplore.community.blockedcharacter.domain.BlockedCharacter;
import org.github.saphyra.skyxplore.community.blockedcharacter.repository.BlockedCharacterDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class BlockedCharacterQueryService {
    private final BlockedCharacterDao blockedCharacterDao;
    private final CharacterQueryService characterQueryService;

    Optional<BlockedCharacter> findByCharacterIdAndBlockedCharacterId(String characterId, String blockedCharacterId) {
        return blockedCharacterDao.findByCharacterIdAndBlockedCharacterId(characterId, blockedCharacterId);
    }

    public List<BlockedCharacter> findByCharacterIdOrBlockedCharacterId(String characterId, String blockedCharacterId) {
        return blockedCharacterDao.findByCharacterIdOrBlockedCharacterId(characterId, blockedCharacterId);
    }

    public List<BlockedCharacter> getBlockedCharactersOf(String characterId) {
        return blockedCharacterDao.getBlockedCharacters(characterId);
    }

    List<SkyXpCharacter> getBlockedCharacters(String characterId) {
        return getBlockedCharactersOf(characterId).stream()
            .map(blockedCharacter -> characterQueryService.findByCharacterId(blockedCharacter.getBlockedCharacterId()))
            .collect(Collectors.toList());
    }
}
