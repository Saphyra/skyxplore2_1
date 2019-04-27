package org.github.saphyra.selenium.test.registration.password;

import static skyxplore.controller.request.user.UserRegistrationRequest.PASSWORD_MAX_LENGTH;

import org.openqa.selenium.WebElement;

import lombok.Builder;
import org.github.saphyra.selenium.logic.domain.MessageCodes;
import org.github.saphyra.selenium.logic.domain.SeleniumUser;
import org.github.saphyra.selenium.logic.page.IndexPage;
import org.github.saphyra.selenium.logic.validator.FieldValidator;
import org.github.saphyra.selenium.test.registration.password.helper.PasswordTestHelper;

@Builder
public class TooLongPasswordTest {
    private static final String TOO_LONG_PASSWORD;

    static {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i <= PASSWORD_MAX_LENGTH + 1; i++) {
            builder.append("a");
        }
        TOO_LONG_PASSWORD = builder.toString();
    }

    private static final String MESSAGE_CODE_PASSWORD_TOO_LONG = "PASSWORD_TOO_LONG";

    private final PasswordTestHelper passwordTestHelper;
    private final IndexPage indexPage;
    private final FieldValidator fieldValidator;
    private final MessageCodes messageCodes;

    public void testTooLongPassword() {
        SeleniumUser user = SeleniumUser.create();
        passwordTestHelper.setUpForPasswordTest(user);

        WebElement passwordField = indexPage.getRegistrationPasswordField();
        passwordField.sendKeys(TOO_LONG_PASSWORD);

        fieldValidator.verifyError(
            indexPage.getInvalidPasswordField(),
            messageCodes.get(MESSAGE_CODE_PASSWORD_TOO_LONG),
            passwordField,
            indexPage.getRegisterButton()
        );
    }
}
