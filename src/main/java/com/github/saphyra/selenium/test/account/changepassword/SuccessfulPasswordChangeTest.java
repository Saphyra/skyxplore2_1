package com.github.saphyra.selenium.test.account.changepassword;

import com.github.saphyra.selenium.test.account.changepassword.helper.ChangePasswordTestHelper;
import lombok.Builder;
import org.openqa.selenium.WebElement;
import com.github.saphyra.selenium.logic.domain.localization.MessageCodes;
import com.github.saphyra.selenium.logic.domain.SeleniumUser;
import com.github.saphyra.selenium.logic.flow.Login;
import com.github.saphyra.selenium.logic.flow.Logout;
import com.github.saphyra.selenium.logic.flow.Navigate;
import com.github.saphyra.selenium.logic.page.AccountPage;
import com.github.saphyra.selenium.logic.validator.NotificationValidator;

@Builder
public class SuccessfulPasswordChangeTest {
    private static final String MESSAGE_CODE_SUCCESSFUL_PASSWORD_CHANGE = "PASSWORD_CHANGE_SUCCESSFUL";

    private final ChangePasswordTestHelper changePasswordTestHelper;
    private final AccountPage accountPage;
    private final Logout logout;
    private final Login login;
    private final Navigate navigate;
    private final NotificationValidator notificationValidator;
    private final MessageCodes messageCodes;

    public void testSuccessfulPasswordChange() {
        SeleniumUser user = changePasswordTestHelper.registerAndNavigateToAccount();
        SeleniumUser changed = user.cloneUser();
        changed.setPassword(SeleniumUser.createRandomPassword());

        WebElement newPasswordField = accountPage.getNewPasswordField();
        newPasswordField.sendKeys(changed.getPassword());

        WebElement confirmPasswordField = accountPage.getNewConfirmPasswordField();
        confirmPasswordField.sendKeys(changed.getPassword());

        WebElement currentPasswordField = accountPage.getCurrentNewPasswordField();
        currentPasswordField.sendKeys(user.getPassword());

        changePasswordTestHelper.sendForm();

        verifyPasswordChange(user, changed);
    }

    private void verifyPasswordChange(SeleniumUser originalUser, SeleniumUser changed) {
        verifySuccessfulNotification();

        changePasswordTestHelper.verifyInputFieldsAreEmpty();

        logout.logOut();
        login.loginFailure(originalUser);

        login.login(changed);

        navigate.toAccountPage();

        accountPage.getNewPasswordField().sendKeys(originalUser.getPassword());
        accountPage.getNewConfirmPasswordField().sendKeys(originalUser.getPassword());
        accountPage.getCurrentNewPasswordField().sendKeys(changed.getPassword());

        changePasswordTestHelper.sendForm();

        verifySuccessfulNotification();
    }

    private void verifySuccessfulNotification() {
        notificationValidator.verifyOnlyOneNotification(messageCodes.get(MESSAGE_CODE_SUCCESSFUL_PASSWORD_CHANGE));
    }
}
