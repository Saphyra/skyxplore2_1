package org.github.saphyra.skyxplore.character.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCharacterRequest {
    public static final int CHARACTER_NAME_MIN_LENGTH = 3;
    public static final int CHARACTER_NAME_MAX_LENGTH = 30;

    @NotNull
    @Size(min = CHARACTER_NAME_MIN_LENGTH, max = CHARACTER_NAME_MAX_LENGTH)
    private String characterName;
}
