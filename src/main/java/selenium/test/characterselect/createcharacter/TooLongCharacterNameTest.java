package selenium.test.characterselect.createcharacter;

import static skyxplore.controller.request.character.CreateCharacterRequest.CHARACTER_NAME_MAX_LENGTH;

import org.openqa.selenium.WebElement;

import lombok.Builder;
import selenium.logic.page.CharacterSelectPage;
import selenium.logic.validator.FieldValidator;
import selenium.test.characterselect.common.CharacterSelectTestHelper;

@Builder
public class TooLongCharacterNameTest {
    private static final String TOO_LONG_CHARACTER_NAME;
    private static final String ERROR_MESSAGE_CHARACTER_NAME_TOO_LONG = "Karakternév túl hosszú. (Maximum 30 karakter)";

    static {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i <= CHARACTER_NAME_MAX_LENGTH + 1; i++) {
            builder.append("a");
        }
        TOO_LONG_CHARACTER_NAME = builder.toString();
    }

    private final CharacterSelectTestHelper characterSelectTestHelper;
    private final CharacterSelectPage characterSelectPage;
    private final FieldValidator fieldValidator;

    public void testTooLongCharacterName() {
        characterSelectTestHelper.registerUser();

        WebElement newCharacterNameField = characterSelectPage.getNewCharacterNameField();
        newCharacterNameField.sendKeys(TOO_LONG_CHARACTER_NAME);

        fieldValidator.verifyError(
            characterSelectPage.getInvalidNewCharacterNameField(),
            ERROR_MESSAGE_CHARACTER_NAME_TOO_LONG,
            newCharacterNameField,
            characterSelectPage.getCreateCharacterButton()
        );
    }
}
