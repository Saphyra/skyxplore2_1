package skyxplore.dataaccess.db;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.db.repository.FriendRequestRepository;
import skyxplore.domain.friend.friendrequest.FriendRequest;
import skyxplore.domain.friend.friendrequest.FriendRequestConverter;

@Component
@Slf4j
@RequiredArgsConstructor
public class FriendRequestDao {
    private final FriendRequestRepository friendRequestRepository;
    private final FriendRequestConverter friendRequestConverter;

    public FriendRequest save(FriendRequest friendRequest) {
        return friendRequestConverter.convertEntity(
            friendRequestRepository.save(
                friendRequestConverter.convertDomain(friendRequest)
            )
        );
    }

    public List<FriendRequest> getByCharacterIdAndFriendId(String characterId, String friendId) {
        return friendRequestConverter.convertEntity(friendRequestRepository.findByCharacterIdOrFriendId(characterId, friendId));
    }
}
