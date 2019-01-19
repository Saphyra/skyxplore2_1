package selenium.test.characterselect.renamecharacter;

import lombok.Builder;
import org.openqa.selenium.WebElement;
import selenium.logic.domain.SeleniumCharacter;
import selenium.logic.page.CharacterSelectPage;
import selenium.logic.validator.FieldValidator;
import selenium.test.characterselect.renamecharacter.helper.RenameCharacterTestHelper;

import static selenium.logic.util.Util.crop;
import static skyxplore.controller.request.character.CreateCharacterRequest.CHARACTER_NAME_MIN_LENGTH;

@Builder
public class TooShortCharacterNameTest {
    private static final String ERROR_MESSAGE_CHARACTER_NAME_TOO_SHORT = "Karakternév túl rövid. (Minimum 3 karakter)";

    private final RenameCharacterTestHelper renameCharacterTestHelper;
    private final CharacterSelectPage characterSelectPage;
    private final FieldValidator fieldValidator;

    public void testTooShortCharacterName() {
        renameCharacterTestHelper.initAndOpenRenamePage();

        WebElement renameCharacterField = characterSelectPage.getRenameCharacterNameField();
        renameCharacterField.clear();
        renameCharacterField.sendKeys(crop(SeleniumCharacter.createRandomCharacterName(), CHARACTER_NAME_MIN_LENGTH - 1));

        fieldValidator.verifyError(
            characterSelectPage.getInvalidRenameCharacterNameField(),
            ERROR_MESSAGE_CHARACTER_NAME_TOO_SHORT,
            renameCharacterField,
            characterSelectPage.getRenameCharacterButton()
        );
    }
}
