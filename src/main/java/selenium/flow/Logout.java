package selenium.flow;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.util.LocatorUtil;
import selenium.validator.NotificationValidator;

import static org.junit.Assert.assertEquals;
import static selenium.util.LinkUtil.HOST;
import static selenium.util.LocatorUtil.getNotificationElementsLocator;

public class Logout {
    public static final String SUCCESSFUL_LOGOUT_NOTIFICATION = "Sikeres kijelentkezés!";

    private final WebDriver driver;
    private final NotificationValidator notificationValidator;

    public Logout(WebDriver driver) {
        this.driver = driver;
        this.notificationValidator = new NotificationValidator(driver);
    }

    public void logOut(){
        WebElement logoutButton = LocatorUtil.getLogoutButton(driver);
        logoutButton.click();

        validateLogout();
    }

    private void validateLogout() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getNotificationElementsLocator()));

        assertEquals(HOST, driver.getCurrentUrl());
        notificationValidator.verifyOnlyOneNotification(SUCCESSFUL_LOGOUT_NOTIFICATION);
    }
}
