package selenium.logic.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static selenium.logic.util.LocatorUtil.getNotificationElementsLocator;
import static selenium.logic.util.Util.sleep;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotificationValidator {
    private static final String SELECTOR_NOTIFICATION_TEXT = ":first-child";

    private final WebDriver driver;

    public void verifyOnlyOneNotification(String text) {
        List<WebElement> notifications = getNotifications();

        assertEquals(1, notifications.size());
        verifyContains(notifications, text);
    }

    public void verifyNotificationVisibility(String text) {
        int counter = 0;
        boolean contains;
        do {
            List<WebElement> notifications = getNotifications();
            contains = contains(notifications, text);
            counter++;
            sleep(100);
        } while (!contains && counter < 100);
        assertTrue(contains);
    }

    private List<WebElement> getNotifications() {
        List<WebElement> notifications;
        int counter = 0;
        do {
            notifications = driver.findElements(getNotificationElementsLocator());
            sleep(100);
            counter++;
        } while (notifications.isEmpty() && counter < 100);
        return notifications;
    }

    private void verifyContains(List<WebElement> elements, String text) {
        assertTrue(contains(elements, text));
    }

    private boolean contains(List<WebElement> elements, String text) {
        return elements.stream()
            .anyMatch(w -> w.findElement(By.cssSelector(SELECTOR_NOTIFICATION_TEXT)).getText().equals(text));
    }
}
