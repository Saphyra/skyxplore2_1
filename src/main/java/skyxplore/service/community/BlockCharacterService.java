package skyxplore.service.community;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skyxplore.controller.request.AllowBlockedCharacterRequest;
import skyxplore.controller.request.BlockCharacterRequest;
import skyxplore.dataaccess.db.BlockedCharacterDao;
import skyxplore.domain.community.blockeduser.BlockedCharacter;
import skyxplore.exception.BlockedCharacterNotFoundException;
import skyxplore.exception.CharacterAlreadyBlockedException;
import skyxplore.service.character.CharacterQueryService;

@Service
@Slf4j
@RequiredArgsConstructor
//TODO unit test
public class BlockCharacterService {
    private final BlockedCharacterDao blockedCharacterDao;
    private final BlockedCharacterQueryService blockedCharacterQueryService;
    private final CharacterQueryService characterQueryService;

    public void allowBlockedCharacter(AllowBlockedCharacterRequest request, String userId) {
        characterQueryService.findCharacterByIdAuthorized(request.getCharacterId(), userId);
        BlockedCharacter blockedCharacter = blockedCharacterQueryService.findByCharacterIdAndBlockedCharacterId(
            request.getCharacterId(),
            request.getBlockedCharacterId()
        );
        if(blockedCharacter == null){
            throw new BlockedCharacterNotFoundException(request);
        }
        blockedCharacterDao.delete(blockedCharacter);
    }

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
        //TODO remove community requests
        blockedCharacterDao.save(blockedCharacter);
    }
}
