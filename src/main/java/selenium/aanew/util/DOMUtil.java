package selenium.aanew.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static selenium.aanew.util.LocatorUtil.getNotificationElementsLocator;

public class DOMUtil {
    public static final String ATTRIBUTE_VALUE = "value";

    public static void cleanNotifications(WebDriver driver){
        driver.findElements(getNotificationElementsLocator()).forEach(WebElement::click);
    }
}
