package skyxplore.controller.request.community;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddFriendRequest {
    @NotNull
    private String characterId;

    @NotNull
    private String friendId;
}
