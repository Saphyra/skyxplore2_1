package com.github.saphyra.selenium.test.characterselect.renamecharacter;

import com.github.saphyra.selenium.logic.domain.SeleniumCharacter;
import com.github.saphyra.selenium.logic.domain.localization.Page;
import com.github.saphyra.selenium.logic.page.CharacterSelectPage;
import com.github.saphyra.selenium.logic.validator.FieldValidator;
import com.github.saphyra.selenium.test.characterselect.renamecharacter.helper.RenameCharacterTestHelper;
import lombok.Builder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.github.saphyra.selenium.logic.util.LocalizationUtil.getAdditionalContent;
import static com.github.saphyra.selenium.logic.util.Util.crop;
import static com.github.saphyra.skyxplore.userdata.character.domain.CreateCharacterRequest.CHARACTER_NAME_MIN_LENGTH;

@Builder
public class TooShortCharacterNameTest {
    private static final String MESSAGE_CODE_CHARACTER_NAME_TOO_SHORT = "character-name-too-short";

    private final WebDriver driver;
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
            getAdditionalContent(driver, Page.CHARACTER_SELECT, MESSAGE_CODE_CHARACTER_NAME_TOO_SHORT),
            renameCharacterField,
            characterSelectPage.getRenameCharacterButton()
        );
    }
}
