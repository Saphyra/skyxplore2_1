package com.github.saphyra.selenium.logic.flow;

import com.github.saphyra.selenium.logic.domain.localization.Page;
import com.github.saphyra.selenium.logic.util.LocatorUtil;
import com.github.saphyra.selenium.logic.validator.NotificationValidator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.github.saphyra.selenium.logic.util.LinkUtil.INDEX_PAGE;
import static com.github.saphyra.selenium.logic.util.LocalizationUtil.getAdditionalContent;

public class Logout {
    private static final String MESSAGE_CODE_SUCCESSFUL_LOGOUT = "successful-logout";

    private final WebDriver driver;
    private final NotificationValidator notificationValidator;

    public Logout(WebDriver driver) {
        this.driver = driver;
        this.notificationValidator = new NotificationValidator(driver);
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

        notificationValidator.verifyOnlyOneNotification(getAdditionalContent(driver, Page.INDEX, MESSAGE_CODE_SUCCESSFUL_LOGOUT));
    }
}
