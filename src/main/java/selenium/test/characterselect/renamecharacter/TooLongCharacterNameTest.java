package selenium.test.characterselect.renamecharacter;

import lombok.Builder;
import org.openqa.selenium.WebElement;
import selenium.logic.page.CharacterSelectPage;
import selenium.logic.validator.FieldValidator;
import selenium.test.characterselect.renamecharacter.helper.RenameCharacterTestHelper;

import static skyxplore.controller.request.character.CreateCharacterRequest.CHARACTER_NAME_MAX_LENGTH;

@Builder
public class TooLongCharacterNameTest {
    private static final String TOO_LONG_CHARACTER_NAME;

    static {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i <= CHARACTER_NAME_MAX_LENGTH + 1; i++) {
            builder.append("a");
        }
        TOO_LONG_CHARACTER_NAME = builder.toString();
    }

    private static final String ERROR_MESSAGE_CHARACTER_NAME_TOO_LONG = "Karakternév túl hosszú. (Maximum 30 karakter)";

    private final RenameCharacterTestHelper renameCharacterTestHelper;
    private final CharacterSelectPage characterSelectPage;
    private final FieldValidator fieldValidator;

    public void testTooLongCharacterName() {
        renameCharacterTestHelper.initAndOpenRenamePage();

        WebElement renameCharacterField = characterSelectPage.getRenameCharacterNameField();
        renameCharacterField.clear();
        renameCharacterField.sendKeys(TOO_LONG_CHARACTER_NAME);

        fieldValidator.verifyError(
            characterSelectPage.getInvalidRenameCharacterNameField(),
            ERROR_MESSAGE_CHARACTER_NAME_TOO_LONG,
            renameCharacterField,
            characterSelectPage.getRenameCharacterButton()
        );
    }
}
