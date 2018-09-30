package selenium.util;

import org.openqa.selenium.By;

import static selenium.util.IdCollection.NOTIFICATION_CONTROLLER_ID;

public class LocatorUtil {
    public static By getNotificationElementsLocator() {
        StringBuilder builder = new StringBuilder();
        builder.append("#")
            .append(NOTIFICATION_CONTROLLER_ID)
            .append(" DIV");
        return By.cssSelector(builder.toString());
    }
}
