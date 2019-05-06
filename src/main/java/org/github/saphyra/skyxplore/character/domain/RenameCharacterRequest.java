package org.github.saphyra.skyxplore.character.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RenameCharacterRequest {
    @NotNull
    @Size(min = 3, max = 30)
    private String newCharacterName;

    @NotNull
    private String characterId;
}
