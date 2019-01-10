package selenium.aanew.test.registration.username;

import lombok.Builder;
import org.openqa.selenium.WebElement;
import selenium.aanew.logic.domain.SeleniumUser;
import selenium.aanew.logic.page.IndexPage;
import selenium.aanew.logic.validator.FieldValidator;
import selenium.aanew.test.registration.username.helper.UserNameTestHelper;

import static skyxplore.controller.request.user.UserRegistrationRequest.USER_NAME_MAX_LENGTH;

@Builder
public class TooLongUserNameTest {
    private static final String TOO_LONG_USER_NAME;

    static {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i <= USER_NAME_MAX_LENGTH + 1; i++) {
            builder.append("a");
        }
        TOO_LONG_USER_NAME = builder.toString();
    }


    private static final String ERROR_MESSAGE_TOO_LONG_USER_NAME = "Felhasználónév túl hosszú (Maximum 30 karakter).";

    private final UserNameTestHelper userNameTestHelper;
    private final IndexPage indexPage;
    private final FieldValidator fieldValidator;

    public void testTooLongUserName() {
        SeleniumUser user = SeleniumUser.create();
        userNameTestHelper.setUpForUserNameTest(user);

        WebElement userNameField = indexPage.getRegistrationUserNameField();
        userNameField.sendKeys(TOO_LONG_USER_NAME);

        fieldValidator.verifyError(
            indexPage.getInvalidUserNameField(),
            ERROR_MESSAGE_TOO_LONG_USER_NAME,
            userNameField,
            indexPage.getRegisterButton()
        );
    }
}
