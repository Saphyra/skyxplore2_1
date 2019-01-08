package selenium.aanew.test.characterselect.createcharacter;

import org.openqa.selenium.WebElement;

import lombok.Builder;
import selenium.aanew.logic.domain.SeleniumCharacter;
import selenium.aanew.logic.page.CharacterSelectPage;
import selenium.aanew.logic.validator.FieldValidator;
import selenium.aanew.test.characterselect.createcharacter.helper.CreateCharacterTestHelper;

@Builder
public class ExistingCharacterNameTest {
    private static final String ERROR_MESSAGE_CHARACTER_NAME_ALREADY_EXISTS = "Karakternév foglalt.";

    private final CreateCharacterTestHelper createCharacterTestHelper;
    private final CharacterSelectPage characterSelectPage;
    private final FieldValidator fieldValidator;

    public void testExistingCharacterName() {
        createCharacterTestHelper.registerUser();
        SeleniumCharacter character = createCharacterTestHelper.createCharacter();

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
