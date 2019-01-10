package selenium.aanew.test.registration.password;

import lombok.Builder;
import org.openqa.selenium.WebElement;
import selenium.aanew.logic.domain.SeleniumUser;
import selenium.aanew.logic.page.IndexPage;
import selenium.aanew.logic.validator.FieldValidator;
import selenium.aanew.test.registration.password.helper.PasswordTestHelper;

import static selenium.aanew.logic.util.Util.crop;
import static skyxplore.controller.request.user.UserRegistrationRequest.PASSWORD_MIN_LENGTH;

@Builder
public class TooShortPasswordTest {
    private static final String ERROR_MESSAGE_TOO_SHORT_PASSWORD = "Jelszó túl rövid (Minimum 6 karakter).";

    private final PasswordTestHelper passwordTestHelper;
    private final IndexPage indexPage;
    private final FieldValidator fieldValidator;

    public void testTooShortPassword() {
        SeleniumUser user = SeleniumUser.create();
        passwordTestHelper.setUpForPasswordTest(user);

        WebElement passwordField = indexPage.getRegistrationPasswordField();
        passwordField.sendKeys(crop(user.getPassword(), PASSWORD_MIN_LENGTH - 1));

        fieldValidator.verifyError(
            indexPage.getInvalidPasswordField(),
            ERROR_MESSAGE_TOO_SHORT_PASSWORD,
            passwordField,
            indexPage.getRegisterButton()
        );
    }
}
