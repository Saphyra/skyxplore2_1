package selenium.aanew.test.characterselect.renamecharacter;

import lombok.Builder;
import org.openqa.selenium.WebElement;
import selenium.aanew.logic.domain.SeleniumCharacter;
import selenium.aanew.logic.page.CharacterSelectPage;
import selenium.aanew.logic.validator.FieldValidator;
import selenium.aanew.test.characterselect.renamecharacter.helper.RenameCharacterTestHelper;

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
