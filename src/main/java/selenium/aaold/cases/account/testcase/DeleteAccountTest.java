package selenium.aaold.cases.account.testcase;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static selenium.aanew.logic.util.Util.cleanNotifications;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import lombok.Builder;
import selenium.aanew.logic.domain.SeleniumUser;
import selenium.aanew.logic.flow.Login;
import selenium.aanew.logic.flow.Navigate;
import selenium.aanew.logic.page.AccountPage;
import selenium.aanew.logic.validator.FieldValidator;
import selenium.aanew.logic.validator.NotificationValidator;

@Builder
public class DeleteAccountTest {



    private static final String NOTIFICATION_ACCOUNT_DELETED = "Account törölve.";

    private final WebDriver driver;
    private final Navigate navigate;
    private final Login login;
    private final AccountPage accountPage;
    private final SeleniumUser originalUser;
    private final FieldValidator fieldValidator;
    private final NotificationValidator notificationValidator;

    public void validateSuccess() {
        clearAll();
        WebElement passwordField = accountPage.getDeleteAccountPasswordField();
        passwordField.sendKeys(originalUser.getPassword());

        verifyFormSubmittable();

        accountPage.getDeleteAccountButton().click();

        verifyAlert();
        driver.switchTo().alert().accept();

        notificationValidator.verifyOnlyOneNotification(NOTIFICATION_ACCOUNT_DELETED);

        login.loginFailure(originalUser);
    }

    private void clearAll() {
        cleanNotifications(driver);

        accountPage.getDeleteAccountPasswordField().clear();
    }

    private void verifyFormSubmittable(){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.invisibilityOf(accountPage.getInvalidDeleteAccountPasswordField()));

        assertFalse(accountPage.getInvalidDeleteAccountPasswordField().isDisplayed());
        assertTrue(accountPage.getDeleteAccountButton().isEnabled());
    }

    private void verifyAlert() {
        assertNotNull(ExpectedConditions.alertIsPresent().apply(driver));
    }
}
