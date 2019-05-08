package com.github.saphyra.selenium.test.registration.password;

import org.openqa.selenium.WebElement;

import com.github.saphyra.selenium.logic.domain.SeleniumUser;
import com.github.saphyra.selenium.logic.domain.localization.MessageCodes;
import com.github.saphyra.selenium.logic.validator.FieldValidator;
import com.github.saphyra.selenium.test.registration.password.helper.PasswordTestHelper;
import lombok.Builder;
import com.github.saphyra.selenium.logic.page.IndexPage;

@Builder
public class ConfirmPasswordTest {
    private static final String MESSAGE_CODE_BAD_CONFIRM_PASSWORD = "BAD_CONFIRM_PASSWORD";

    private final PasswordTestHelper passwordTestHelper;
    private final IndexPage indexPage;
    private final FieldValidator fieldValidator;
    private final MessageCodes messageCodes;

    public void testConfirmPassword() {
        SeleniumUser user = SeleniumUser.create();
        passwordTestHelper.setUpForPasswordTest(user);

        WebElement passwordField = indexPage.getRegistrationPasswordField();
        passwordField.sendKeys(user.getPassword());

        WebElement confirmPasswordField = indexPage.getRegistrationConfirmPasswordField();
        confirmPasswordField.sendKeys(SeleniumUser.createRandomPassword());

        fieldValidator.verifyError(
            indexPage.getInvalidPasswordField(),
            messageCodes.get(MESSAGE_CODE_BAD_CONFIRM_PASSWORD),
            passwordField,
            indexPage.getRegisterButton()
        );

        fieldValidator.verifyError(
            indexPage.getInvalidConfirmPasswordField(),
            messageCodes.get(MESSAGE_CODE_BAD_CONFIRM_PASSWORD),
            confirmPasswordField,
            indexPage.getRegisterButton()
        );
    }
}
