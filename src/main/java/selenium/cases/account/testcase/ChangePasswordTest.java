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
import selenium.validator.FieldValidator;
import selenium.validator.NotificationValidator;

import static org.junit.Assert.assertTrue;
import static selenium.util.DOMUtil.ATTRIBUTE_VALUE;
import static selenium.util.DOMUtil.cleanNotifications;
import static selenium.util.StringUtil.crop;
import static skyxplore.controller.request.user.UserRegistrationRequest.PASSWORD_MAX_LENGTH;
import static skyxplore.controller.request.user.UserRegistrationRequest.PASSWORD_MIN_LENGTH;

@Builder
public class ChangePasswordTest {
    private static final String TOO_LONG_PASSWORD;

    static {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i <= PASSWORD_MAX_LENGTH + 1; i++) {
            builder.append("a");
        }
        TOO_LONG_PASSWORD = builder.toString();
    }

    private static final String ERROR_MESSAGE_PASSWORD_TOO_LONG = "Új jelszó túl hosszú! (Maximum 30 karakter)";
    private static final String ERROR_MESSAGE_BAD_CONFIRM_PASSWORD = "A jelszavak nem egyeznek.";
    private static final String ERROR_MESSAGE_EMPTY_CURRENT_PASSWORD = "Jelszó megadása kötelező!";

    private static final String NOTIFICATION_BAD_PASSWORD = "Hibás jelszó.";
    private static final String NOTIFICATION_SUCCESSFUL_PASSWORD_CHANGE = "Jelszó megváltoztatása sikeres.";

    private static final String ERROR_MESSAGE_PASSWORD_TOO_SHORT = "Új jelszó túl rövid! (Minimum 6 karakter)";

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

    public void validateTooShortPassword() {
        setUpForChangePasswordTest();

        WebElement newPasswordField = accountPage.getNewPasswordField();

        newPasswordField.sendKeys(crop(originalUser.getPassword(), PASSWORD_MIN_LENGTH - 1));

        fieldValidator.verifyError(
            accountPage.getInvalidNewPasswordField(),
            ERROR_MESSAGE_PASSWORD_TOO_SHORT,
            newPasswordField,
            accountPage.getChangePasswordButton(),
            accountPage.getInvalidNewConfirmPasswordField(),
            accountPage.getCurrentInvalidNewPasswordField()
        );
    }

    public void validateTooLongPassword() {
        setUpForChangePasswordTest();

        WebElement newPasswordField = accountPage.getNewPasswordField();
        newPasswordField.sendKeys(TOO_LONG_PASSWORD);

        fieldValidator.verifyError(
            accountPage.getInvalidNewPasswordField(),
            ERROR_MESSAGE_PASSWORD_TOO_LONG,
            newPasswordField,
            accountPage.getChangePasswordButton(),
            accountPage.getInvalidNewConfirmPasswordField(),
            accountPage.getCurrentInvalidNewPasswordField()
        );
    }

    public void validateConfirmPassword() {
        clearAll();
        accountPage.getCurrentNewPasswordField().sendKeys(originalUser.getPassword());

        WebElement newPasswordField = accountPage.getNewPasswordField();
        newPasswordField.sendKeys(SeleniumUser.createRandomPassword());

        WebElement newConfirmPasswordField = accountPage.getNewConfirmPasswordField();
        newConfirmPasswordField.sendKeys(SeleniumUser.createRandomPassword());

        fieldValidator.verifyError(
            accountPage.getInvalidNewPasswordField(),
            ERROR_MESSAGE_BAD_CONFIRM_PASSWORD,
            newPasswordField,
            accountPage.getChangePasswordButton(),
            accountPage.getCurrentInvalidNewPasswordField()
        );

        fieldValidator.verifyError(
            accountPage.getInvalidNewConfirmPasswordField(),
            ERROR_MESSAGE_BAD_CONFIRM_PASSWORD,
            newConfirmPasswordField,
            accountPage.getChangePasswordButton()
        );
    }

    public void validateEmptyCurrentPassword() {
        setUpForCurrentPasswordTest();

        fieldValidator.verifyError(
            accountPage.getCurrentInvalidNewPasswordField(),
            ERROR_MESSAGE_EMPTY_CURRENT_PASSWORD,
            accountPage.getCurrentNewPasswordField(),
            accountPage.getChangePasswordButton(),
            accountPage.getInvalidNewPasswordField(),
            accountPage.getInvalidNewConfirmPasswordField()
        );
    }

    public void validateBadPassword() {
        clearAll();

        String newPassword = SeleniumUser.createRandomPassword();

        WebElement newPasswordField = accountPage.getNewPasswordField();
        newPasswordField.sendKeys(newPassword);

        WebElement confirmPasswordField = accountPage.getNewConfirmPasswordField();
        confirmPasswordField.sendKeys(newPassword);

        WebElement currentPasswordField = accountPage.getCurrentNewPasswordField();
        currentPasswordField.sendKeys(SeleniumUser.createRandomPassword());

        sendForm();

        notificationValidator.verifyOnlyOneNotification(NOTIFICATION_BAD_PASSWORD);

        assertTrue(accountPage.getNewPasswordField().getAttribute(ATTRIBUTE_VALUE).isEmpty());
        assertTrue(accountPage.getNewConfirmPasswordField().getAttribute(ATTRIBUTE_VALUE).isEmpty());
        assertTrue(accountPage.getCurrentNewPasswordField().getAttribute(ATTRIBUTE_VALUE).isEmpty());
    }

    public void validateHappyPath() {
        clearAll();

        String newPassword = SeleniumUser.createRandomPassword();
        user.setPassword(newPassword);

        WebElement newPasswordField = accountPage.getNewPasswordField();
        newPasswordField.sendKeys(newPassword);

        WebElement confirmPasswordField = accountPage.getNewConfirmPasswordField();
        confirmPasswordField.sendKeys(newPassword);

        WebElement currentPasswordField = accountPage.getCurrentNewPasswordField();
        currentPasswordField.sendKeys(originalUser.getPassword());

        sendForm();

        verifyPasswordChange();
    }

    private void verifyPasswordChange() {
        verifySuccessfulNotification();

        assertTrue(accountPage.getNewPasswordField().getAttribute(ATTRIBUTE_VALUE).isEmpty());
        assertTrue(accountPage.getNewConfirmPasswordField().getAttribute(ATTRIBUTE_VALUE).isEmpty());
        assertTrue(accountPage.getCurrentNewPasswordField().getAttribute(ATTRIBUTE_VALUE).isEmpty());

        logout.logOut();
        login.loginFailure(originalUser);
        login.login(user);

        navigate.toAccountPage();

        accountPage.getNewPasswordField().sendKeys(originalUser.getPassword());
        accountPage.getNewConfirmPasswordField().sendKeys(originalUser.getPassword());
        accountPage.getCurrentNewPasswordField().sendKeys(user.getPassword());

        sendForm();

        verifySuccessfulNotification();
    }

    private void verifySuccessfulNotification() {
        notificationValidator.verifyOnlyOneNotification(NOTIFICATION_SUCCESSFUL_PASSWORD_CHANGE);
    }

    private void sendForm() {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.invisibilityOf(accountPage.getInvalidNewPasswordField()));
        webDriverWait.until(ExpectedConditions.invisibilityOf(accountPage.getInvalidNewConfirmPasswordField()));
        webDriverWait.until(ExpectedConditions.invisibilityOf(accountPage.getCurrentInvalidNewPasswordField()));

        WebElement changePasswordButton = accountPage.getChangePasswordButton();
        assertTrue(changePasswordButton.isEnabled());
        changePasswordButton.click();
    }

    private void setUpForChangePasswordTest() {
        clearAll();

        accountPage.getCurrentNewPasswordField().sendKeys(originalUser.getPassword());
    }

    private void setUpForCurrentPasswordTest() {
        clearAll();
        String password = SeleniumUser.createRandomPassword();

        accountPage.getNewPasswordField().sendKeys(password);
        accountPage.getNewConfirmPasswordField().sendKeys(password);
    }

    private void clearAll() {
        cleanNotifications(driver);

        accountPage.getNewPasswordField().clear();
        accountPage.getNewConfirmPasswordField().clear();
        accountPage.getCurrentNewPasswordField().clear();
    }
}
