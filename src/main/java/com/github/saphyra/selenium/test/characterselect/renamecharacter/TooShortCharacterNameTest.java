package com.github.saphyra.selenium.test.characterselect.renamecharacter;

import com.github.saphyra.selenium.logic.domain.SeleniumCharacter;
import com.github.saphyra.selenium.logic.domain.localization.MessageCodes;
import com.github.saphyra.selenium.logic.validator.FieldValidator;
import com.github.saphyra.selenium.test.characterselect.renamecharacter.helper.RenameCharacterTestHelper;
import lombok.Builder;
import org.openqa.selenium.WebElement;

import com.github.saphyra.selenium.logic.page.CharacterSelectPage;

import static com.github.saphyra.selenium.logic.util.Util.crop;
import static com.github.saphyra.skyxplore.userdata.character.domain.CreateCharacterRequest.CHARACTER_NAME_MIN_LENGTH;

@Builder
public class TooShortCharacterNameTest {
    private static final String MESSAGE_CODE_CHARACTER_NAME_TOO_SHORT = "CHARACTER_NAME_TOO_SHORT";

    private final RenameCharacterTestHelper renameCharacterTestHelper;
    private final CharacterSelectPage characterSelectPage;
    private final FieldValidator fieldValidator;
    private final MessageCodes messageCodes;

    public void testTooShortCharacterName() {
        renameCharacterTestHelper.initAndOpenRenamePage();

        WebElement renameCharacterField = characterSelectPage.getRenameCharacterNameField();
        renameCharacterField.clear();
        renameCharacterField.sendKeys(crop(SeleniumCharacter.createRandomCharacterName(), CHARACTER_NAME_MIN_LENGTH - 1));

        fieldValidator.verifyError(
            characterSelectPage.getInvalidRenameCharacterNameField(),
            messageCodes.get(MESSAGE_CODE_CHARACTER_NAME_TOO_SHORT),
            renameCharacterField,
            characterSelectPage.getRenameCharacterButton()
        );
    }
}
