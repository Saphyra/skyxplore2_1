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

public class FriendRequestDao {
    private final FriendRequestRepository friendRequestRepository;
    private final FriendRequestConverter friendRequestConverter;

    //TODO unit test
    public void delete(FriendRequest friendRequest) {
        friendRequestRepository.delete(friendRequestConverter.convertDomain(friendRequest));
    }

    //TODO unit test
    public void deleteByCharacterId(String characterId) {
        friendRequestRepository.deleteByCharacterId(characterId);
    }

    //TODO unit test
    public FriendRequest findById(String friendRequestId) {
        return friendRequestRepository.findById(friendRequestId).map(friendRequestConverter::convertEntity).orElse(null);
    }

    //TODO unit test
    public List<FriendRequest> getByCharacterId(String characterId) {
        return friendRequestConverter.convertEntity(friendRequestRepository.findByCharacterId(characterId));
    }

    //TODO unit test
    public List<FriendRequest> getByCharacterIdOrFriendId(String characterId, String friendId) {
        return friendRequestConverter.convertEntity(friendRequestRepository.findByCharacterIdOrFriendId(characterId, friendId));
    }

    //TODO unit test
    public List<FriendRequest> getByFriendId(String characterId) {
        return friendRequestConverter.convertEntity(friendRequestRepository.getByFriendId(characterId));
    }

    //TODO unit test
    public void save(FriendRequest friendRequest) {
        friendRequestRepository.save(friendRequestConverter.convertDomain(friendRequest));
    }
}
