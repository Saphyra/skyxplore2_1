package org.github.saphyra.selenium.test.account.deleteaccount;

import org.openqa.selenium.WebElement;

import lombok.Builder;
import org.github.saphyra.selenium.logic.domain.MessageCodes;
import org.github.saphyra.selenium.logic.page.AccountPage;
import org.github.saphyra.selenium.logic.validator.FieldValidator;
import org.github.saphyra.selenium.test.account.deleteaccount.helper.DeleteAccountTestHelper;

@Builder
public class EmptyPasswordTest {
    private static final String MESSAGE_CODE_EMPTY_PASSWORD = "CURRENT_PASSWORD_IS_EMPTY";

    private final DeleteAccountTestHelper deleteAccountTestHelper;
    private final AccountPage accountPage;
    private final FieldValidator fieldValidator;
    private final MessageCodes messageCodes;

    public void testEmptyPassword() {
        deleteAccountTestHelper.registerAndNavigateToAccount();

        WebElement passwordField = accountPage.getDeleteAccountPasswordField();
        passwordField.sendKeys();

        fieldValidator.verifyError(
            accountPage.getInvalidDeleteAccountPasswordField(),
            messageCodes.get(MESSAGE_CODE_EMPTY_PASSWORD),
            passwordField,
            accountPage.getDeleteAccountButton()
        );
    }
}
