package com.github.saphyra.selenium.test.account.changepassword;

import lombok.Builder;
import org.openqa.selenium.WebElement;
import com.github.saphyra.selenium.logic.domain.localization.MessageCodes;
import com.github.saphyra.selenium.logic.domain.SeleniumUser;
import com.github.saphyra.selenium.logic.page.AccountPage;
import com.github.saphyra.selenium.logic.validator.NotificationValidator;
import com.github.saphyra.selenium.test.account.changepassword.helper.ChangePasswordTestHelper;

@Builder
public class BadPasswordTest {
    private static final String MESSAGE_CODE_BAD_PASSWORD = "BAD_PASSWORD";

    private final ChangePasswordTestHelper changePasswordTestHelper;
    private final AccountPage accountPage;
    private final NotificationValidator notificationValidator;
    private final MessageCodes messageCodes;

    public void testBadPassword() {
        changePasswordTestHelper.registerAndNavigateToAccount();

        String newPassword = SeleniumUser.createRandomPassword();

        WebElement newPasswordField = accountPage.getNewPasswordField();
        newPasswordField.sendKeys(newPassword);

        WebElement confirmPasswordField = accountPage.getNewConfirmPasswordField();
        confirmPasswordField.sendKeys(newPassword);

        WebElement currentPasswordField = accountPage.getCurrentNewPasswordField();
        currentPasswordField.sendKeys(SeleniumUser.createRandomPassword());

        changePasswordTestHelper.sendForm();

        notificationValidator.verifyOnlyOneNotification(messageCodes.get(MESSAGE_CODE_BAD_PASSWORD));

        changePasswordTestHelper.verifyInputFieldsAreEmpty();
    }
}
