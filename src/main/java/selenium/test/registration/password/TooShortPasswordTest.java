package selenium.test.registration.password;

import static selenium.logic.util.Util.crop;
import static skyxplore.controller.request.user.UserRegistrationRequest.PASSWORD_MIN_LENGTH;

import org.openqa.selenium.WebElement;

import lombok.Builder;
import selenium.logic.domain.MessageCodes;
import selenium.logic.domain.SeleniumUser;
import selenium.logic.page.IndexPage;
import selenium.logic.validator.FieldValidator;
import selenium.test.registration.password.helper.PasswordTestHelper;

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
        passwordField.sendKeys(crop(user.getPassword(), PASSWORD_MIN_LENGTH - 1));

        fieldValidator.verifyError(
            indexPage.getInvalidPasswordField(),
            messageCodes.get(MESSAGE_CODE_PASSWORD_TOO_SHORT),
            passwordField,
            indexPage.getRegisterButton()
        );
    }
}
