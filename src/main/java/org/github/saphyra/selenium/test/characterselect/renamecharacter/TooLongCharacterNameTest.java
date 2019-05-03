package org.github.saphyra.selenium.test.characterselect.renamecharacter;

import lombok.Builder;
import org.openqa.selenium.WebElement;
import org.github.saphyra.selenium.logic.domain.MessageCodes;
import org.github.saphyra.selenium.logic.page.CharacterSelectPage;
import org.github.saphyra.selenium.logic.validator.FieldValidator;
import org.github.saphyra.selenium.test.characterselect.renamecharacter.helper.RenameCharacterTestHelper;

import static org.github.saphyra.skyxplore.character.domain.CreateCharacterRequest.CHARACTER_NAME_MAX_LENGTH;

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

    private static final String MESSAGE_CODE_CHARACTER_NAME_TOO_LONG = "CHARACTER_NAME_TOO_LONG";

    private final RenameCharacterTestHelper renameCharacterTestHelper;
    private final CharacterSelectPage characterSelectPage;
    private final FieldValidator fieldValidator;
    private final MessageCodes messageCodes;

    public void testTooLongCharacterName() {
        renameCharacterTestHelper.initAndOpenRenamePage();

        WebElement renameCharacterField = characterSelectPage.getRenameCharacterNameField();
        renameCharacterField.clear();
        renameCharacterField.sendKeys(TOO_LONG_CHARACTER_NAME);

        fieldValidator.verifyError(
            characterSelectPage.getInvalidRenameCharacterNameField(),
            messageCodes.get(MESSAGE_CODE_CHARACTER_NAME_TOO_LONG),
            renameCharacterField,
            characterSelectPage.getRenameCharacterButton()
        );
    }
}
