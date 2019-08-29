package com.github.saphyra.selenium.logic.flow;

import static com.github.saphyra.selenium.logic.util.LinkUtil.CHARACTER_SELECT;
import static com.github.saphyra.selenium.logic.util.LinkUtil.INDEX_PAGE;
import static com.github.saphyra.selenium.logic.util.LocalizationUtil.getErrorCode;
import static com.github.saphyra.selenium.logic.util.Util.cleanNotifications;
import static org.junit.Assert.assertEquals;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.saphyra.selenium.logic.domain.SeleniumUser;
import com.github.saphyra.selenium.logic.page.IndexPage;
import com.github.saphyra.selenium.logic.validator.NotificationValidator;
import com.github.saphyra.skyxplore.common.ErrorCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Login {
    private final WebDriver driver;
    private final IndexPage indexPage;
    private final NotificationValidator notificationValidator;
    private final Navigate navigate;

    public Login(WebDriver driver) {
        this.driver = driver;
        indexPage = new IndexPage(driver);
        notificationValidator = new NotificationValidator(driver);
        navigate = new Navigate(driver);
    }

    public void login(SeleniumUser user) {
        navigate.toIndexPage();
        sendRequest(user);
        validateSuccessfulLogin();
    }

    private void sendRequest(SeleniumUser user) {
        WebElement userNameField = indexPage.getLoginUserNameField();
        userNameField.clear();
        userNameField.sendKeys(user.getUserName());

        WebElement passwordField = indexPage.getLoginPasswordField();
        passwordField.clear();
        passwordField.sendKeys(user.getPassword());

        WebElement loginButton = indexPage.getLoginButton();
        loginButton.click();
    }

    private void validateSuccessfulLogin() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe(CHARACTER_SELECT));
        assertEquals(CHARACTER_SELECT, driver.getCurrentUrl());
    }

    public void loginFailure(SeleniumUser user) {
        cleanNotifications(driver);
        assertEquals(INDEX_PAGE, driver.getCurrentUrl());
        sendRequest(user);
        validateLoginFailure();
    }

    private void validateLoginFailure() {
        assertEquals(INDEX_PAGE, driver.getCurrentUrl());
        notificationValidator.verifyOnlyOneNotification(getErrorCode(driver, ErrorCode.BAD_CREDENTIALS));
    }
}
