package com.github.saphyra.selenium.test.account.changepassword;

import com.github.saphyra.selenium.test.account.changepassword.helper.ChangePasswordTestHelper;
import lombok.Builder;
import com.github.saphyra.selenium.logic.domain.localization.MessageCodes;
import com.github.saphyra.selenium.logic.domain.SeleniumUser;
import com.github.saphyra.selenium.logic.page.AccountPage;
import com.github.saphyra.selenium.logic.validator.FieldValidator;

@Builder
public class EmptyCurrentPasswordTest {
    private static final String MESSAGE_CODE_EMPTY_CURRENT_PASSWORD = "CURRENT_PASSWORD_IS_EMPTY";

    private final ChangePasswordTestHelper changePasswordTestHelper;
    private final AccountPage accountPage;
    private final FieldValidator fieldValidator;
    private final MessageCodes messageCodes;

    public void testEmptyCurrentPassword() {
        SeleniumUser user = changePasswordTestHelper.registerAndNavigateToAccount();

        accountPage.getNewPasswordField().sendKeys(user.getPassword());
        accountPage.getNewConfirmPasswordField().sendKeys(user.getPassword());

        fieldValidator.verifyError(
            accountPage.getCurrentInvalidNewPasswordField(),
            messageCodes.get(MESSAGE_CODE_EMPTY_CURRENT_PASSWORD),
            accountPage.getCurrentNewPasswordField(),
            accountPage.getChangePasswordButton(),
            accountPage.getInvalidNewPasswordField(),
            accountPage.getInvalidNewConfirmPasswordField()
        );
    }
}
