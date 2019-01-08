package selenium.aaold.cases.account.testcase;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static selenium.aanew.logic.domain.SeleniumUser.createRandomPassword;
import static selenium.aanew.logic.domain.SeleniumUser.createRandomUserName;
import static selenium.aanew.logic.util.Util.ATTRIBUTE_VALUE;
import static selenium.aanew.logic.util.Util.cleanNotifications;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import lombok.Builder;
import selenium.aanew.logic.domain.SeleniumUser;
import selenium.aanew.logic.flow.Login;
import selenium.aanew.logic.flow.Logout;
import selenium.aanew.logic.flow.Navigate;
import selenium.aanew.logic.page.AccountPage;
import selenium.aanew.logic.validator.FieldValidator;
import selenium.aanew.logic.validator.NotificationValidator;

@Builder
public class ChangeUserNameTest {
    private static final String NOTIFICATION_BAD_PASSWORD = "Hibás jelszó.";
    private static final String NOTIFICATION_SUCCESSFUL_USER_NAME_CHANGE = "Felhasználónév megváltoztatása sikeres.";

    private final WebDriver driver;
    private final Navigate navigate;
    private final Login login;
    private final Logout logout;
    private final AccountPage accountPage;
    private final SeleniumUser user;
    private final SeleniumUser originalUser;
    private final SeleniumUser otherUser;
    private final FieldValidator fieldValidator;
    private final NotificationValidator notificationValidator;

    public void validateBadPassword() {
        clearAll();

        WebElement userNameField = accountPage.getChangeUserNameField();
        userNameField.sendKeys(createRandomUserName());

        WebElement changePasswordField = accountPage.getChangeUserNamePasswordField();
        changePasswordField.sendKeys(createRandomPassword());

        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.invisibilityOf(accountPage.getInvalidChangeUserNameField()));
        webDriverWait.until(ExpectedConditions.invisibilityOf(accountPage.getInvalidChangeUserNamePasswordField()));

        assertFalse(accountPage.getInvalidChangeUserNameField().isDisplayed());
        assertFalse(accountPage.getInvalidChangeUserNamePasswordField().isDisplayed());

        WebElement submitButton = accountPage.getChangeUserNameButton();
        assertTrue(submitButton.isEnabled());
        submitButton.click();

        notificationValidator.verifyOnlyOneNotification(NOTIFICATION_BAD_PASSWORD);
        assertTrue(accountPage.getChangeUserNamePasswordField().getAttribute(ATTRIBUTE_VALUE).isEmpty());
    }

    private void setUpForUserNameTest() {
        clearAll();

        WebElement passwordField = accountPage.getChangeUserNamePasswordField();
        passwordField.sendKeys(user.getPassword());
    }

    public void validateHappyPath() {
        clearAll();
        
        WebElement userNameField = accountPage.getChangeUserNameField();
        userNameField.clear();
        user.setUserName(createRandomUserName());
        userNameField.sendKeys(user.getUserName());

        WebElement passwordField = accountPage.getChangeUserNamePasswordField();
        passwordField.clear();
        passwordField.sendKeys(user.getPassword());

        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.invisibilityOf(accountPage.getInvalidChangeUserNameField()));
        webDriverWait.until(ExpectedConditions.invisibilityOf(accountPage.getInvalidChangeUserNamePasswordField()));

        WebElement submitButton = accountPage.getChangeUserNameButton();
        assertTrue(submitButton.isEnabled());
        submitButton.click();

        verifyUserNameChange();
    }

    private void clearAll() {
        accountPage.getChangeUserNameField().clear();
        accountPage.getChangeUserNamePasswordField().clear();

        cleanNotifications(driver);
    }

    private void verifyUserNameChange() {
        notificationValidator.verifyOnlyOneNotification(NOTIFICATION_SUCCESSFUL_USER_NAME_CHANGE);

        logout.logOut();
        login.loginFailure(originalUser);
        login.login(user);

        navigate.toAccountPage();

        accountPage.getChangeUserNameField().sendKeys(originalUser.getUserName());
        accountPage.getChangeUserNamePasswordField().sendKeys(originalUser.getPassword());

        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.invisibilityOf(accountPage.getInvalidChangeUserNameField()));
        webDriverWait.until(ExpectedConditions.invisibilityOf(accountPage.getInvalidChangeUserNamePasswordField()));

        assertFalse(accountPage.getInvalidChangeUserNameField().isDisplayed());
        assertFalse(accountPage.getInvalidChangeUserNamePasswordField().isDisplayed());

        accountPage.getChangeUserNameButton().click();

        notificationValidator.verifyOnlyOneNotification(NOTIFICATION_SUCCESSFUL_USER_NAME_CHANGE);
    }
}
