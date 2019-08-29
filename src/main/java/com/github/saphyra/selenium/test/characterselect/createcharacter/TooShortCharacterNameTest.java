package com.github.saphyra.selenium.test.characterselect.createcharacter;

import com.github.saphyra.selenium.logic.domain.SeleniumCharacter;
import com.github.saphyra.selenium.logic.domain.localization.Page;
import com.github.saphyra.selenium.logic.page.CharacterSelectPage;
import com.github.saphyra.selenium.logic.validator.FieldValidator;
import com.github.saphyra.selenium.test.characterselect.common.CharacterSelectTestHelper;
import lombok.Builder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.github.saphyra.selenium.logic.util.LocalizationUtil.getAdditionalContent;
import static com.github.saphyra.selenium.logic.util.Util.crop;
import static com.github.saphyra.skyxplore.userdata.character.domain.CreateCharacterRequest.CHARACTER_NAME_MIN_LENGTH;

@Builder
public class TooShortCharacterNameTest {
    private static final String MESSAGE_CODE_CHARACTER_NAME_TOO_SHORT = "charactername-too-short";

    private final WebDriver driver;
    private final CharacterSelectTestHelper characterSelectTestHelper;
    private final CharacterSelectPage characterSelectPage;
    private final FieldValidator fieldValidator;

    public void testTooShortCharacterName() {
        characterSelectTestHelper.registerUser();

        WebElement newCharacterNameField = characterSelectPage.getNewCharacterNameField();
        newCharacterNameField.sendKeys(crop(SeleniumCharacter.createRandomCharacterName(), CHARACTER_NAME_MIN_LENGTH - 1));

        fieldValidator.verifyError(
            characterSelectPage.getInvalidNewCharacterNameField(),
            getAdditionalContent(driver, Page.CHARACTER_SELECT, MESSAGE_CODE_CHARACTER_NAME_TOO_SHORT),
            newCharacterNameField,
            characterSelectPage.getCreateCharacterButton()
        );
    }
}
