package selenium.logic.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static selenium.logic.util.LocatorUtil.getNotificationElementsLocator;

import java.util.List;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class NotificationValidator {
    private static final String SELECTOR_NOTIFICATION_TEXT = ":first-child";

    private final WebDriver driver;

    public void verifyOnlyOneNotification(String text) {
        log.info("Verifying only one notification with text {}", text);
        List<WebElement> notifications = getNotifications(ExpectedConditions.numberOfElementsToBe(getNotificationElementsLocator(), 1));

        assertEquals(1, notifications.size());
        verifyContains(notifications, text);
    }

    public void verifyNotificationVisibility(String text) {
        log.info("Verifying notification visibility with text {}", text);
        List<WebElement> notifications = getNotifications(ExpectedConditions.numberOfElementsToBeMoreThan(getNotificationElementsLocator(), 0));
        verifyContains(notifications, text);
    }

    private <T> List<WebElement> getNotifications(Function<WebDriver, T> function) {
        new WebDriverWait(driver, 10)
            .until(function);
        return driver.findElements(getNotificationElementsLocator());
    }

    private void verifyContains(List<WebElement> elements, String text) {
        assertTrue(contains(elements, text));
    }

    private boolean contains(List<WebElement> elements, String text) {
        return elements.stream()
            .anyMatch(w -> w.findElement(By.cssSelector(SELECTOR_NOTIFICATION_TEXT)).getText().equals(text));
    }
}
