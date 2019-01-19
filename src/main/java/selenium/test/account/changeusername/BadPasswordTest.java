package selenium.test.account.changeusername;

import static org.junit.Assert.assertTrue;
import static selenium.logic.util.Util.ATTRIBUTE_VALUE;

import lombok.Builder;
import selenium.logic.domain.SeleniumUser;
import selenium.logic.page.AccountPage;
import selenium.logic.validator.NotificationValidator;
import selenium.test.account.changeusername.helper.ChangeUserNameTestHelper;

@Builder
public class BadPasswordTest {
    private static final String NOTIFICATION_BAD_PASSWORD = "Hibás jelszó.";

    private final ChangeUserNameTestHelper changeUserNameTestHelper;
    private final AccountPage accountPage;
    private final NotificationValidator notificationValidator;

    public void testBadPassword() {
        changeUserNameTestHelper.setUpWithRandomUserName();

        accountPage.getChangeUserNamePasswordField().sendKeys(SeleniumUser.createRandomPassword());
        changeUserNameTestHelper.sendForm();

        notificationValidator.verifyOnlyOneNotification(NOTIFICATION_BAD_PASSWORD);
        assertTrue(accountPage.getChangeUserNamePasswordField().getAttribute(ATTRIBUTE_VALUE).isEmpty());
    }
}
