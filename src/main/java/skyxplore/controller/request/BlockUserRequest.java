package skyxplore.controller.request;

import lombok.Data;
import lombok.NonNull;

@Data
public class BlockUserRequest {
    @NonNull
    private String characterId;

    @NonNull
    private String blockedUserId;
}
