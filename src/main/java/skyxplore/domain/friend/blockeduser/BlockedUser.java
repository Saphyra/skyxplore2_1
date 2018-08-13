package skyxplore.domain.friend.blockeduser;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BlockedUser {
    private Long blockedUserEntityId;
    private String characterId;
    private String blockedUserId;
}
