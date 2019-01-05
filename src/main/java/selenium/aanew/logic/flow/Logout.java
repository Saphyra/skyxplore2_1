package selenium.aanew.logic.flow;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.aanew.logic.util.LocatorUtil;
import selenium.aanew.logic.validator.NotificationValidator;

import static org.junit.Assert.assertEquals;
import static selenium.aanew.logic.util.LinkUtil.HOST;
import static selenium.aanew.logic.util.LocatorUtil.getNotificationElementsLocator;

public class Logout {
    private static final String SUCCESSFUL_LOGOUT_NOTIFICATION = "Sikeres kijelentkezés!";

    private final WebDriver driver;
    private final NotificationValidator notificationValidator;

    public Logout(WebDriver driver) {
        this.driver = driver;
        this.notificationValidator = new NotificationValidator(driver);
    }

    public void logOut(){
        LocatorUtil.getLogoutButton(driver).ifPresent(WebElement::click);

        validateLogout();
    }

    private void validateLogout() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getNotificationElementsLocator()));

        assertEquals(HOST, driver.getCurrentUrl());
        notificationValidator.verifyOnlyOneNotification(SUCCESSFUL_LOGOUT_NOTIFICATION);
    }
}
