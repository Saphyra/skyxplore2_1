package selenium.aaold.cases.account.testcase;

import lombok.Builder;
import org.openqa.selenium.WebDriver;
import selenium.aanew.logic.domain.SeleniumUser;
import selenium.aanew.logic.flow.Login;
import selenium.aanew.logic.flow.Logout;
import selenium.aanew.logic.flow.Navigate;
import selenium.aanew.logic.page.AccountPage;
import selenium.aanew.logic.validator.FieldValidator;
import selenium.aanew.logic.validator.NotificationValidator;

@Builder
public class ChangePasswordTest {
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
/*

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

    private void setUpForCurrentPasswordTest() {
        clearAll();
        String password = SeleniumUser.createRandomPassword();

        accountPage.getNewPasswordField().sendKeys(password);
        accountPage.getNewConfirmPasswordField().sendKeys(password);
    }

*/
}
