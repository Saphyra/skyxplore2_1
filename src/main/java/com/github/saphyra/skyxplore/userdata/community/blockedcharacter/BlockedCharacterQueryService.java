package com.github.saphyra.skyxplore.userdata.community.blockedcharacter;

import com.github.saphyra.skyxplore.userdata.character.CharacterQueryService;
import com.github.saphyra.skyxplore.userdata.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.userdata.community.blockedcharacter.domain.BlockedCharacter;
import com.github.saphyra.skyxplore.userdata.community.blockedcharacter.repository.BlockedCharacterDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
        return blockedCharacterDao.getByCharacterIdOrBlockedCharacterId(characterId, blockedCharacterId);
    }

    List<SkyXpCharacter> getBlockedCharacters(String characterId) {
        return blockedCharacterDao.getBlockedCharacters(characterId).stream()
            .map(blockedCharacter -> characterQueryService.findByCharacterId(blockedCharacter.getBlockedCharacterId()))
            .collect(Collectors.toList());
    }
}
