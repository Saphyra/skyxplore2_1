package selenium.cases.characterselect.testcase;

import lombok.Builder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.domain.SeleniumCharacter;
import selenium.flow.CreateCharacter;
import selenium.page.CharacterSelectPage;
import selenium.validator.FieldValidator;
import selenium.validator.NotificationValidator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static selenium.util.StringUtil.crop;
import static selenium.util.ValidationUtil.validateIfPresent;
import static skyxplore.controller.request.character.CreateCharacterRequest.CHARACTER_NAME_MAX_LENGTH;
import static skyxplore.controller.request.character.CreateCharacterRequest.CHARACTER_NAME_MIN_LENGTH;

@Builder
public class RenameCharacterTest {
    private static final String TOO_LONG_CHARACTER_NAME;

    static {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i <= CHARACTER_NAME_MAX_LENGTH + 1; i++) {
            builder.append("a");
        }
        TOO_LONG_CHARACTER_NAME = builder.toString();
    }

    private static final String ERROR_MESSAGE_CHARACTER_NAME_TOO_SHORT = "Karakternév túl rövid. (Minimum 3 karakter)";
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

    public void testTooShortCharacterName() {
        clear();
        WebElement renameCharacterField = characterSelectPage.getRenameCharacterNameField();
        renameCharacterField.sendKeys(crop(SeleniumCharacter.createRandomCharacterName(), CHARACTER_NAME_MIN_LENGTH - 1));

        fieldValidator.verifyError(
            characterSelectPage.getInvalidRenameCharacterNameField(),
            ERROR_MESSAGE_CHARACTER_NAME_TOO_SHORT,
            renameCharacterField,
            characterSelectPage.getRenameCharacterButton()
        );
    }

    public void testTooLongCharacterName() {
        clear();
        WebElement renameCharacterField = characterSelectPage.getRenameCharacterNameField();
        renameCharacterField.sendKeys(TOO_LONG_CHARACTER_NAME);

        fieldValidator.verifyError(
            characterSelectPage.getInvalidRenameCharacterNameField(),
            ERROR_MESSAGE_CHARACTER_NAME_TOO_LONG,
            renameCharacterField,
            characterSelectPage.getRenameCharacterButton()
        );
    }

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
                .map(element -> element.findElement(By.cssSelector("td:first-child")))
                .anyMatch(webElement -> webElement.getText().equals(renamedCharacter.getCharacterName()))
        );

        assertFalse(characterSelectPage.getCharacterList().stream()
            .map(element -> element.findElement(By.cssSelector("td:first-child")))
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
                .filter(element -> element.findElement(By.cssSelector("td:first-child"))
                    .getText()
                    .equals(testCharacter.getCharacterName())
                )
                .map(element -> element.findElement(By.cssSelector("td:nth-child(2)")))
                .map(element -> element.findElement(By.cssSelector("button:first-child")))
                .findFirst()
        ).ifPresent(WebElement::click);

        assertTrue(characterSelectPage.getRenameCharacterWindow().isDisplayed());
    }
}
