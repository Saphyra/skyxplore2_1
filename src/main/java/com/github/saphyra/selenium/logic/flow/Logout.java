package com.github.saphyra.selenium.logic.flow;

import com.github.saphyra.selenium.logic.util.LocatorUtil;
import com.github.saphyra.selenium.logic.validator.NotificationValidator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Map;

import static com.github.saphyra.selenium.logic.util.LinkUtil.INDEX_PAGE;

public class Logout {
    private static final String MESSAGE_CODE_SUCCESSFUL_LOGOUT = "SUCCESSFUL_LOGOUT";

    private final WebDriver driver;
    private final NotificationValidator notificationValidator;
    private final Map<String, String> messageCodes;

    public Logout(WebDriver driver, Map<String, String> messageCodes) {
        this.driver = driver;
        this.notificationValidator = new NotificationValidator(driver);
        this.messageCodes = messageCodes;
    }

    public void logOut() {
        LocatorUtil.getLogoutButton(driver)
            .orElseThrow(() -> new RuntimeException("Logout button not found."))
            .click();

        validateLogout();
    }

    private void validateLogout() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.urlToBe(INDEX_PAGE));

        notificationValidator.verifyOnlyOneNotification(messageCodes.get(MESSAGE_CODE_SUCCESSFUL_LOGOUT));
    }
}
