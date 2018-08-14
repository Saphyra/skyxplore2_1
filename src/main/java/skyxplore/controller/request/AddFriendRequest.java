package skyxplore.controller.request;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class AddFriendRequest {
    @NotNull
    private String characterId;

    @NotNull
    private String friendId;
}
