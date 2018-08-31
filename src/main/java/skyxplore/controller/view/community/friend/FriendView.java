package skyxplore.controller.view.community.friend;

import lombok.Data;

@Data
public class FriendView {
    private String friendshipId;
    private String friendId;
    private String friendName;
    private Boolean active;
}
