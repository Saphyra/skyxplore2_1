package skyxplore.domain.friend.friendrequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendRequest {
    private String friendRequestId;
    private String characterId;
    private String friendId;
}
