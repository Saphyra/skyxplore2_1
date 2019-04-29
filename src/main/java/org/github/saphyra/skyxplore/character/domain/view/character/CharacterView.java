package org.github.saphyra.skyxplore.character.domain.view.character;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CharacterView {
    private String characterId;
    private String characterName;
}
