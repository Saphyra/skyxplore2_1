package selenium.aanew.test.characterselect.renamecharacter.helper;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.aanew.logic.domain.SeleniumCharacter;
import selenium.aanew.logic.flow.CreateCharacter;
import selenium.aanew.logic.flow.Registration;
import selenium.aanew.logic.page.CharacterSelectPage;
import selenium.aanew.logic.validator.FieldValidator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RequiredArgsConstructor
public class RenameCharacterTestHelper {
    private static final String SELECTOR_CHARACTER_MODIFICATION_MENU = "td:nth-child(2)";
    private static final String SELECTOR_RENAME_CHARACTER_BUTTON = "button:first-child";

    private final WebDriver driver;
    private final Registration registration;
    private final CreateCharacter createCharacter;
    private final CharacterSelectPage characterSelectPage;
    private final FieldValidator fieldValidator;

    public SeleniumCharacter initAndOpenRenamePage() {
        SeleniumCharacter character = registerAndCreateCharacter();
        openRenameCharacterWindow(character);
        return character;
    }

    public SeleniumCharacter registerAndCreateCharacter() {
        registration.registerUser();
        return createCharacter.createCharacter();
    }

    public void openRenameCharacterWindow(SeleniumCharacter testCharacter) {
        characterSelectPage.getCharacterRow(testCharacter.getCharacterName())
            .map(element -> element.findElement(By.cssSelector(SELECTOR_CHARACTER_MODIFICATION_MENU)))
            .map(element -> element.findElement(By.cssSelector(SELECTOR_RENAME_CHARACTER_BUTTON)))
            .orElseThrow(() -> new RuntimeException("Character not found"))
            .click();

        assertTrue(characterSelectPage.getRenameCharacterWindow().isDisplayed());
    }

    public SeleniumCharacter createCharacter() {
        return createCharacter.createCharacter();
    }

    public void sendForm() {
        new WebDriverWait(driver, 10)
            .until(ExpectedConditions.invisibilityOf(characterSelectPage.getInvalidRenameCharacterNameField()));

        fieldValidator.verifySuccess(
            characterSelectPage.getInvalidRenameCharacterNameField(),
            characterSelectPage.getRenameCharacterButton()
        );

        characterSelectPage.getRenameCharacterButton().click();

        new WebDriverWait(driver, 10)
            .until(ExpectedConditions.invisibilityOf(characterSelectPage.getRenameCharacterWindow()));
        assertFalse(characterSelectPage.getRenameCharacterWindow().isDisplayed());
    }
}
