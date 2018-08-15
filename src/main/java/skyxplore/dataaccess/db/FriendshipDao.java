package skyxplore.dataaccess.db;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.dataaccess.db.repository.FriendshipRepository;
import skyxplore.domain.friend.friendship.FriendshipConverter;

@Component
@Slf4j
@RequiredArgsConstructor
public class FriendshipDao {
    private final FriendshipConverter friendshipConverter;
    private final FriendshipRepository friendshipRepository;

    public List<String> getByCharacterIdAndFriendId(String characterId, String friendId) {
        return Collections.emptyList();
    }
}
