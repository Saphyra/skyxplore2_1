package skyxplore.service.community;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.dataaccess.db.FriendRequestDao;
import skyxplore.dataaccess.db.FriendshipDao;
import skyxplore.domain.community.friendrequest.FriendRequest;
import skyxplore.service.character.CharacterQueryService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
//TODO unit test
public class FriendshipQueryService {
    private final CharacterQueryService characterQueryService;
    private final FriendRequestDao friendRequestDao;
    private final FriendshipDao friendshipDao;

    public List<FriendRequest> getReceivedFriendRequests(String characterId, String userId) {
        characterQueryService.findCharacterByIdAuthorized(characterId, userId);
        return friendRequestDao.getByFriendId(characterId);
    }

    public List<FriendRequest> getSentFriendRequests(String characterId, String userId) {
        characterQueryService.findCharacterByIdAuthorized(characterId, userId);
        return friendRequestDao.getByCharacterId(characterId);
    }

    public boolean isFriendshipOrFriendRequestAlreadyExists(String characterId, String friendId) {
        return friendRequestDao.getByCharacterIdAndFriendId(characterId, friendId).size() > 0
            || friendshipDao.getByCharacterIdAndFriendId(characterId, friendId).size() > 0;
    }
}
