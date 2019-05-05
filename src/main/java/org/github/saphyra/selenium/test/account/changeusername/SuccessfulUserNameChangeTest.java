package org.github.saphyra.selenium.test.account.changeusername;

import lombok.Builder;
import org.github.saphyra.selenium.logic.domain.localization.MessageCodes;
import org.github.saphyra.selenium.logic.domain.SeleniumUser;
import org.github.saphyra.selenium.logic.flow.Login;
import org.github.saphyra.selenium.logic.flow.Logout;
import org.github.saphyra.selenium.logic.page.AccountPage;
import org.github.saphyra.selenium.logic.validator.NotificationValidator;
import org.github.saphyra.selenium.test.account.changeusername.helper.ChangeUserNameTestHelper;

import static org.junit.Assert.assertTrue;

@Builder
public class SuccessfulUserNameChangeTest {
    private static final String MESSAGE_CODE_SUCCESSFUL_USERNAME_CHANGE = "USERNAME_CHANGE_SUCCESSFUL";

    private final ChangeUserNameTestHelper changeUserNameTestHelper;
    private final AccountPage accountPage;
    private final NotificationValidator notificationValidator;
    private final Logout logout;
    private final Login login;
    private final MessageCodes messageCodes;

    public void testSuccessfulUserNameChange() {
        SeleniumUser user = changeUserNameTestHelper.setUpWithCurrentPassword();

        String newName = SeleniumUser.createRandomUserName();
        SeleniumUser newUser = user.cloneUser();
        newUser.setUserName(newName);

        accountPage.getChangeUserNameField().sendKeys(newName);

        changeUserNameTestHelper.sendForm();

        notificationValidator.verifyOnlyOneNotification(messageCodes.get(MESSAGE_CODE_SUCCESSFUL_USERNAME_CHANGE));
        assertTrue(accountPage.getChangeUserNamePasswordField().getText().isEmpty());

        logout.logOut();
        login.loginFailure(user);
        login.login(newUser);

    }
}
