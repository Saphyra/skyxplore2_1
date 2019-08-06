package com.github.saphyra.skyxplore.userdata.community.blockedcharacter;

import com.github.saphyra.skyxplore.common.exception.BlockedCharacterNotFoundException;
import com.github.saphyra.skyxplore.common.exception.CharacterAlreadyBlockedException;
import com.github.saphyra.skyxplore.userdata.community.blockedcharacter.domain.BlockedCharacter;
import com.github.saphyra.skyxplore.userdata.community.blockedcharacter.repository.BlockedCharacterDao;
import com.github.saphyra.skyxplore.userdata.community.friendship.FriendshipService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class BlockCharacterService {
    private final BlockedCharacterDao blockedCharacterDao;
    private final BlockedCharacterQueryService blockedCharacterQueryService;
    private final FriendshipService friendshipService;

    void allowBlockedCharacter(String blockedCharacterId, String characterId) {
        BlockedCharacter blockedCharacter = blockedCharacterQueryService.findByCharacterIdAndBlockedCharacterId(characterId, blockedCharacterId)
            .orElseThrow(() -> new BlockedCharacterNotFoundException(characterId, blockedCharacterId));
        blockedCharacterDao.delete(blockedCharacter);
    }

    @Transactional
    public void blockCharacter(String blockedCharacterId, String characterId) {
        if (characterId.equals(blockedCharacterId)) {
            throw new BadRequestException("You cannot block yourself.");
        }
        if (blockedCharacterQueryService.findByCharacterIdAndBlockedCharacterId(characterId, blockedCharacterId).isPresent()) {
            throw new CharacterAlreadyBlockedException(blockedCharacterId, characterId);
        }

        BlockedCharacter blockedCharacter = new BlockedCharacter(characterId, blockedCharacterId);
        friendshipService.removeContactsBetween(characterId, blockedCharacterId);
        blockedCharacterDao.save(blockedCharacter);
    }
}
