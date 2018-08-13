package skyxplore.dataaccess.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.db.repository.BlockedUserRepository;
import skyxplore.domain.friend.blockeduser.BlockedUserConverter;

@Component
@RequiredArgsConstructor
@Slf4j
public class BlockedUserDao {
    private final BlockedUserRepository blockedUserRepository;
    private final BlockedUserConverter blockedUserConverter;
}
