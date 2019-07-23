package com.github.saphyra.selenium.test.registration.password;

import static com.github.saphyra.selenium.logic.util.Util.crop;

import org.openqa.selenium.WebElement;

import com.github.saphyra.selenium.logic.domain.SeleniumUser;
import com.github.saphyra.selenium.logic.domain.localization.MessageCodes;
import com.github.saphyra.selenium.logic.validator.FieldValidator;
import com.github.saphyra.selenium.test.registration.password.helper.PasswordTestHelper;
import com.github.saphyra.skyxplore.userdata.user.domain.UserRegistrationRequest;
import lombok.Builder;
import com.github.saphyra.selenium.logic.page.IndexPage;

@Builder
public class TooShortPasswordTest {
    private static final String MESSAGE_CODE_PASSWORD_TOO_SHORT = "PASSWORD_TOO_SHORT";

    private final PasswordTestHelper passwordTestHelper;
    private final IndexPage indexPage;
    private final FieldValidator fieldValidator;
    private final MessageCodes messageCodes;

    public void testTooShortPassword() {
        SeleniumUser user = SeleniumUser.create();
        passwordTestHelper.setUpForPasswordTest(user);

        WebElement passwordField = indexPage.getRegistrationPasswordField();
        passwordField.sendKeys(crop(user.getPassword(), UserRegistrationRequest.PASSWORD_MIN_LENGTH - 1));

        fieldValidator.verifyError(
            indexPage.getInvalidPasswordField(),
            messageCodes.get(MESSAGE_CODE_PASSWORD_TOO_SHORT),
            passwordField,
            indexPage.getRegisterButton()
        );
    }
}
