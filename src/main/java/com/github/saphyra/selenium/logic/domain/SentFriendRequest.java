package com.github.saphyra.selenium.logic.domain;

import com.github.saphyra.selenium.logic.domain.localization.MessageCodes;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import lombok.RequiredArgsConstructor;
import com.github.saphyra.selenium.logic.validator.NotificationValidator;

@RequiredArgsConstructor
public class SentFriendRequest {
    private static final String SELECTOR_CHARACTER_NAME = "div:first-child";
    private static final String SELECTOR_CANCEL_FRIEND_REQUEST = "button.friend-list-item-button:first-of-type";
    private static final String MESSAGE_CODE_FRIEND_REQUEST_CANCELLED = "FRIEND_REQUEST_CANCELLED";

    private final WebDriver driver;
    private final WebElement element;
    private final MessageCodes messageCodes;

    public String getCharacterName() {
        return element.findElement(By.cssSelector(SELECTOR_CHARACTER_NAME)).getText();
    }

    public void cancel() {
        element.findElement(By.cssSelector(SELECTOR_CANCEL_FRIEND_REQUEST)).click();
        new NotificationValidator(driver).verifyNotificationVisibility(messageCodes.get(MESSAGE_CODE_FRIEND_REQUEST_CANCELLED));
    }
}
