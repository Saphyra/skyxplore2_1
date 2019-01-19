package selenium.test.account.changeusername;

import lombok.Builder;
import selenium.logic.domain.SeleniumUser;
import selenium.logic.flow.Login;
import selenium.logic.flow.Logout;
import selenium.logic.page.AccountPage;
import selenium.logic.validator.NotificationValidator;
import selenium.test.account.changeusername.helper.ChangeUserNameTestHelper;

@Builder
public class SuccessfulUserNameChangeTest {
    private static final String NOTIFICATION_SUCCESSFUL_USER_NAME_CHANGE = "Felhasználónév megváltoztatása sikeres.";

    private final ChangeUserNameTestHelper changeUserNameTestHelper;
    private final AccountPage accountPage;
    private final NotificationValidator notificationValidator;
    private final Logout logout;
    private final Login login;

    public void testSuccessfulUserNameChange() {
        SeleniumUser user = changeUserNameTestHelper.setUpWithCurrentPassword();

        String newName = SeleniumUser.createRandomUserName();
        SeleniumUser newUser = user.cloneUser();
        newUser.setUserName(newName);

        accountPage.getChangeUserNameField().sendKeys(newName);

        changeUserNameTestHelper.sendForm();

        notificationValidator.verifyOnlyOneNotification(NOTIFICATION_SUCCESSFUL_USER_NAME_CHANGE);

        logout.logOut();
        login.loginFailure(user);
        login.login(newUser);

    }
}
