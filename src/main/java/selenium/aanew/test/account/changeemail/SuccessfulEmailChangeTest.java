package selenium.aanew.test.account.changeemail;

import lombok.Builder;
import org.openqa.selenium.WebElement;
import selenium.aanew.logic.domain.SeleniumUser;
import selenium.aanew.logic.page.AccountPage;
import selenium.aanew.logic.validator.NotificationValidator;
import selenium.aanew.test.account.changeemail.helper.ChangeEmailTestHelper;

import static org.junit.Assert.assertTrue;
import static selenium.aanew.logic.util.Util.ATTRIBUTE_VALUE;

@Builder
public class SuccessfulEmailChangeTest {
    private static final String NOTIFICATION_SUCCESSFUL_EMAIL_CHANGE = "E-mail cím sikeresen megváltoztatva.";

    private final ChangeEmailTestHelper changeEmailTestHelper;
    private final AccountPage accountPage;
    private final NotificationValidator notificationValidator;

    public void testSuccessfulEmailChange() {
        SeleniumUser user = changeEmailTestHelper.registerAndNavigateToAccount();

        fillInputFields(user);

        changeEmailTestHelper.submitForm();

        verifySuccess();
    }

    private void fillInputFields(SeleniumUser user) {
        WebElement emailField = accountPage.getChangeEmailField();
        emailField.sendKeys(SeleniumUser.createRandomEmail());

        WebElement passwordField = accountPage.getChangeEmailPasswordField();
        passwordField.sendKeys(user.getPassword());
    }

    private void verifySuccess() {
        notificationValidator.verifyOnlyOneNotification(NOTIFICATION_SUCCESSFUL_EMAIL_CHANGE);
        assertTrue(accountPage.getChangeEmailPasswordField().getAttribute(ATTRIBUTE_VALUE).isEmpty());
    }
}
