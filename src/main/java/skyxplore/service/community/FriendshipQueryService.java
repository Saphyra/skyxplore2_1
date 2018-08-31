package skyxplore.service.community;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.dataaccess.db.FriendRequestDao;
import skyxplore.dataaccess.db.FriendshipDao;
import skyxplore.domain.community.friendrequest.FriendRequest;
import skyxplore.domain.community.friendship.Friendship;
import skyxplore.service.character.CharacterQueryService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
//TODO unit test
public class FriendshipQueryService {
    private final CharacterQueryService characterQueryService;
    private final FriendRequestDao friendRequestDao;
    private final FriendshipDao friendshipDao;

    public List<Friendship> getFriends(String characterId) {
        return friendshipDao.getFriendshipsOfCharacter(characterId);
    }

    public Integer getNumberOfFriendRequests(String characterId) {
        return getReceivedFriendRequests(characterId).size();
    }

    public List<FriendRequest> getReceivedFriendRequests(String characterId) {
        characterQueryService.findByCharacterId(characterId);
        return friendRequestDao.getByFriendId(characterId)
            .stream()
            .map(this::swapIds)
            .collect(Collectors.toList());
    }

    private FriendRequest swapIds(FriendRequest request){
        String characterId = request.getCharacterId();
        request.setCharacterId(request.getFriendId());
        request.setFriendId(characterId);
        return request;
    }

    public List<FriendRequest> getSentFriendRequests(String characterId, String userId) {
        characterQueryService.findCharacterByIdAuthorized(characterId, userId);
        return friendRequestDao.getByCharacterId(characterId);
    }

    public boolean isFriendshipOrFriendRequestAlreadyExists(String characterId, String friendId) {
        return friendRequestDao.getByCharacterIdOrFriendId(characterId, friendId).size() > 0
            || friendshipDao.getByCharacterIdOrFriendId(characterId, friendId).size() > 0;
    }
}
