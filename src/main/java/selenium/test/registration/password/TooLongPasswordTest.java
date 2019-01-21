package selenium.test.registration.password;

import lombok.Builder;
import org.openqa.selenium.WebElement;
import selenium.logic.domain.SeleniumUser;
import selenium.logic.page.IndexPage;
import selenium.logic.validator.FieldValidator;
import selenium.test.registration.password.helper.PasswordTestHelper;

import static skyxplore.controller.request.user.UserRegistrationRequest.PASSWORD_MAX_LENGTH;

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

    private static final String ERROR_MESSAGE_TOO_LONG_PASSWORD = " túl hosszú (Maximum 30 karakter).";

    private final PasswordTestHelper passwordTestHelper;
    private final IndexPage indexPage;
    private final FieldValidator fieldValidator;

    public void testTooLongPassword() {
        SeleniumUser user = SeleniumUser.create();
        passwordTestHelper.setUpForPasswordTest(user);

        WebElement passwordField = indexPage.getRegistrationPasswordField();
        passwordField.sendKeys(TOO_LONG_PASSWORD);

        fieldValidator.verifyError(
            indexPage.getInvalidPasswordField(),
            ERROR_MESSAGE_TOO_LONG_PASSWORD,
            passwordField,
            indexPage.getRegisterButton()
        );
    }
}
