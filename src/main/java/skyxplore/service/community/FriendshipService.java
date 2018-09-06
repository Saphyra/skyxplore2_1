package skyxplore.service.community;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.dataaccess.db.FriendRequestDao;
import skyxplore.dataaccess.db.FriendshipDao;
import skyxplore.domain.community.blockedcharacter.BlockedCharacter;
import skyxplore.domain.community.friendrequest.FriendRequest;
import skyxplore.domain.community.friendship.Friendship;
import skyxplore.exception.CharacterBlockedException;
import skyxplore.exception.FriendRequestNotFoundException;
import skyxplore.exception.FriendshipAlreadyExistsException;
import skyxplore.exception.base.UnauthorizedException;
import skyxplore.service.character.CharacterQueryService;
import skyxplore.util.IdGenerator;

@Service
@Slf4j
@RequiredArgsConstructor
//TODO unit test
public class FriendshipService {
    private final BlockedCharacterQueryService blockedCharacterQueryService;
    private final CharacterQueryService characterQueryService;
    private final FriendRequestDao friendRequestDao;
    private final FriendshipDao friendshipDao;
    private final FriendshipQueryService friendshipQueryService;
    private final IdGenerator idGenerator;

    @Transactional
    public void acceptFriendRequest(String friendRequestId, String characterId) {
        FriendRequest friendRequest = friendRequestDao.findById(friendRequestId);
        if (friendRequest == null) {
            throw new FriendRequestNotFoundException("FriendRequest not found with id " + friendRequestId);
        }
        if (!friendRequest.getFriendId().equals(characterId)) {
            throw new UnauthorizedException(characterId + "has no rights to acccept friendRequest " + friendRequestId);
        }

        Friendship friendship = Friendship.builder()
            .friendshipId(idGenerator.getRandomId())
            .characterId(friendRequest.getCharacterId())
            .friendId(friendRequest.getFriendId())
            .build();
        friendshipDao.save(friendship);
        friendRequestDao.delete(friendRequest);
    }

    public void addFriendRequest(String friendId, String characterId) {
        characterQueryService.findByCharacterId(friendId);
        List<BlockedCharacter> blockedCharacter = blockedCharacterQueryService.findByCharacterIdOrBlockedCharacterId(characterId, friendId);

        if (!blockedCharacter.isEmpty()) {
            throw new CharacterBlockedException(friendId + " is blocked.");
        }
        if (friendshipQueryService.isFriendshipOrFriendRequestAlreadyExists(characterId, friendId)) {
            throw new FriendshipAlreadyExistsException(friendId, characterId);
        }

        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setFriendRequestId(idGenerator.getRandomId());
        friendRequest.setCharacterId(characterId);
        friendRequest.setFriendId(friendId);

        friendRequestDao.save(friendRequest);
    }

    public void declineFriendRequest(String friendRequestId, String characterId) {
        FriendRequest friendRequest = friendRequestDao.findById(friendRequestId);
        if (friendRequest == null) {
            throw new FriendRequestNotFoundException("Friend request not found with id " + friendRequestId);
        }
        if (!friendRequest.getCharacterId().equals(characterId)
            && !friendRequest.getFriendId().equals(characterId)) {
            throw new UnauthorizedException(friendRequestId + " is not a friendRequest of character " + characterId);
        }
        friendRequestDao.delete(friendRequest);
    }

    public void deleteFriendship(String friendshipId, String characterId) {
        Friendship friendship = friendshipDao.getByFriendshipId(friendshipId);
        if (!friendship.getFriendId().equals(characterId)
            && !friendship.getCharacterId().equals(characterId)) {
            throw new UnauthorizedException(characterId + " has no access to friendship " + friendshipId);
        }
        friendshipDao.delete(friendship);
    }

    public void removeContactsBetween(String characterId, String blockedCharacterId) {
        friendRequestDao.getByCharacterIdOrFriendId(characterId, blockedCharacterId).forEach(friendRequestDao::delete);
        friendshipDao.getByCharacterIdOrFriendId(characterId, blockedCharacterId).forEach(friendshipDao::delete);
    }
}
