package org.github.saphyra.selenium.logic.flow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.github.saphyra.selenium.logic.util.LinkUtil.CHARACTER_SELECT;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.github.saphyra.selenium.logic.domain.localization.MessageCodes;
import org.github.saphyra.selenium.logic.domain.SeleniumCharacter;
import org.github.saphyra.selenium.logic.page.CharacterSelectPage;
import org.github.saphyra.selenium.logic.validator.FieldValidator;
import org.github.saphyra.selenium.logic.validator.NotificationValidator;

public class CreateCharacter {
    private static final String MESSAGE_CODE_CHARACTER_CREATED = "CHARACTER_CREATED";

    private final WebDriver driver;
    private final CharacterSelectPage characterSelectPage;
    private final FieldValidator fieldValidator;
    private final NotificationValidator notificationValidator;
    private final MessageCodes messageCodes;

    public CreateCharacter(WebDriver driver, MessageCodes messageCodes) {
        this.driver = driver;
        characterSelectPage = new CharacterSelectPage(driver);
        fieldValidator = new FieldValidator(driver, CHARACTER_SELECT);
        notificationValidator = new NotificationValidator(driver);
        this.messageCodes = messageCodes;
    }

    public SeleniumCharacter createCharacter() {
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
        notificationValidator.verifyNotificationVisibility(messageCodes.get(MESSAGE_CODE_CHARACTER_CREATED));

        assertTrue(characterSelectPage.isCharacterExists(character.getCharacterName()));
    }
}
