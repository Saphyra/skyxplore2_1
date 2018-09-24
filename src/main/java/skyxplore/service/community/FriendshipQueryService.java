package skyxplore.service.community;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.dataaccess.db.FriendRequestDao;
import skyxplore.dataaccess.db.FriendshipDao;
import skyxplore.domain.community.friendrequest.FriendRequest;
import skyxplore.domain.community.friendship.Friendship;
import skyxplore.exception.FriendRequestNotFoundException;
import skyxplore.exception.FriendshipNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FriendshipQueryService {
    private final FriendRequestDao friendRequestDao;
    private final FriendshipDao friendshipDao;

    //TODO unit test
    public FriendRequest findFriendRequestById(String friendRequestId) {
        FriendRequest friendRequest = friendRequestDao.findById(friendRequestId);
        if (friendRequest == null) {
            throw new FriendRequestNotFoundException("FriendRequest not found with id " + friendRequestId);
        }
        return friendRequest;
    }

    //TODO unit test
    public Friendship findFriendshipById(String friendshipId) {
        Friendship friendship = friendshipDao.findById(friendshipId);
        if(friendship == null){
            throw new FriendshipNotFoundException("Friendship not found with id " + friendshipId);
        }
        return friendship;
    }

    public List<Friendship> getFriends(String characterId) {
        return friendshipDao.getFriendshipsOfCharacter(characterId);
    }

    public Integer getNumberOfFriendRequests(String characterId) {
        return getReceivedFriendRequests(characterId).size();
    }

    public List<FriendRequest> getReceivedFriendRequests(String characterId) {
        return friendRequestDao.getByFriendId(characterId).stream()
            .map(this::swapIds)
            .collect(Collectors.toList());
    }

    private FriendRequest swapIds(FriendRequest request) {
        String characterId = request.getCharacterId();
        request.setCharacterId(request.getFriendId());
        request.setFriendId(characterId);
        return request;
    }

    public List<FriendRequest> getSentFriendRequests(String characterId) {
        return friendRequestDao.getByCharacterId(characterId);
    }

    public boolean isFriendshipAlreadyExists(String characterId, String friendId){
        return friendshipDao.getByCharacterIdOrFriendId(characterId, friendId).size() > 0;
    }

    public boolean isFriendRequestAlreadyExists(String characterId, String friendId){
        return friendRequestDao.getByCharacterIdOrFriendId(characterId, friendId).size() > 0;
    }

    public boolean isFriendshipOrFriendRequestAlreadyExists(String characterId, String friendId) {
        return isFriendRequestAlreadyExists(characterId, friendId)
            || isFriendshipAlreadyExists(characterId, friendId);
    }
}
