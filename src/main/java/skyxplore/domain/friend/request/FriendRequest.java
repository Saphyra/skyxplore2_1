package skyxplore.domain.friend.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FriendRequest {
    private Long friendRequestId;
    private String characterId;
    private String friendId;
}
