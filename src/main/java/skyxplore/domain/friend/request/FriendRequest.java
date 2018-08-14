package skyxplore.domain.friend.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class FriendRequest {
    private String friendRequestId;
    private String characterId;
    private String friendId;
}
