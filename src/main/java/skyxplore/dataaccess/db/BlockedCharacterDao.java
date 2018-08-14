package skyxplore.dataaccess.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.db.repository.BlockedCharacterRepository;
import skyxplore.domain.friend.blockeduser.BlockedCharacterConverter;

@Component
@RequiredArgsConstructor
@Slf4j
public class BlockedCharacterDao {
    private final BlockedCharacterRepository blockedCharacterRepository;
    private final BlockedCharacterConverter blockedCharacterConverter;
}
