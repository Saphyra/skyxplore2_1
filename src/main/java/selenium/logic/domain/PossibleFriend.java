package selenium.logic.domain;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import selenium.logic.validator.NotificationValidator;

@RequiredArgsConstructor
public class PossibleFriend {
    private static final String SELECTOR_CHARACTER_NAME = "div:first-child";
    private static final String SELECTOR_ADD_FRIEND_BUTTON = "button:first-of-type";
    private static final String NOTIFICATION_FRIEND_REQUEST_SENT = "Barátkérelem elküldve.";

    private final WebElement element;

    public String getCharacterName() {
        return element.findElement(By.cssSelector(SELECTOR_CHARACTER_NAME)).getText();
    }

    public void addFriend(NotificationValidator notificationValidator) {
        element.findElement(By.cssSelector(SELECTOR_ADD_FRIEND_BUTTON)).click();
        verifySuccess(notificationValidator);
    }

    private void verifySuccess(NotificationValidator notificationValidator) {
        notificationValidator.verifyNotificationVisibility(NOTIFICATION_FRIEND_REQUEST_SENT);
    }
}
