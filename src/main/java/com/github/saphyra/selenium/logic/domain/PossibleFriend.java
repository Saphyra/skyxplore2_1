package com.github.saphyra.selenium.logic.domain;

import com.github.saphyra.selenium.logic.domain.localization.MessageCodes;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.github.saphyra.selenium.logic.validator.NotificationValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PossibleFriend {
    private static final String SELECTOR_CHARACTER_NAME = "div:first-child";
    private static final String SELECTOR_ADD_FRIEND_BUTTON = "button:first-of-type";
    private static final String MESSAGE_CODE_FRIEND_REQUEST_SENT = "FRIEND_REQUEST_SENT";

    private final WebElement element;
    private final MessageCodes messageCodes;

    public String getCharacterName() {
        return element.findElement(By.cssSelector(SELECTOR_CHARACTER_NAME)).getText();
    }

    public void addFriend(NotificationValidator notificationValidator) {
        element.findElement(By.cssSelector(SELECTOR_ADD_FRIEND_BUTTON)).click();
        verifySuccess(notificationValidator);
    }

    private void verifySuccess(NotificationValidator notificationValidator) {
        notificationValidator.verifyNotificationVisibility(messageCodes.get(MESSAGE_CODE_FRIEND_REQUEST_SENT));
    }
}
