package skyxplore.controller.request;

import lombok.Data;
import lombok.NonNull;

@Data
public class AddFriendRequest {
    @NonNull
    private String characterId;

    @NonNull
    private String friendId;
}
