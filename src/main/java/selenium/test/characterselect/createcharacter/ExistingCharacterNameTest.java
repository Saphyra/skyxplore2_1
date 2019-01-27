package selenium.test.characterselect.createcharacter;

import org.openqa.selenium.WebElement;

import lombok.Builder;
import selenium.logic.domain.SeleniumCharacter;
import selenium.logic.page.CharacterSelectPage;
import selenium.logic.validator.FieldValidator;
import selenium.test.characterselect.common.CharacterSelectTestHelper;

@Builder
public class ExistingCharacterNameTest {
    private static final String ERROR_MESSAGE_CHARACTER_NAME_ALREADY_EXISTS = "aef";

    private final CharacterSelectTestHelper characterSelectTestHelper;
    private final CharacterSelectPage characterSelectPage;
    private final FieldValidator fieldValidator;

    public void testExistingCharacterName() {
        characterSelectTestHelper.registerUser();
        SeleniumCharacter character = characterSelectTestHelper.createCharacter();

        WebElement newCharacterNameField = characterSelectPage.getNewCharacterNameField();
        newCharacterNameField.sendKeys(character.getCharacterName());

        fieldValidator.verifyError(
            characterSelectPage.getInvalidNewCharacterNameField(),
            ERROR_MESSAGE_CHARACTER_NAME_ALREADY_EXISTS,
            newCharacterNameField,
            characterSelectPage.getCreateCharacterButton()
        );
    }
}
