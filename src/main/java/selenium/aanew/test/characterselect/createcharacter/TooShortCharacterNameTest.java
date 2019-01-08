package selenium.aanew.test.characterselect.createcharacter;

import static selenium.aanew.logic.util.Util.crop;
import static skyxplore.controller.request.character.CreateCharacterRequest.CHARACTER_NAME_MIN_LENGTH;

import org.openqa.selenium.WebElement;

import lombok.Builder;
import selenium.aanew.logic.domain.SeleniumCharacter;
import selenium.aanew.logic.page.CharacterSelectPage;
import selenium.aanew.logic.validator.FieldValidator;
import selenium.aanew.test.characterselect.createcharacter.helper.CreateCharacterTestHelper;

@Builder
public class TooShortCharacterNameTest {
    private static final String ERROR_MESSAGE_CHARACTER_NAME_TOO_SHORT = "Karakternév túl rövid. (Minimum 3 karakter)";

    private final CreateCharacterTestHelper createCharacterTestHelper;
    private final CharacterSelectPage characterSelectPage;
    private final FieldValidator fieldValidator;

    public void testTooShortCharacterName() {
        createCharacterTestHelper.registerUser();

        WebElement newCharacterNameField = characterSelectPage.getNewCharacterNameField();
        newCharacterNameField.sendKeys(crop(SeleniumCharacter.createRandomCharacterName(), CHARACTER_NAME_MIN_LENGTH - 1));

        fieldValidator.verifyError(
            characterSelectPage.getInvalidNewCharacterNameField(),
            ERROR_MESSAGE_CHARACTER_NAME_TOO_SHORT,
            newCharacterNameField,
            characterSelectPage.getCreateCharacterButton()
        );
    }
}
