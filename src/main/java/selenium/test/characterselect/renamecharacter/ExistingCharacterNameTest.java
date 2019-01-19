package selenium.test.characterselect.renamecharacter;

import lombok.Builder;
import org.openqa.selenium.WebElement;
import selenium.logic.domain.SeleniumCharacter;
import selenium.logic.page.CharacterSelectPage;
import selenium.logic.validator.FieldValidator;
import selenium.test.characterselect.renamecharacter.helper.RenameCharacterTestHelper;

@Builder
public class ExistingCharacterNameTest {
    private static final String ERROR_MESSAGE_CHARACTER_NAME_ALREADY_EXISTS = "Karakternév foglalt.";

    private final RenameCharacterTestHelper renameCharacterTestHelper;
    private final CharacterSelectPage characterSelectPage;
    private final FieldValidator fieldValidator;

    public void testExistingCharacterName() {
        SeleniumCharacter otherCharacter = renameCharacterTestHelper.registerAndCreateCharacter();
        SeleniumCharacter character = renameCharacterTestHelper.createCharacter();

        renameCharacterTestHelper.openRenameCharacterWindow(character);

        WebElement renameCharacterField = characterSelectPage.getRenameCharacterNameField();
        renameCharacterField.clear();
        renameCharacterField.sendKeys(otherCharacter.getCharacterName());

        fieldValidator.verifyError(
            characterSelectPage.getInvalidRenameCharacterNameField(),
            ERROR_MESSAGE_CHARACTER_NAME_ALREADY_EXISTS,
            renameCharacterField,
            characterSelectPage.getRenameCharacterButton()
        );
    }
}
