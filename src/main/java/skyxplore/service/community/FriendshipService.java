package skyxplore.service.community;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.controller.request.AddFriendRequest;
import skyxplore.controller.request.DeclineFriendRequestRequest;
import skyxplore.dataaccess.db.FriendRequestDao;
import skyxplore.dataaccess.db.FriendshipDao;
import skyxplore.domain.community.blockeduser.BlockedCharacter;
import skyxplore.domain.community.friendrequest.FriendRequest;
import skyxplore.exception.CharacterBlockedException;
import skyxplore.exception.FriendRequestNotFoundException;
import skyxplore.exception.FriendshipAlreadyExistsException;
import skyxplore.exception.base.UnauthorizedException;
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
    private final FriendshipDao friendshipDao;
    private final FriendshipQueryService friendshipQueryService;
    private final IdGenerator idGenerator;

    public void addFriendRequest(AddFriendRequest request, String userId) {
        characterQueryService.findCharacterByIdAuthorized(request.getCharacterId(), userId);
        characterQueryService.findByCharacterId(request.getFriendId());
        List<BlockedCharacter> blockedCharacter = blockedCharacterQueryService.findByCharacterIdOrBlockedCharacterId(
            request.getCharacterId(),
            request.getFriendId()
        );

        if (!blockedCharacter.isEmpty()) {
            throw new CharacterBlockedException(request.getFriendId() + " is blocked.");
        }
        if (friendshipQueryService.isFriendshipOrFriendRequestAlreadyExists(request.getCharacterId(), request.getFriendId())) {
            throw new FriendshipAlreadyExistsException(request);
        }

        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setFriendRequestId(idGenerator.getRandomId());
        friendRequest.setCharacterId(request.getCharacterId());
        friendRequest.setFriendId(request.getFriendId());

        friendRequestDao.save(friendRequest);
    }

    public void declineFriendRequest(DeclineFriendRequestRequest request, String userId) {
        characterQueryService.findCharacterByIdAuthorized(request.getCharacterId(), userId);
        FriendRequest friendRequest = friendRequestDao.findById(request.getFriendRequestId());
        if (friendRequest == null) {
            throw new FriendRequestNotFoundException("Friend request not found with id " + request.getFriendRequestId());
        }
        if (!friendRequest.getCharacterId().equals(request.getCharacterId())
            && !friendRequest.getFriendId().equals(request.getCharacterId())) {
            throw new UnauthorizedException(request.getFriendRequestId() + " is not a friendRequest of character " + request.getCharacterId());
        }
        friendRequestDao.delete(friendRequest);
    }

    public void removeContactsBetween(String characterId, String blockedCharacterId) {
        friendRequestDao.getByCharacterIdOrFriendId(characterId, blockedCharacterId).forEach(friendRequestDao::delete);
        friendshipDao.getByCharacterIdOrFriendId(characterId, blockedCharacterId).forEach(friendshipDao::delete);
    }
}
