package skyxplore.restcontroller.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RenameCharacterRequest {
    @NotNull
    private String characterId;
    @NotNull
    private String newCharacterName;
}
