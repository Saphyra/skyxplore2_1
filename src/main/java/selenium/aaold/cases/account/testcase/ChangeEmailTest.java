package selenium.aaold.cases.account.testcase;

import lombok.Builder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.aanew.logic.domain.SeleniumUser;
import selenium.aanew.logic.page.AccountPage;
import selenium.aanew.logic.validator.FieldValidator;
import selenium.aanew.logic.validator.NotificationValidator;

import static org.junit.Assert.assertTrue;
import static selenium.aanew.logic.util.Util.ATTRIBUTE_VALUE;
import static selenium.aanew.logic.util.Util.cleanNotifications;

@Builder
public class ChangeEmailTest {
    //TODO restore after fixing FE validation
    //private static final String INVALID_EMAIL = "aa.a@";


    private static final String ERROR_MESSAGE_EMPTY_PASSWORD = "Jelszó megadása kötelező!";
    private static final String NOTIFICATION_SUCCESSFUL_EMAIL_CHANGE = "E-mail cím sikeresen megváltoztatva.";

    private final WebDriver driver;
    private final AccountPage accountPage;
    private final SeleniumUser user;
    private final SeleniumUser originalUser;
    private final SeleniumUser otherUser;
    private final FieldValidator fieldValidator;
    private final NotificationValidator notificationValidator;

    /*


    public void validateHappyPath() {
        clearAll();

        user.setEmail(SeleniumUser.createRandomEmail());

        changeEmail(user.getEmail(), originalUser.getPassword());
        clearAll();
        changeEmail(originalUser.getEmail(), originalUser.getPassword());
    }*/

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
