package selenium.flow;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import selenium.domain.SeleniumCharacter;
import selenium.page.CharacterSelectPage;
import selenium.validator.FieldValidator;
import selenium.validator.NotificationValidator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static selenium.util.LinkUtil.CHARACTER_SELECT;

public class CreateCharacter {
    private static final String NOTIFICATION_CHARACTER_CREATED = "Karakter létrehozva.";

    private final WebDriver driver;
    private final CharacterSelectPage characterSelectPage;
    private final FieldValidator fieldValidator;
    private final NotificationValidator notificationValidator;

    public CreateCharacter(WebDriver driver){
        this.driver = driver;
        characterSelectPage = new CharacterSelectPage(driver);
        fieldValidator = new FieldValidator(driver, CHARACTER_SELECT);
        notificationValidator = new NotificationValidator(driver);
    }

    public SeleniumCharacter createCharacter(){
        return createCharacter(SeleniumCharacter.create());
    }

    public SeleniumCharacter createCharacter(SeleniumCharacter character) {
        assertEquals(CHARACTER_SELECT, driver.getCurrentUrl());

        WebElement characterNameField = characterSelectPage.getNewCharacterNameField();
        characterNameField.clear();
        characterNameField.sendKeys(character.getCharacterName());

        WebElement createCharacterButton = characterSelectPage.getCreateCharacterButton();
        fieldValidator.verifySuccess(
            characterSelectPage.getInvalidNewCharacterNameField(),
            createCharacterButton
        );

        createCharacterButton.click();

        verifyCharacterCreation(character);

        return character;
    }

    private void verifyCharacterCreation(SeleniumCharacter character) {
        notificationValidator.verifyNotificationVisibility(NOTIFICATION_CHARACTER_CREATED);

        assertTrue(
            characterSelectPage.getCharacterList().stream()
                .map(element -> element.findElement(By.cssSelector("td:first-child")))
                .anyMatch(webElement -> webElement.getText().equals(character.getCharacterName()))
        );
    }
}
