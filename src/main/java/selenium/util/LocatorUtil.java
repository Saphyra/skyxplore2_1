package selenium.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static selenium.util.IdCollection.NOTIFICATION_CONTROLLER_ID;

public class LocatorUtil {
    public static final String LOGOUT_BUTTON_SELECTOR = "footer button:first-child";

    public static By getNotificationElementsLocator() {
        StringBuilder builder = new StringBuilder();
        builder.append("#")
            .append(NOTIFICATION_CONTROLLER_ID)
            .append(" > DIV");
        return By.cssSelector(builder.toString());
    }

    public static WebElement getLogoutButton(WebDriver driver){
        return  driver.findElement(By.cssSelector(LOGOUT_BUTTON_SELECTOR));
    }
}
