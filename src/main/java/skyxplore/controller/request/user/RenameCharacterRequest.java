package skyxplore.controller.request.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RenameCharacterRequest {
    @NotNull
    private String characterId;
    @NotNull
    private String newCharacterName;
}
