package org.github.saphyra.selenium.logic.validator;

import static org.github.saphyra.selenium.logic.util.LocatorUtil.getNotificationElementsLocator;
import static org.github.saphyra.selenium.logic.util.WaitUtil.waitUntil;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class NotificationValidator {
    private static final String SELECTOR_NOTIFICATION_TEXT = ":first-child";

    private final WebDriver driver;

    public void verifyOnlyOneNotification(String text) {
        log.info("Verifying only one notification with text {}", text);
        waitUntil(() -> {
            List<WebElement> notifications = getNotifications();
            if (notifications.size() > 1) {
                throw new RuntimeException("More than 1 notifications are present.");
            }
            return notifications.size() == 1;
        }, "Waiting until only one notification is present.");
        verifyContains(text);
    }

    public void verifyNotificationVisibility(String text) {
        log.info("Verifying notification visibility with text {}", text);
        verifyContains(text);
    }

    private List<WebElement> getNotifications() {
        return driver.findElements(getNotificationElementsLocator());
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
