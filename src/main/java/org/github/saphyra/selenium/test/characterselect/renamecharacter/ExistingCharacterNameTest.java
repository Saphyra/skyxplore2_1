package org.github.saphyra.selenium.test.characterselect.renamecharacter;

import lombok.Builder;
import org.openqa.selenium.WebElement;
import org.github.saphyra.selenium.logic.domain.localization.MessageCodes;
import org.github.saphyra.selenium.logic.domain.SeleniumCharacter;
import org.github.saphyra.selenium.logic.page.CharacterSelectPage;
import org.github.saphyra.selenium.logic.validator.FieldValidator;
import org.github.saphyra.selenium.test.characterselect.renamecharacter.helper.RenameCharacterTestHelper;

@Builder
public class ExistingCharacterNameTest {
    private static final String MESSAGE_CODE_CHARACTER_NAME_ALREADY_EXISTS = "CHARACTER_NAME_ALREADY_EXISTS";

    private final RenameCharacterTestHelper renameCharacterTestHelper;
    private final CharacterSelectPage characterSelectPage;
    private final FieldValidator fieldValidator;
    private final MessageCodes messageCodes;

    public void testExistingCharacterName() {
        SeleniumCharacter otherCharacter = renameCharacterTestHelper.registerAndCreateCharacter();
        SeleniumCharacter character = renameCharacterTestHelper.createCharacter();

        renameCharacterTestHelper.openRenameCharacterWindow(character);

        WebElement renameCharacterField = characterSelectPage.getRenameCharacterNameField();
        renameCharacterField.clear();
        renameCharacterField.sendKeys(otherCharacter.getCharacterName());

        fieldValidator.verifyError(
            characterSelectPage.getInvalidRenameCharacterNameField(),
            messageCodes.get(MESSAGE_CODE_CHARACTER_NAME_ALREADY_EXISTS),
            renameCharacterField,
            characterSelectPage.getRenameCharacterButton()
        );
    }
}
