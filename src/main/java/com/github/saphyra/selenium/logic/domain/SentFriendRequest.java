package com.github.saphyra.selenium.logic.domain;

import static com.github.saphyra.selenium.logic.util.LocalizationUtil.getAdditionalContent;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.saphyra.selenium.logic.domain.localization.Page;
import com.github.saphyra.selenium.logic.validator.NotificationValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SentFriendRequest {
    private static final String SELECTOR_CHARACTER_NAME = "div:first-child";
    private static final String SELECTOR_CANCEL_FRIEND_REQUEST = "button.friend-list-item-button:first-of-type";
    private static final String MESSAGE_CODE_FRIEND_REQUEST_CANCELLED = "friend-request-cancelled";

    private final WebDriver driver;
    private final WebElement element;

    public String getCharacterName() {
        return element.findElement(By.cssSelector(SELECTOR_CHARACTER_NAME)).getText();
    }

    public void cancel() {
        element.findElement(By.cssSelector(SELECTOR_CANCEL_FRIEND_REQUEST)).click();
        new NotificationValidator(driver).verifyNotificationVisibility(getAdditionalContent(driver, Page.COMMUNITY, MESSAGE_CODE_FRIEND_REQUEST_CANCELLED));
    }
}
