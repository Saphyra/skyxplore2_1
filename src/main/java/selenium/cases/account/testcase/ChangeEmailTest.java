package selenium.cases.account.testcase;

import lombok.Builder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.domain.SeleniumUser;
import selenium.page.AccountPage;
import selenium.validator.FieldValidator;
import selenium.validator.NotificationValidator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static selenium.util.DOMUtil.ATTRIBUTE_VALUE;
import static selenium.util.DOMUtil.cleanNotifications;

@Builder
public class ChangeEmailTest {
    private static final String INVALID_EMAIL = "aa.a";

    //TODO restore after fixing FE validation
    //private static final String INVALID_EMAIL = "aa.a@";
    private static final String ERROR_MESSAGE_INVALID_EMAIL = "Érvénytelen e-mail cím";
    private static final String ERROR_MESSAGE_EMAIL_ALREADY_EXISTS = "E-mail cím már regisztrálva van.";
    private static final String ERROR_MESSAGE_EMPTY_PASSWORD = "Jelszó megadása kötelező!";

    private static final String NOTIFICATION_BAD_PASSWORD = "Hibás jelszó.";
    private static final String NOTIFICATION_SUCCESSFUL_EMAIL_CHANGE = "E-mail cím sikeresen megváltoztatva.";

    private final WebDriver driver;
    private final AccountPage accountPage;
    private final SeleniumUser user;
    private final SeleniumUser originalUser;
    private final SeleniumUser otherUser;
    private final FieldValidator fieldValidator;
    private final NotificationValidator notificationValidator;

    public void validateInvalidEmail() {
        setUpForEmailTest();

        WebElement emailField = accountPage.getChangeEmailField();
        emailField.sendKeys(INVALID_EMAIL);

        fieldValidator.verifyError(
            accountPage.getInvalidChangeEmailField(),
            ERROR_MESSAGE_INVALID_EMAIL,
            emailField,
            accountPage.getChangeEmailButton(),
            accountPage.getInvalidChangeEmailPasswordField()
        );
    }

    public void validateExistingEmail() {
        setUpForEmailTest();

        setUpForEmailTest();

        WebElement emailField = accountPage.getChangeEmailField();
        emailField.sendKeys(otherUser.getEmail());

        fieldValidator.verifyError(
            accountPage.getInvalidChangeEmailField(),
            ERROR_MESSAGE_EMAIL_ALREADY_EXISTS,
            emailField,
            accountPage.getChangeEmailButton(),
            accountPage.getInvalidChangeEmailPasswordField()
        );
    }

    public void validateEmptyPassword() {
        setUpForPasswordTest();

        accountPage.getChangeEmailPasswordField().clear();

        fieldValidator.verifyError(
            accountPage.getInvalidChangeEmailPasswordField(),
            ERROR_MESSAGE_EMPTY_PASSWORD,
            accountPage.getChangeEmailPasswordField(),
            accountPage.getChangeEmailButton(),
            accountPage.getInvalidChangeEmailField()
        );
    }

    public void validateBadPassword() {
        setUpForPasswordTest();

        WebElement passwordField = accountPage.getChangeEmailPasswordField();
        passwordField.sendKeys(SeleniumUser.createRandomPassword());

        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.invisibilityOf(accountPage.getInvalidChangeEmailField()));
        webDriverWait.until(ExpectedConditions.invisibilityOf(accountPage.getInvalidChangeEmailPasswordField()));

        assertFalse(accountPage.getInvalidChangeEmailField().isDisplayed());
        assertFalse(accountPage.getInvalidChangeEmailPasswordField().isDisplayed());

        WebElement submitButton = accountPage.getChangeEmailButton();
        assertTrue(submitButton.isEnabled());
        submitButton.click();

        notificationValidator.verifyOnlyOneNotification(NOTIFICATION_BAD_PASSWORD);
        assertTrue(accountPage.getChangeEmailPasswordField().getAttribute(ATTRIBUTE_VALUE).isEmpty());
    }

    public void validateHappyPath() {
        clearAll();

        user.setEmail(SeleniumUser.createRandomEmail());

        changeEmail(user.getEmail(), originalUser.getPassword());
        clearAll();
        changeEmail(originalUser.getEmail(), originalUser.getPassword());
    }

    private void changeEmail(String email, String password) {
        WebElement emailField = accountPage.getChangeEmailField();
        emailField.sendKeys(email);

        WebElement passwordField = accountPage.getChangeEmailPasswordField();
        passwordField.sendKeys(password);

        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.invisibilityOf(accountPage.getInvalidChangeEmailField()));
        webDriverWait.until(ExpectedConditions.invisibilityOf(accountPage.getInvalidChangeEmailPasswordField()));

        WebElement submitButton = accountPage.getChangeEmailButton();
        assertTrue(submitButton.isEnabled());
        submitButton.click();
        
        verifySuccessfulChange();
    }

    private void verifySuccessfulChange() {
        notificationValidator.verifyOnlyOneNotification(NOTIFICATION_SUCCESSFUL_EMAIL_CHANGE);

        assertTrue(accountPage.getChangeEmailPasswordField().getAttribute(ATTRIBUTE_VALUE).isEmpty());
    }

    private void setUpForEmailTest() {
        clearAll();

        accountPage.getChangeEmailPasswordField().sendKeys(SeleniumUser.createRandomPassword());
    }

    private void setUpForPasswordTest() {
        clearAll();

        accountPage.getChangeEmailField().sendKeys(SeleniumUser.createRandomEmail());
    }

    private void clearAll() {
        cleanNotifications(driver);

        accountPage.getChangeEmailField().clear();
        accountPage.getChangeEmailPasswordField().clear();
    }
}
