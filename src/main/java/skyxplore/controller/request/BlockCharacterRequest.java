package skyxplore.controller.request;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class BlockCharacterRequest {
    @NotNull
    private String characterId;

    @NotNull
    private String blockedCharacterId;
}
