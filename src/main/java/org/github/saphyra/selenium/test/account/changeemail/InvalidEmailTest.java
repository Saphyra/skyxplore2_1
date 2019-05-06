package org.github.saphyra.selenium.test.account.changeemail;

import lombok.Builder;
import org.openqa.selenium.WebElement;
import org.github.saphyra.selenium.logic.domain.localization.MessageCodes;
import org.github.saphyra.selenium.logic.page.AccountPage;
import org.github.saphyra.selenium.logic.validator.FieldValidator;
import org.github.saphyra.selenium.test.account.changeemail.helper.ChangeEmailTestHelper;

@Builder
public class InvalidEmailTest {
    private static final String MESSAGE_CODE_INVALID_EMAIL = "INVALID_EMAIL";
    private static final String INVALID_EMAIL = "aa.a";

    private final ChangeEmailTestHelper changeEmailTestHelper;
    private final FieldValidator fieldValidator;
    private final AccountPage accountPage;
    private final MessageCodes messageCodes;

    public void testInvalidEmail() {
        changeEmailTestHelper.setUpWithCurrentPassword();

        WebElement emailField = accountPage.getChangeEmailField();
        emailField.sendKeys(INVALID_EMAIL);

        fieldValidator.verifyError(
            accountPage.getInvalidChangeEmailField(),
            messageCodes.get(MESSAGE_CODE_INVALID_EMAIL),
            emailField,
            accountPage.getChangeEmailButton(),
            accountPage.getInvalidChangeEmailPasswordField()
        );
    }
}
