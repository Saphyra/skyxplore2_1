package selenium.aaold.cases.account.testcase;

import lombok.Builder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.aanew.logic.domain.SeleniumUser;
import selenium.aanew.logic.flow.Navigate;
import selenium.aanew.logic.validator.FieldValidator;
import selenium.aanew.logic.validator.NotificationValidator;
import selenium.aanew.logic.flow.Login;
import selenium.aanew.logic.flow.Logout;
import selenium.page.AccountPage;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static selenium.aanew.logic.domain.SeleniumUser.createRandomPassword;
import static selenium.aanew.logic.domain.SeleniumUser.createRandomUserName;
import static selenium.aanew.logic.util.DOMUtil.ATTRIBUTE_VALUE;
import static selenium.aanew.logic.util.DOMUtil.cleanNotifications;
import static selenium.aanew.logic.util.StringUtil.crop;
import static skyxplore.controller.request.user.UserRegistrationRequest.USER_NAME_MAX_LENGTH;
import static skyxplore.controller.request.user.UserRegistrationRequest.USER_NAME_MIN_LENGTH;

@Builder
public class ChangeUserNameTest {
    private static final String TOO_LONG_USER_NAME;

    static {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i <= USER_NAME_MAX_LENGTH + 1; i++) {
            builder.append("a");
        }
        TOO_LONG_USER_NAME = builder.toString();
    }

    private static final String ERROR_MESSAGE_USER_NAME_TOO_SHORT = "Túl rövid felhasználónév. (Minimum 3 karakter)";
    private static final String ERROR_MESSAGE_USER_NAME_TOO_LONG = "Túl hosszú felhasználónév. (Maximum 30 karakter)";
    private static final String ERROR_MESSAGE_USER_NAME_EXISTS = "Felhasználónév foglalt.";
    private static final String ERROR_MESSAGE_EMPTY_PASSWORD = "Jelszó megadása kötelező!";

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

    public void validateTooShortUserName() {
        WebElement userNameField = accountPage.getChangeUserNameField();

        setUpForUserNameTest();

        userNameField.sendKeys(crop(user.getUserName(), USER_NAME_MIN_LENGTH - 1));

        fieldValidator.verifyError(
            accountPage.getInvalidChangeUserNameField(),
            ERROR_MESSAGE_USER_NAME_TOO_SHORT,
            userNameField,
            accountPage.getChangeUserNameButton()
        );
    }

    public void validateTooLongUserName() {
        WebElement userNameField = accountPage.getChangeUserNameField();

        setUpForUserNameTest();

        userNameField.sendKeys(TOO_LONG_USER_NAME);

        fieldValidator.verifyError(
            accountPage.getInvalidChangeUserNameField(),
            ERROR_MESSAGE_USER_NAME_TOO_LONG,
            userNameField,
            accountPage.getChangeUserNameButton()
        );
    }

    public void validateExistingUserName() {
        WebElement userNameField = accountPage.getChangeUserNameField();

        setUpForUserNameTest();

        userNameField.sendKeys(otherUser.getUserName());

        fieldValidator.verifyError(
            accountPage.getInvalidChangeUserNameField(),
            ERROR_MESSAGE_USER_NAME_EXISTS,
            userNameField,
            accountPage.getChangeUserNameButton()
        );
    }

    public void validateEmptyPassword() {
        WebElement changePasswordField = accountPage.getChangeUserNamePasswordField();
        changePasswordField.clear();

        WebElement userNameField = accountPage.getChangeUserNameField();
        userNameField.clear();
        userNameField.sendKeys(createRandomUserName());

        fieldValidator.verifyError(
            accountPage.getInvalidChangeUserNamePasswordField(),
            ERROR_MESSAGE_EMPTY_PASSWORD,
            userNameField,
            accountPage.getChangeUserNameButton()
        );
    }

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
