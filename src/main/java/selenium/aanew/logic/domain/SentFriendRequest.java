package selenium.aanew.logic.domain;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import selenium.aanew.logic.validator.NotificationValidator;

@RequiredArgsConstructor
public class SentFriendRequest {
    private static final String SELECTOR_CHARACTER_NAME = "div:first-child";
    private static final String SELECTOR_CANCEL_FRIEND_REQUEST = "button.friendlistitembutton:first-of-type";
    private static final String NOTIFICATION_FRIEND_REQUEST_CANCELLED = "Barátkérelem elutasítva.";

    private final WebDriver driver;
    private final WebElement element;

    public String getCharacterName() {
        return element.findElement(By.cssSelector(SELECTOR_CHARACTER_NAME)).getText();
    }

    public void cancel() {
        element.findElement(By.cssSelector(SELECTOR_CANCEL_FRIEND_REQUEST)).click();
        driver.switchTo().alert().accept();
        new NotificationValidator(driver).verifyNotificationVisibility(NOTIFICATION_FRIEND_REQUEST_CANCELLED);
    }
}
