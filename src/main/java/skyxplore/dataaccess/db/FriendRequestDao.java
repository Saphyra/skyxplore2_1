package skyxplore.dataaccess.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.db.repository.FriendRequestRepository;
import skyxplore.domain.friend.request.FriendRequest;
import skyxplore.domain.friend.request.FriendRequestConverter;

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
}
