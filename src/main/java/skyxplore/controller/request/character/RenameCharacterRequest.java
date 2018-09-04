package skyxplore.controller.request.character;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RenameCharacterRequest {
    @NotNull
    private String characterId;
    @NotNull
    @Size(min = 1, max = 30)
    private String newCharacterName;
}
