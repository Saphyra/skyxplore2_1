package skyxplore.controller.request.character;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CharacterDeleteRequest {
    @NotNull
    private String characterId;
}
