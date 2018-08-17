package skyxplore.dataaccess.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.db.repository.FriendRequestRepository;
import skyxplore.domain.community.friendrequest.FriendRequest;
import skyxplore.domain.community.friendrequest.FriendRequestConverter;
import skyxplore.domain.community.friendrequest.FriendRequestEntity;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
//TODO unit test
public class FriendRequestDao {
    private final FriendRequestRepository friendRequestRepository;
    private final FriendRequestConverter friendRequestConverter;

    public void delete(FriendRequest friendRequest){
        friendRequestRepository.delete(friendRequestConverter.convertDomain(friendRequest));
    }

    public FriendRequest findById(String friendRequestId) {
        Optional<FriendRequestEntity> friendRequestEntity = friendRequestRepository.findById(friendRequestId);
        return friendRequestEntity.map(friendRequestConverter::convertEntity).orElse(null);
    }

    public List<FriendRequest> getByCharacterId(String characterId) {
        return friendRequestConverter.convertEntity(friendRequestRepository.findByCharacterId(characterId));
    }

    public List<FriendRequest> getByCharacterIdOrFriendId(String characterId, String friendId) {
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
