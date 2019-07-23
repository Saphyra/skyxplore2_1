package com.github.saphyra.selenium.test.registration.password;

import org.openqa.selenium.WebElement;

import com.github.saphyra.selenium.logic.domain.SeleniumUser;
import com.github.saphyra.selenium.logic.domain.localization.MessageCodes;
import com.github.saphyra.selenium.logic.validator.FieldValidator;
import com.github.saphyra.selenium.test.registration.password.helper.PasswordTestHelper;
import com.github.saphyra.skyxplore.userdata.user.domain.UserRegistrationRequest;
import lombok.Builder;
import com.github.saphyra.selenium.logic.page.IndexPage;

@Builder
public class TooLongPasswordTest {
    private static final String TOO_LONG_PASSWORD;

    static {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i <= UserRegistrationRequest.PASSWORD_MAX_LENGTH + 1; i++) {
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
