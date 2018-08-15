package skyxplore.service.community;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.controller.request.AddFriendRequest;
import skyxplore.dataaccess.db.FriendRequestDao;
import skyxplore.domain.community.blockeduser.BlockedCharacter;
import skyxplore.domain.community.friendrequest.FriendRequest;
import skyxplore.exception.CharacterBlockedException;
import skyxplore.exception.FriendshipAlreadyExistsException;
import skyxplore.service.character.CharacterQueryService;
import skyxplore.util.IdGenerator;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FriendshipService {
    private final BlockedCharacterQueryService blockedCharacterQueryService;
    private final CharacterQueryService characterQueryService;
    private final FriendRequestDao friendRequestDao;
    private final FriendshipQueryService friendshipQueryService;
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
        if(friendshipQueryService.isFriendshipOrFriendRequestAlreadyExists(request.getCharacterId(), request.getFriendId())){
            throw new FriendshipAlreadyExistsException(request);
        }

        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setFriendRequestId(idGenerator.getRandomId());
        friendRequest.setCharacterId(request.getCharacterId());
        friendRequest.setFriendId(request.getFriendId());

        friendRequestDao.save(friendRequest);
    }
}
