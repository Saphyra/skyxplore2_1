package org.github.saphyra.skyxplore.community.blockedcharacter;

import java.util.List;
import java.util.Optional;

import org.github.saphyra.skyxplore.community.blockedcharacter.domain.BlockedCharacter;
import org.github.saphyra.skyxplore.community.blockedcharacter.repository.BlockedCharacterDao;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class BlockedCharacterQueryService {
    private final BlockedCharacterDao blockedCharacterDao;

    Optional<BlockedCharacter> findByCharacterIdAndBlockedCharacterId(String characterId, String blockedCharacterId) {
        return blockedCharacterDao.findByCharacterIdAndBlockedCharacterId(characterId, blockedCharacterId);
    }

    public List<BlockedCharacter> findByCharacterIdOrBlockedCharacterId(String characterId, String blockedCharacterId) {
        return blockedCharacterDao.findByCharacterIdOrBlockedCharacterId(characterId, blockedCharacterId);
    }

    public List<BlockedCharacter> getBlockedCharactersOf(String characterId) {
        return blockedCharacterDao.getBlockedCharactersOf(characterId);
    }
}
