package com.github.saphyra.selenium.logic.domain;

import static com.github.saphyra.selenium.logic.util.LocalizationUtil.getAdditionalContent;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.saphyra.selenium.logic.domain.localization.Page;
import com.github.saphyra.selenium.logic.validator.NotificationValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PossibleFriend {
    private static final String SELECTOR_CHARACTER_NAME = "div:first-child";
    private static final String SELECTOR_ADD_FRIEND_BUTTON = "button:first-of-type";
    private static final String MESSAGE_CODE_FRIEND_REQUEST_SENT = "friend-request-sent";

    private final WebDriver driver;
    private final WebElement element;

    public String getCharacterName() {
        return element.findElement(By.cssSelector(SELECTOR_CHARACTER_NAME)).getText();
    }

    public void addFriend(NotificationValidator notificationValidator) {
        element.findElement(By.cssSelector(SELECTOR_ADD_FRIEND_BUTTON)).click();
        verifySuccess(notificationValidator);
    }

    private void verifySuccess(NotificationValidator notificationValidator) {
        notificationValidator.verifyNotificationVisibility(getAdditionalContent(driver, Page.COMMUNITY, MESSAGE_CODE_FRIEND_REQUEST_SENT));
    }
}
