package selenium.aaold.cases.characterselect.testcase;

import lombok.Builder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.aanew.logic.domain.SeleniumCharacter;
import selenium.aanew.logic.flow.CreateCharacter;
import selenium.aanew.logic.page.CharacterSelectPage;
import selenium.aanew.logic.validator.FieldValidator;
import selenium.aanew.logic.validator.NotificationValidator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static selenium.aanew.logic.util.Util.validateIfPresent;

@Builder
public class RenameCharacterTest {

    private static final String SELECTOR_CHARACTER_NAME = "td:first-child";
    private static final String SELECTOR_CHARACTER_MODIFICATION_MENU = "td:nth-child(2)";
    private static final String SELECTOR_RENAME_CHARACTER_BUTTON = "button:first-child";




    private static final String ERROR_MESSAGE_CHARACTER_NAME_TOO_LONG = "Karakternév túl hosszú. (Maximum 30 karakter)";
    private static final String ERROR_MESSAGE_CHARACTER_NAME_ALREADY_EXISTS = "Karakternév foglalt.";

    private static final String NOTIFICATION_CHARACTER_RENAMED = "Karakter átnevezve.";

    private final WebDriver driver;
    private final CharacterSelectPage characterSelectPage;
    private final FieldValidator fieldValidator;
    private final SeleniumCharacter registeredCharacter;
    private final SeleniumCharacter testCharacter = SeleniumCharacter.create();
    private final SeleniumCharacter renamedCharacter = SeleniumCharacter.create();
    private final NotificationValidator notificationValidator;

    public void testExistingCharacterName() {
        clear();
        WebElement renameCharacterField = characterSelectPage.getRenameCharacterNameField();
        renameCharacterField.sendKeys(registeredCharacter.getCharacterName());

        fieldValidator.verifyError(
            characterSelectPage.getInvalidRenameCharacterNameField(),
            ERROR_MESSAGE_CHARACTER_NAME_ALREADY_EXISTS,
            renameCharacterField,
            characterSelectPage.getRenameCharacterButton()
        );
    }

    public void testRename() {
        clear();
        WebElement renameCharacterField = characterSelectPage.getRenameCharacterNameField();
        renameCharacterField.sendKeys(renamedCharacter.getCharacterName());

        fieldValidator.verifySuccess(
            characterSelectPage.getInvalidRenameCharacterNameField(),
            characterSelectPage.getRenameCharacterButton()
        );

        characterSelectPage.getRenameCharacterButton().click();

        verifySuccessfulRename();
    }

    private void verifySuccessfulRename() {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.invisibilityOf(characterSelectPage.getRenameCharacterWindow()));
        assertFalse(characterSelectPage.getRenameCharacterWindow().isDisplayed());

        notificationValidator.verifyNotificationVisibility(NOTIFICATION_CHARACTER_RENAMED);

        assertTrue(
            characterSelectPage.getCharacterList().stream()
                .map(element -> element.findElement(By.cssSelector(SELECTOR_CHARACTER_NAME)))
                .anyMatch(webElement -> webElement.getText().equals(renamedCharacter.getCharacterName()))
        );

        assertFalse(characterSelectPage.getCharacterList().stream()
            .map(element -> element.findElement(By.cssSelector(SELECTOR_CHARACTER_NAME)))
            .anyMatch(webElement -> webElement.getText().equals(testCharacter.getCharacterName()))
        );
    }

    public void clear() {
        characterSelectPage.getRenameCharacterNameField().clear();
    }

    public void init() {
        new CreateCharacter(driver).createCharacter(testCharacter);
        openRenameCharacterWindow(testCharacter);
    }

    private void openRenameCharacterWindow(SeleniumCharacter testCharacter) {
        validateIfPresent(
            characterSelectPage.getCharacterList().stream()
                .filter(element -> element.findElement(By.cssSelector(SELECTOR_CHARACTER_NAME))
                    .getText()
                    .equals(testCharacter.getCharacterName())
                )
                .map(element -> element.findElement(By.cssSelector(SELECTOR_CHARACTER_MODIFICATION_MENU)))
                .map(element -> element.findElement(By.cssSelector(SELECTOR_RENAME_CHARACTER_BUTTON)))
                .findFirst()
        ).ifPresent(WebElement::click);

        assertTrue(characterSelectPage.getRenameCharacterWindow().isDisplayed());
    }
}
