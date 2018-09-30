package selenium.cases.account.testcase;

import lombok.Builder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.domain.SeleniumUser;
import selenium.flow.Login;
import selenium.flow.Logout;
import selenium.flow.Navigate;
import selenium.page.AccountPage;
import selenium.util.FieldValidator;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static selenium.domain.SeleniumUser.createRandomPassword;
import static selenium.domain.SeleniumUser.createRandomUserName;
import static selenium.util.LocatorUtil.getNotificationElementsLocator;
import static selenium.util.StringUtil.crop;
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

    public void validateTooShortUserName() {
        WebElement userNameField = accountPage.getNewUserNameField();

        setUpForUserNameTest();

        userNameField.sendKeys(crop(user.getUserName(), USER_NAME_MIN_LENGTH - 1));

        fieldValidator.verifyError(
            accountPage.getInvalidNewUserNameField(),
            ERROR_MESSAGE_USER_NAME_TOO_SHORT,
            userNameField,
            accountPage.getChangeUserNameButton()
        );
    }

    public void validateTooLongUserName() {
        WebElement userNameField = accountPage.getNewUserNameField();

        setUpForUserNameTest();

        userNameField.sendKeys(TOO_LONG_USER_NAME);

        fieldValidator.verifyError(
            accountPage.getInvalidNewUserNameField(),
            ERROR_MESSAGE_USER_NAME_TOO_LONG,
            userNameField,
            accountPage.getChangeUserNameButton()
        );
    }

    public void validateExistingUserName() {
        WebElement userNameField = accountPage.getNewUserNameField();

        setUpForUserNameTest();

        userNameField.sendKeys(otherUser.getUserName());

        fieldValidator.verifyError(
            accountPage.getInvalidNewUserNameField(),
            ERROR_MESSAGE_USER_NAME_EXISTS,
            userNameField,
            accountPage.getChangeUserNameButton()
        );
    }

    public void validateEmptyPassword() {
        WebElement changePasswordField = accountPage.getNewUserNamePasswordField();
        changePasswordField.clear();

        WebElement userNameField = accountPage.getNewUserNameField();
        userNameField.clear();
        userNameField.sendKeys(createRandomUserName());

        fieldValidator.verifyError(
            accountPage.getInvalidNewUserNamePasswordField(),
            ERROR_MESSAGE_EMPTY_PASSWORD,
            userNameField,
            accountPage.getChangeUserNameButton()
        );
    }

    public void validateBadPassword() {
        WebElement userNameField = accountPage.getNewUserNameField();
        userNameField.clear();
        userNameField.sendKeys(createRandomUserName());

        WebElement changePasswordField = accountPage.getNewUserNamePasswordField();
        changePasswordField.clear();
        changePasswordField.sendKeys(createRandomPassword());

        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.invisibilityOf(accountPage.getInvalidNewUserNameField()));
        webDriverWait.until(ExpectedConditions.invisibilityOf(accountPage.getInvalidNewUserNamePasswordField()));

        assertFalse(accountPage.getInvalidNewUserNameField().isDisplayed());
        assertFalse(accountPage.getInvalidNewUserNamePasswordField().isDisplayed());

        WebElement submitButton = accountPage.getChangeUserNameButton();
        assertTrue(submitButton.isEnabled());
        submitButton.click();

        List<WebElement> notifications = driver.findElements(getNotificationElementsLocator());
        assertTrue(notifications.stream().anyMatch(w -> w.getText().equals(NOTIFICATION_BAD_PASSWORD)));
    }

    private void setUpForUserNameTest() {
        accountPage.getNewUserNameField().clear();

        WebElement passwordField = accountPage.getNewUserNamePasswordField();
        passwordField.clear();
        passwordField.sendKeys(user.getPassword());
    }

    public void validateHappyPath() {
        WebElement userNameField = accountPage.getNewUserNameField();
        userNameField.clear();
        user.setUserName(createRandomUserName());
        userNameField.sendKeys(user.getUserName());

        WebElement passwordField = accountPage.getNewUserNamePasswordField();
        passwordField.clear();
        passwordField.sendKeys(user.getPassword());

        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.invisibilityOf(accountPage.getInvalidNewUserNameField()));
        webDriverWait.until(ExpectedConditions.invisibilityOf(accountPage.getInvalidNewUserNamePasswordField()));

        assertFalse(accountPage.getInvalidNewUserNameField().isDisplayed());
        assertFalse(accountPage.getInvalidNewUserNamePasswordField().isDisplayed());

        WebElement submitButton = accountPage.getChangeUserNameButton();
        assertTrue(submitButton.isEnabled());
        submitButton.click();

        verifyUserNameChange();
    }

    private void verifyUserNameChange() {
        List<WebElement> notifications = driver.findElements(getNotificationElementsLocator());
        assertTrue(notifications.stream().anyMatch(w -> w.getText().equals(NOTIFICATION_SUCCESSFUL_USER_NAME_CHANGE)));

        logout.logOut();
        login.loginFailure(originalUser);
        login.login(user);

        navigate.toAccountPage();
        accountPage.getNewUserNameField().sendKeys(originalUser.getUserName());
        accountPage.getNewUserNamePasswordField().sendKeys(originalUser.getPassword());

        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.invisibilityOf(accountPage.getInvalidNewUserNameField()));
        webDriverWait.until(ExpectedConditions.invisibilityOf(accountPage.getInvalidNewUserNamePasswordField()));

        assertFalse(accountPage.getInvalidNewUserNameField().isDisplayed());
        assertFalse(accountPage.getInvalidNewUserNamePasswordField().isDisplayed());

        accountPage.getChangeUserNameButton().click();

        List<WebElement> newNotifications = driver.findElements(getNotificationElementsLocator());
        assertTrue(newNotifications.stream().anyMatch(w -> w.getText().equals(NOTIFICATION_SUCCESSFUL_USER_NAME_CHANGE)));
    }
}
