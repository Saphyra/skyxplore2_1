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

@Builder
public class ExistingCharacterNameTest {
    private static final String MESSAGE_CODE_CHARACTER_NAME_ALREADY_EXISTS = "charactername-already-exists";

    private final WebDriver driver;
    private final RenameCharacterTestHelper renameCharacterTestHelper;
    private final CharacterSelectPage characterSelectPage;
    private final FieldValidator fieldValidator;

    public void testExistingCharacterName() {
        SeleniumCharacter otherCharacter = renameCharacterTestHelper.registerAndCreateCharacter();
        SeleniumCharacter character = renameCharacterTestHelper.createCharacter();

        renameCharacterTestHelper.openRenameCharacterWindow(character);

        WebElement renameCharacterField = characterSelectPage.getRenameCharacterNameField();
        renameCharacterField.clear();
        renameCharacterField.sendKeys(otherCharacter.getCharacterName());

        fieldValidator.verifyError(
            characterSelectPage.getInvalidRenameCharacterNameField(),
            getAdditionalContent(driver, Page.CHARACTER_SELECT, MESSAGE_CODE_CHARACTER_NAME_ALREADY_EXISTS),
            renameCharacterField,
            characterSelectPage.getRenameCharacterButton()
        );
    }
}
