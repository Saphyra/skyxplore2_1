package com.github.saphyra.selenium.logic.flow;

import static com.github.saphyra.selenium.logic.util.LinkUtil.CHARACTER_SELECT;
import static com.github.saphyra.selenium.logic.util.LocalizationUtil.getAdditionalContent;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.saphyra.selenium.logic.domain.SeleniumCharacter;
import com.github.saphyra.selenium.logic.domain.localization.Page;
import com.github.saphyra.selenium.logic.page.CharacterSelectPage;
import com.github.saphyra.selenium.logic.validator.FieldValidator;
import com.github.saphyra.selenium.logic.validator.NotificationValidator;

public class CreateCharacter {
    private static final String MESSAGE_CODE_CHARACTER_CREATED = "character-created";

    private final WebDriver driver;
    private final CharacterSelectPage characterSelectPage;
    private final FieldValidator fieldValidator;
    private final NotificationValidator notificationValidator;

    public CreateCharacter(WebDriver driver) {
        this.driver = driver;
        characterSelectPage = new CharacterSelectPage(driver);
        fieldValidator = new FieldValidator(driver, CHARACTER_SELECT);
        notificationValidator = new NotificationValidator(driver);
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
        notificationValidator.verifyNotificationVisibility(getAdditionalContent(driver, Page.CHARACTER_SELECT, MESSAGE_CODE_CHARACTER_CREATED));

        assertTrue(characterSelectPage.isCharacterExists(character.getCharacterName()));
    }
}
