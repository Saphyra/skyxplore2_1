package com.github.saphyra.selenium.test.account.changepassword;

import lombok.Builder;
import org.openqa.selenium.WebElement;

import com.github.saphyra.selenium.logic.domain.SeleniumUser;
import com.github.saphyra.selenium.logic.page.AccountPage;
import com.github.saphyra.selenium.logic.validator.FieldValidator;
import com.github.saphyra.selenium.test.account.changepassword.helper.ChangePasswordTestHelper;

@Builder
public class BadConfirmPasswordTest {
    private static final String MESSAGE_CODE_BAD_CONFIRM_PASSWORD = "BAD_CONFIRM_PASSWORD";

    private final ChangePasswordTestHelper changePasswordTestHelper;
    private final AccountPage accountPage;
    private final FieldValidator fieldValidator;
    private final MessageCodes messageCodes;

    public void testBadConfirmPassword() {
        changePasswordTestHelper.setUpWithCurrentPassword();

        WebElement newPasswordField = accountPage.getNewPasswordField();
        newPasswordField.sendKeys(SeleniumUser.createRandomPassword());

        WebElement newConfirmPasswordField = accountPage.getNewConfirmPasswordField();
        newConfirmPasswordField.sendKeys(SeleniumUser.createRandomPassword());

        fieldValidator.verifyError(
            accountPage.getInvalidNewPasswordField(),
            messageCodes.get(MESSAGE_CODE_BAD_CONFIRM_PASSWORD),
            newPasswordField,
            accountPage.getChangePasswordButton(),
            accountPage.getCurrentInvalidNewPasswordField()
        );

        fieldValidator.verifyError(
            accountPage.getInvalidNewConfirmPasswordField(),
            messageCodes.get(MESSAGE_CODE_BAD_CONFIRM_PASSWORD),
            newConfirmPasswordField,
            accountPage.getChangePasswordButton()
        );
    }
}
