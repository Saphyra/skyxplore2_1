package skyxplore.restcontroller.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RenameCharacterRequest {
    @NotNull
    private Long characterId;
    @NotNull
    private String newCharacterName;
}
