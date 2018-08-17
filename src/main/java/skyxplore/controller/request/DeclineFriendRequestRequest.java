package skyxplore.controller.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DeclineFriendRequestRequest {
    @NotNull
    private String characterId;

    @NotNull
    private String friendRequestId;
}
