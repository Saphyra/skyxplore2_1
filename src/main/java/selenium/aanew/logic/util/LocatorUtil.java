package selenium.aanew.logic.util;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Optional;

import static selenium.aanew.logic.util.IdCollection.NOTIFICATION_CONTROLLER_ID;

public class LocatorUtil {
    public static final String LOGOUT_BUTTON_SELECTOR = "footer button:first-child";

    public static By getNotificationElementsLocator() {
        StringBuilder builder = new StringBuilder();
        builder.append("#")
            .append(NOTIFICATION_CONTROLLER_ID)
            .append(" > DIV");
        return By.cssSelector(builder.toString());
    }

    public static Optional<WebElement> getLogoutButton(WebDriver driver) {
        try {
            return Optional.of(driver.findElement(By.cssSelector(LOGOUT_BUTTON_SELECTOR)));
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }
}
