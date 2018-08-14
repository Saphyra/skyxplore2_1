package skyxplore.service.community;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.controller.request.AddFriendRequest;
import skyxplore.dataaccess.db.FriendRequestDao;
import skyxplore.domain.friend.blockeduser.BlockedCharacter;
import skyxplore.domain.friend.request.FriendRequest;
import skyxplore.exception.CharacterBlockedException;
import skyxplore.service.character.CharacterQueryService;
import skyxplore.util.IdGenerator;

@Service
@Slf4j
@RequiredArgsConstructor
public class FriendService {
    private final BlockedCharacterQueryService blockedCharacterQueryService;
    private final CharacterQueryService characterQueryService;
    private final FriendRequestDao friendRequestDao;
    private final IdGenerator idGenerator;

    public void addFriendRequest(AddFriendRequest request, String userId) {
        characterQueryService.findCharacterByIdAuthorized(request.getCharacterId(), userId);
        characterQueryService.findByCharacterId(request.getFriendId());
        List<BlockedCharacter> blockedCharacter = blockedCharacterQueryService.findByCharacterIdOrBlockedCharacterId(
            request.getCharacterId(),
            request.getFriendId()
        );

        if(!blockedCharacter.isEmpty()){
            throw new CharacterBlockedException(request.getFriendId() + " is blocked.");
        }

        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setFriendRequestId(idGenerator.getRandomId());
        friendRequest.setCharacterId(request.getCharacterId());
        friendRequest.setFriendId(friendRequest.getFriendId());

        friendRequestDao.save(friendRequest);
    }
}
