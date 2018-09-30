package selenium.flow;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.util.LocatorUtil;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static selenium.util.LinkUtil.HOST;
import static selenium.util.LocatorUtil.getNotificationElementsLocator;

public class Logout {
    public static final String SUCCESSFUL_LOGOUT_NOTIFICATION = "Sikeres kijelentkezés!";

    private final WebDriver driver;

    public Logout(WebDriver driver) {
        this.driver = driver;
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
        List<WebElement> notifications = driver.findElements(getNotificationElementsLocator());
        assertTrue(notifications.stream().anyMatch(w -> w.getText().equals(SUCCESSFUL_LOGOUT_NOTIFICATION)));
    }
}
