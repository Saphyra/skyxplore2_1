package selenium.domain;

import static selenium.util.StringUtil.crop;
import static selenium.util.UserUtil.randomUID;
import static skyxplore.controller.request.character.CreateCharacterRequest.CHARACTER_NAME_MAX_LENGTH;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeleniumCharacter {
    private static final String CHARACTER_NAME_PREFIX = "character";

    private String characterName;

    public static SeleniumCharacter create() {
        return new SeleniumCharacter(createRandomCharacterName());
    }

    public static String createRandomCharacterName() {
        return crop(CHARACTER_NAME_PREFIX + randomUID(), CHARACTER_NAME_MAX_LENGTH);
    }

    public SeleniumCharacter cloneCharacter(){
        return new SeleniumCharacter(characterName);
    }
}
