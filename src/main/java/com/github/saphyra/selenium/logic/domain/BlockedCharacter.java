package com.github.saphyra.selenium.logic.domain;

import lombok.RequiredArgsConstructor;
import com.github.saphyra.selenium.logic.domain.localization.MessageCodes;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import com.github.saphyra.selenium.logic.validator.NotificationValidator;

@RequiredArgsConstructor
public class BlockedCharacter {
    private static final String SELECTOR_CHARACTER_NAME = "div:first-child";
    private static final String SELECTOR_BLOCK_BUTTON = ".friend-list-item-button";
    private static final String MESSAGE_CODE_CHARACTER_ALLOWED = "CHARACTER_ALLOWED";

    private final WebElement element;
    private final MessageCodes messageCodes;

    public String getCharacterName() {
        return element.findElement(By.cssSelector(SELECTOR_CHARACTER_NAME)).getText();
    }

    public void unblock(NotificationValidator notificationValidator) {
        element.findElement(By.cssSelector(SELECTOR_BLOCK_BUTTON)).click();
        notificationValidator.verifyNotificationVisibility(messageCodes.get(MESSAGE_CODE_CHARACTER_ALLOWED));
    }
}
