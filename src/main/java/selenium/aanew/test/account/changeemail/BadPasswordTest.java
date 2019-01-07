package selenium.aanew.test.account.changeemail;

import lombok.Builder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.aanew.logic.domain.SeleniumUser;
import selenium.aanew.logic.page.AccountPage;
import selenium.aanew.logic.validator.FieldValidator;
import selenium.aanew.logic.validator.NotificationValidator;
import selenium.aanew.test.account.changeemail.helper.ChangeEmailTestHelper;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static selenium.aanew.logic.util.Util.ATTRIBUTE_VALUE;

@Builder
public class BadPasswordTest {
    private static final String NOTIFICATION_BAD_PASSWORD = "Hibás jelszó.";

    private final WebDriver driver;
    private final ChangeEmailTestHelper changeEmailTestHelper;
    private final AccountPage accountPage;
    private final FieldValidator fieldValidator;
    private final NotificationValidator notificationValidator;

    public void testBadPassword() {
        changeEmailTestHelper.setUpWithRandomEmail();

        WebElement passwordField = accountPage.getChangeEmailPasswordField();
        passwordField.sendKeys(SeleniumUser.createRandomPassword());

        verifyFailure();
    }

    private void verifyFailure() {
        verifyFormSubmittable();

        changeEmailTestHelper.submitForm();

        notificationValidator.verifyOnlyOneNotification(NOTIFICATION_BAD_PASSWORD);
        assertTrue(accountPage.getChangeEmailPasswordField().getAttribute(ATTRIBUTE_VALUE).isEmpty());
    }

    private void verifyFormSubmittable() {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.invisibilityOf(accountPage.getInvalidChangeEmailField()));
        webDriverWait.until(ExpectedConditions.invisibilityOf(accountPage.getInvalidChangeEmailPasswordField()));

        assertFalse(accountPage.getInvalidChangeEmailField().isDisplayed());
        assertFalse(accountPage.getInvalidChangeEmailPasswordField().isDisplayed());
    }
}
