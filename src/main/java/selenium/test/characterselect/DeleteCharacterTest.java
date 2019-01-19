package selenium.test.characterselect;

import org.junit.Test;
import org.openqa.selenium.By;
import selenium.SeleniumTestApplication;
import selenium.logic.domain.SeleniumCharacter;
import selenium.logic.flow.CreateCharacter;
import selenium.logic.flow.Registration;
import selenium.logic.page.CharacterSelectPage;
import selenium.logic.validator.NotificationValidator;

import static org.junit.Assert.assertFalse;

public class DeleteCharacterTest extends SeleniumTestApplication {
    private static final String SELECTOR_CHARACTER_MODIFICATION_MENU = "td:nth-child(2)";
    private static final String SELECTOR_DELETE_CHARACTER_BUTTON = "button:nth-child(2)";
    private static final String NOTIFICATION_CHARACTER_DELETED = "Karakter törölve.";

    private Registration registration;
    private CreateCharacter createCharacter;
    private CharacterSelectPage characterSelectPage;
    private NotificationValidator notificationValidator;

    @Override
    protected void init() {
        registration = new Registration(driver);
        createCharacter = new CreateCharacter(driver);
        characterSelectPage = new CharacterSelectPage(driver);
        notificationValidator = new NotificationValidator(driver);
    }

    @Test
    public void testDeleteCharacter() {
        registration.registerUser();
        SeleniumCharacter character = createCharacter.createCharacter();

        clickDeleteButton(character);
        driver.switchTo().alert().accept();

        notificationValidator.verifyNotificationVisibility(NOTIFICATION_CHARACTER_DELETED);

        assertFalse(characterSelectPage.isCharacterExists(character.getCharacterName()));
    }

    private void clickDeleteButton(SeleniumCharacter character) {
        characterSelectPage.getCharacterRow(character.getCharacterName())
            .map(element -> element.findElement(By.cssSelector(SELECTOR_CHARACTER_MODIFICATION_MENU)))
            .map(element -> element.findElement(By.cssSelector(SELECTOR_DELETE_CHARACTER_BUTTON)))
            .orElseThrow(() -> new RuntimeException("Character not found."))
            .click();
    }
}
