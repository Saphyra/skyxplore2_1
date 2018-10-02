package selenium.validator;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static selenium.util.LocatorUtil.getNotificationElementsLocator;

@RequiredArgsConstructor
public class NotificationValidator {
    private final WebDriver driver;

    public void verifyOnlyOneNotification(String text){
        List<WebElement> notifications = getNotifications();
        assertEquals(1, notifications.size());
        verifyContains(notifications, text);
    }

    public void verifyNotificationVisibility(String text){
        List<WebElement> notifications = getNotifications();
        verifyContains(notifications, text);
    }

    private List<WebElement> getNotifications() {
        return driver.findElements(getNotificationElementsLocator());
    }

    private void verifyContains(List<WebElement> elements, String text){
        assertTrue(elements.stream().anyMatch(w -> w.findElement(By.cssSelector(":first-child")).getText().equals(text)));
    }
}
