package com.github.saphyra.selenium.test.account.changeemail;

import lombok.Builder;
import org.openqa.selenium.WebElement;
import com.github.saphyra.selenium.logic.domain.localization.MessageCodes;
import com.github.saphyra.selenium.logic.domain.SeleniumUser;
import com.github.saphyra.selenium.logic.flow.Registration;
import com.github.saphyra.selenium.logic.page.AccountPage;
import com.github.saphyra.selenium.logic.validator.FieldValidator;
import com.github.saphyra.selenium.test.account.changeemail.helper.ChangeEmailTestHelper;

@Builder
public class ExistingEmailTest {
    private static final String MESSAGE_CODE_EMAIL_ALREADY_EXISTS = "EMAIL_ALREADY_EXISTS";

    private final Registration registration;
    private final ChangeEmailTestHelper changeEmailTestHelper;
    private final AccountPage accountPage;
    private final FieldValidator fieldValidator;
    private final MessageCodes messageCodes;

    public void testExistingEmail() {
        SeleniumUser otherUser = registration.registerUser();
        changeEmailTestHelper.setUpWithCurrentPassword();

        WebElement emailField = accountPage.getChangeEmailField();
        emailField.sendKeys(otherUser.getEmail());

        fieldValidator.verifyError(
            accountPage.getInvalidChangeEmailField(),
            messageCodes.get(MESSAGE_CODE_EMAIL_ALREADY_EXISTS),
            emailField,
            accountPage.getChangeEmailButton(),
            accountPage.getInvalidChangeEmailPasswordField()
        );
    }
}
