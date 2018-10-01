package selenium.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static selenium.util.LocatorUtil.getNotificationElementsLocator;

public class DOMUtil {
    public static final String ATTRIBUTE_VALUE = "value";

    public static void cleanNotifications(WebDriver driver){
        List<WebElement> notifications = driver.findElements(getNotificationElementsLocator());
        notifications.forEach(WebElement::click);
    }
}
