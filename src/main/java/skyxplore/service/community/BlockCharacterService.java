package skyxplore.service.community;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.controller.request.BlockCharacterRequest;
import skyxplore.dataaccess.db.BlockedCharacterDao;
import skyxplore.domain.friend.blockeduser.BlockedCharacter;
import skyxplore.exception.CharacterAlreadyBlockedException;
import skyxplore.service.character.CharacterQueryService;

@Service
@Slf4j
@RequiredArgsConstructor
public class BlockCharacterService {
    private final BlockedCharacterDao blockedCharacterDao;
    private final BlockedCharacterQueryService blockedCharacterQueryService;
    private final CharacterQueryService characterQueryService;

    @Transactional
    public void blockCharacter(BlockCharacterRequest request, String userId) {
        characterQueryService.findCharacterByIdAuthorized(request.getCharacterId(), userId);
        characterQueryService.findByCharacterId(request.getBlockedCharacterId());
        if (blockedCharacterQueryService.findByCharacterIdAndBlockedCharacterId(
            request.getCharacterId(),
            request.getBlockedCharacterId()
        ) != null) {
            throw new CharacterAlreadyBlockedException(request);
        }

        BlockedCharacter blockedCharacter = new BlockedCharacter(request);
        //TODO remove friendships
        //TODO remove friend requests
        blockedCharacterDao.save(blockedCharacter);
    }
}
