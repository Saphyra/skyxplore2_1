package com.github.saphyra.selenium.test.characterselect.createcharacter;

import com.github.saphyra.selenium.logic.domain.localization.Page;
import com.github.saphyra.selenium.logic.page.CharacterSelectPage;
import com.github.saphyra.selenium.logic.validator.FieldValidator;
import com.github.saphyra.selenium.test.characterselect.common.CharacterSelectTestHelper;
import lombok.Builder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.github.saphyra.selenium.logic.util.LocalizationUtil.getAdditionalContent;
import static com.github.saphyra.skyxplore.userdata.character.domain.CreateCharacterRequest.CHARACTER_NAME_MAX_LENGTH;

@Builder
public class TooLongCharacterNameTest {
    private static final String TOO_LONG_CHARACTER_NAME;
    private static final String MESSAGE_CODE_CHARACTER_NAME_TOO_LONG = "character-name-too-long";

    static {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i <= CHARACTER_NAME_MAX_LENGTH + 1; i++) {
            builder.append("a");
        }
        TOO_LONG_CHARACTER_NAME = builder.toString();
    }

    private final WebDriver driver;
    private final CharacterSelectTestHelper characterSelectTestHelper;
    private final CharacterSelectPage characterSelectPage;
    private final FieldValidator fieldValidator;

    public void testTooLongCharacterName() {
        characterSelectTestHelper.registerUser();

        WebElement newCharacterNameField = characterSelectPage.getNewCharacterNameField();
        newCharacterNameField.sendKeys(TOO_LONG_CHARACTER_NAME);

        fieldValidator.verifyError(
            characterSelectPage.getInvalidNewCharacterNameField(),
            getAdditionalContent(driver, Page.CHARACTER_SELECT, MESSAGE_CODE_CHARACTER_NAME_TOO_LONG),
            newCharacterNameField,
            characterSelectPage.getCreateCharacterButton()
        );
    }
}
