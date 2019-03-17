package selenium.logic.validator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static selenium.logic.util.LocatorUtil.getNotificationElementsLocator;
import static selenium.logic.util.WaitUtil.getWithWait;
import static selenium.logic.util.WaitUtil.waitUntil;

@RequiredArgsConstructor
@Slf4j
public class NotificationValidator {
    private static final String SELECTOR_NOTIFICATION_TEXT = ":first-child";

    private final WebDriver driver;

    public void verifyOnlyOneNotification(String text) {
        log.info("Verifying only one notification with text {}", text);
        assertEquals(1, getNotifications().size());
        verifyContains(text);
    }

    public void verifyNotificationVisibility(String text) {
        log.info("Verifying notification visibility with text {}", text);
        verifyContains(text);
    }

    private List<WebElement> getNotifications() {
        return getWithWait(() -> {
            List<WebElement> result = driver.findElements(getNotificationElementsLocator());
            return result.isEmpty() ? Optional.empty() : Optional.of(result);
        }).orElse(Collections.emptyList());
    }

    private void verifyContains(String text) {
        waitUntil(() -> contains(getNotifications(), text), "Waiting until notification with text " + text + " appears...");
        getNotifications().forEach(WebElement::click);
    }

    private boolean contains(List<WebElement> elements, String text) {
        return elements.stream()
            .anyMatch(w -> w.findElement(By.cssSelector(SELECTOR_NOTIFICATION_TEXT)).getText().equals(text));
    }
}
