package selenium.aaold.cases.characterselect.testcase;

import lombok.Builder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import selenium.aanew.domain.SeleniumCharacter;
import selenium.aanew.flow.CreateCharacter;
import selenium.aanew.page.CharacterSelectPage;
import selenium.aanew.validator.NotificationValidator;

import static org.junit.Assert.*;
import static selenium.aanew.util.LinkUtil.CHARACTER_SELECT;
import static selenium.aanew.util.ValidationUtil.validateIfPresent;

@Builder
public class DeleteCharacterTest {
    private final WebDriver driver;
    private final CharacterSelectPage characterSelectPage;
    private final NotificationValidator notificationValidator;
    private final SeleniumCharacter testCharacter = SeleniumCharacter.create();

    public void testNotDeleteWhenCancel() {
        clickDeleteButton();
        verifyAlert();
        driver.switchTo().alert().dismiss();

        assertTrue(isCharacterExists(testCharacter));
    }

    public void testDelete() {
        clickDeleteButton();
        verifyAlert();
        driver.switchTo().alert().accept();

        assertFalse(isCharacterExists(testCharacter));
        notificationValidator.verifyNotificationVisibility("Karakter törölve.");
    }

    private void clickDeleteButton() {
        validateIfPresent(
            characterSelectPage.getCharacterList().stream()
                .filter(element -> element.findElement(By.cssSelector("td:first-child"))
                    .getText()
                    .equals(testCharacter.getCharacterName())
                )
                .map(element -> element.findElement(By.cssSelector("td:nth-child(2)")))
                .map(element -> element.findElement(By.cssSelector("button:nth-child(2)")))
                .findFirst()
        ).ifPresent(WebElement::click);
    }

    private void verifyAlert() {
        assertNotNull(ExpectedConditions.alertIsPresent().apply(driver));
    }

    private boolean isCharacterExists(SeleniumCharacter character) {
        assertEquals(CHARACTER_SELECT, driver.getCurrentUrl());
        return characterSelectPage.getCharacterList().stream()
            .map(element -> element.findElement(By.cssSelector("td:first-child")))
            .anyMatch(webElement -> webElement.getText().equals(character.getCharacterName()));
    }

    public void setUp() {
        new CreateCharacter(driver).createCharacter(testCharacter);
    }
}
