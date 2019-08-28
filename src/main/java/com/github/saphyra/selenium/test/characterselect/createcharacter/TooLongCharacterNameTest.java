package com.github.saphyra.selenium.test.characterselect.createcharacter;

import static com.github.saphyra.skyxplore.userdata.character.domain.CreateCharacterRequest.CHARACTER_NAME_MAX_LENGTH;

import org.openqa.selenium.WebElement;

import com.github.saphyra.selenium.logic.validator.FieldValidator;
import lombok.Builder;
import com.github.saphyra.selenium.logic.page.CharacterSelectPage;
import com.github.saphyra.selenium.test.characterselect.common.CharacterSelectTestHelper;

@Builder
public class TooLongCharacterNameTest {
    private static final String TOO_LONG_CHARACTER_NAME;
    private static final String MESSAGE_CODE_CHARACTER_NAME_TOO_LONG = "CHARACTER_NAME_TOO_LONG";

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
    private final MessageCodes messageCodes;

    public void testTooLongCharacterName() {
        characterSelectTestHelper.registerUser();

        WebElement newCharacterNameField = characterSelectPage.getNewCharacterNameField();
        newCharacterNameField.sendKeys(TOO_LONG_CHARACTER_NAME);

        fieldValidator.verifyError(
            characterSelectPage.getInvalidNewCharacterNameField(),
            messageCodes.get(MESSAGE_CODE_CHARACTER_NAME_TOO_LONG),
            newCharacterNameField,
            characterSelectPage.getCreateCharacterButton()
        );
    }
}
