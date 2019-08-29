package com.github.saphyra.selenium.test.characterselect;

import com.github.saphyra.selenium.SeleniumTestApplication;
import com.github.saphyra.selenium.logic.domain.SeleniumCharacter;
import com.github.saphyra.selenium.logic.domain.localization.Page;
import com.github.saphyra.selenium.logic.flow.CreateCharacter;
import com.github.saphyra.selenium.logic.flow.Registration;
import com.github.saphyra.selenium.logic.page.CharacterSelectPage;
import com.github.saphyra.selenium.logic.validator.NotificationValidator;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.github.saphyra.selenium.logic.util.LocalizationUtil.getAdditionalContent;
import static org.junit.Assert.assertFalse;

public class DeleteCharacterTest extends SeleniumTestApplication {
    private static final String SELECTOR_CHARACTER_MODIFICATION_MENU = "td:nth-child(2)";
    private static final String SELECTOR_DELETE_CHARACTER_BUTTON = "button:nth-child(2)";
    private static final String MESSAGE_CODE_CHARACTER_DELETED = "character-deleted";

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
        new WebDriverWait(driver, 10).until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();

        notificationValidator.verifyNotificationVisibility(getAdditionalContent(driver, Page.CHARACTER_SELECT, MESSAGE_CODE_CHARACTER_DELETED));

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
