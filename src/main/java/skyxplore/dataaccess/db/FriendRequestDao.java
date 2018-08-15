package skyxplore.dataaccess.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.db.repository.FriendRequestRepository;
import skyxplore.domain.community.friendrequest.FriendRequest;
import skyxplore.domain.community.friendrequest.FriendRequestConverter;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
//TODO unit test
public class FriendRequestDao {
    private final FriendRequestRepository friendRequestRepository;
    private final FriendRequestConverter friendRequestConverter;

    public List<FriendRequest> getByCharacterId(String characterId) {
        return friendRequestConverter.convertEntity(friendRequestRepository.findByCharacterId(characterId));
    }

    public List<FriendRequest> getByCharacterIdAndFriendId(String characterId, String friendId) {
        return friendRequestConverter.convertEntity(friendRequestRepository.findByCharacterIdOrFriendId(characterId, friendId));
    }

    public List<FriendRequest> getByFriendId(String characterId) {
        return friendRequestConverter.convertEntity(friendRequestRepository.getByFriendId(characterId));
    }

    public FriendRequest save(FriendRequest friendRequest) {
        return friendRequestConverter.convertEntity(
            friendRequestRepository.save(
                friendRequestConverter.convertDomain(friendRequest)
            )
        );
    }
}
