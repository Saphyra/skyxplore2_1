package com.github.saphyra.selenium.logic.domain;

import static com.github.saphyra.selenium.logic.util.Util.crop;
import static com.github.saphyra.selenium.logic.util.Util.randomUID;
import static com.github.saphyra.skyxplore.userdata.character.domain.CreateCharacterRequest.CHARACTER_NAME_MAX_LENGTH;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeleniumCharacter {
    public static final String CHARACTER_NAME_PREFIX = "character-";

    private String characterName;

    public static SeleniumCharacter create() {
        return new SeleniumCharacter(createRandomCharacterName());
    }

    public static String createRandomCharacterName() {
        return crop(CHARACTER_NAME_PREFIX + randomUID(), CHARACTER_NAME_MAX_LENGTH);
    }
}
