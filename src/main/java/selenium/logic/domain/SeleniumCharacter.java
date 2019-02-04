package selenium.logic.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static selenium.logic.util.Util.crop;
import static selenium.logic.util.Util.randomUID;
import static skyxplore.controller.request.character.CreateCharacterRequest.CHARACTER_NAME_MAX_LENGTH;

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

    public SeleniumCharacter cloneCharacter() {
        return new SeleniumCharacter(characterName);
    }
}
