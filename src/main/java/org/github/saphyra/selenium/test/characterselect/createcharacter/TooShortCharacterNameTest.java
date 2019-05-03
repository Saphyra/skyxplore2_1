package org.github.saphyra.selenium.test.characterselect.createcharacter;

import static org.github.saphyra.selenium.logic.util.Util.crop;
import static org.github.saphyra.skyxplore.character.domain.CreateCharacterRequest.CHARACTER_NAME_MIN_LENGTH;

import org.openqa.selenium.WebElement;

import lombok.Builder;
import org.github.saphyra.selenium.logic.domain.MessageCodes;
import org.github.saphyra.selenium.logic.domain.SeleniumCharacter;
import org.github.saphyra.selenium.logic.page.CharacterSelectPage;
import org.github.saphyra.selenium.logic.validator.FieldValidator;
import org.github.saphyra.selenium.test.characterselect.common.CharacterSelectTestHelper;

@Builder
public class TooShortCharacterNameTest {
    private static final String MESSAGE_CODE_CHARACTER_NAME_TOO_SHORT = "CHARACTER_NAME_TOO_SHORT";

    private final CharacterSelectTestHelper characterSelectTestHelper;
    private final CharacterSelectPage characterSelectPage;
    private final FieldValidator fieldValidator;
    private final MessageCodes messageCodes;

    public void testTooShortCharacterName() {
        characterSelectTestHelper.registerUser();

        WebElement newCharacterNameField = characterSelectPage.getNewCharacterNameField();
        newCharacterNameField.sendKeys(crop(SeleniumCharacter.createRandomCharacterName(), CHARACTER_NAME_MIN_LENGTH - 1));

        fieldValidator.verifyError(
            characterSelectPage.getInvalidNewCharacterNameField(),
            messageCodes.get(MESSAGE_CODE_CHARACTER_NAME_TOO_SHORT),
            newCharacterNameField,
            characterSelectPage.getCreateCharacterButton()
        );
    }
}
