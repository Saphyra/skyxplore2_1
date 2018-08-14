package skyxplore.service.community;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.dataaccess.db.FriendRequestDao;
import skyxplore.dataaccess.db.FriendshipDao;

@Service
@Slf4j
@RequiredArgsConstructor
public class FriendshipQueryService {
    private final FriendRequestDao friendRequestDao;
    private final FriendshipDao friendshipDao;

    public boolean isFriendshipOrFriendRequestAlreadyExists(String characterId, String friendId) {
        return friendRequestDao.getByCharacterIdAndFriendId(characterId, friendId).size() > 0
            || friendshipDao.getByCharacterIdAndFriendId(characterId, friendId).size() > 0;
    }
}
