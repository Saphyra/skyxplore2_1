package skyxplore.service.community;

import org.github.saphyra.skyxplore.character.CharacterQueryService;
import org.github.saphyra.skyxplore.community.blockedcharacter.BlockedCharacterQueryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.github.saphyra.skyxplore.community.blockedcharacter.repository.BlockedCharacterDao;
import org.github.saphyra.skyxplore.community.blockedcharacter.domain.BlockedCharacter;
import org.github.saphyra.skyxplore.common.exception.BlockedCharacterNotFoundException;
import org.github.saphyra.skyxplore.common.exception.CharacterAlreadyBlockedException;

@Service
@Slf4j
@RequiredArgsConstructor
public class BlockCharacterService {
    private final BlockedCharacterDao blockedCharacterDao;
    private final BlockedCharacterQueryService blockedCharacterQueryService;
    private final CharacterQueryService characterQueryService;
    private final FriendshipService friendshipService;

    public void allowBlockedCharacter(String blockedCharacterId, String characterId) {
        BlockedCharacter blockedCharacter = blockedCharacterQueryService.findByCharacterIdAndBlockedCharacterId(characterId, blockedCharacterId);
        if (blockedCharacter == null) {
            throw new BlockedCharacterNotFoundException(characterId, blockedCharacterId);
        }
        blockedCharacterDao.delete(blockedCharacter);
    }

    @Transactional
    public void blockCharacter(String blockedCharacterId, String characterId) {
        if (characterId.equals(blockedCharacterId)) {
            throw new BadRequestException("You cannot block yourself.");
        }
        characterQueryService.findByCharacterId(blockedCharacterId);
        if (blockedCharacterQueryService.findByCharacterIdAndBlockedCharacterId(characterId, blockedCharacterId) != null) {
            throw new CharacterAlreadyBlockedException(blockedCharacterId, characterId);
        }

        BlockedCharacter blockedCharacter = new BlockedCharacter(characterId, blockedCharacterId);
        friendshipService.removeContactsBetween(characterId, blockedCharacterId);
        blockedCharacterDao.save(blockedCharacter);
    }
}
