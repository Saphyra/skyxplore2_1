package selenium.aanew.logic.util;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Optional;

public class LocatorUtil {
    private static final String LOGOUT_BUTTON_SELECTOR = "footer button:first-child";
    private static final String SELECTOR_NOTIFICATIONS = "#notificationcontainer > DIV";

    public static By getNotificationElementsLocator() {
        return By.cssSelector(SELECTOR_NOTIFICATIONS);
    }

    public static Optional<WebElement> getLogoutButton(WebDriver driver) {
        try {
            return Optional.of(driver.findElement(By.cssSelector(LOGOUT_BUTTON_SELECTOR)));
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }
}
