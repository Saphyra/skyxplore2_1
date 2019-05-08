package com.github.saphyra.selenium.test.account.changeemail;

import lombok.Builder;
import com.github.saphyra.selenium.logic.domain.localization.MessageCodes;
import com.github.saphyra.selenium.logic.page.AccountPage;
import com.github.saphyra.selenium.logic.validator.FieldValidator;
import com.github.saphyra.selenium.test.account.changeemail.helper.ChangeEmailTestHelper;

@Builder
public class EmptyPasswordTest {
    private static final String MESSAGE_CODE_EMPTY_PASSWORD = "CURRENT_PASSWORD_IS_EMPTY";

    private final ChangeEmailTestHelper changeEmailTestHelper;
    private final AccountPage accountPage;
    private final FieldValidator fieldValidator;
    private final MessageCodes messageCodes;

    public void testEmptyPassword() {
        changeEmailTestHelper.setUpWithRandomEmail();

        accountPage.getChangeEmailPasswordField().clear();

        fieldValidator.verifyError(
            accountPage.getInvalidChangeEmailPasswordField(),
            messageCodes.get(MESSAGE_CODE_EMPTY_PASSWORD),
            accountPage.getChangeEmailPasswordField(),
            accountPage.getChangeEmailButton(),
            accountPage.getInvalidChangeEmailField()
        );
    }
}
