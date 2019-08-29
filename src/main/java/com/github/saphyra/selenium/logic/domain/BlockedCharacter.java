package com.github.saphyra.selenium.logic.domain;

import static com.github.saphyra.selenium.logic.util.LocalizationUtil.getAdditionalContent;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.saphyra.selenium.logic.domain.localization.Page;
import com.github.saphyra.selenium.logic.validator.NotificationValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BlockedCharacter {
    private static final String SELECTOR_CHARACTER_NAME = "div:first-child";
    private static final String SELECTOR_BLOCK_BUTTON = ".friend-list-item-button";
    private static final String MESSAGE_CODE_CHARACTER_ALLOWED = "character-allowed";

    private final WebDriver driver;
    private final WebElement element;

    public String getCharacterName() {
        return element.findElement(By.cssSelector(SELECTOR_CHARACTER_NAME)).getText();
    }

    public void unblock(NotificationValidator notificationValidator) {
        element.findElement(By.cssSelector(SELECTOR_BLOCK_BUTTON)).click();
        notificationValidator.verifyNotificationVisibility(getAdditionalContent(driver, Page.COMMUNITY, MESSAGE_CODE_CHARACTER_ALLOWED));
    }
}
