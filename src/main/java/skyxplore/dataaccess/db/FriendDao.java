package skyxplore.dataaccess.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.db.repository.FriendRequestRepository;
import skyxplore.domain.friend.request.FriendRequestConverter;

@Component
@Slf4j
@RequiredArgsConstructor
public class FriendDao {
    private final FriendRequestRepository friendRequestRepository;
    private final FriendRequestConverter friendRequestConverter;
}
