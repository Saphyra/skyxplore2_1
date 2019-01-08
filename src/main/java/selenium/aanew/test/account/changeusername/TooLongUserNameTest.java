package selenium.aanew.test.account.changeusername;

import static skyxplore.controller.request.user.UserRegistrationRequest.USER_NAME_MAX_LENGTH;

import org.openqa.selenium.WebElement;

import lombok.Builder;
import selenium.aanew.logic.page.AccountPage;
import selenium.aanew.logic.validator.FieldValidator;
import selenium.aanew.test.account.changeusername.helper.ChangeUserNameTestHelper;

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
    private static final String ERROR_MESSAGE_USER_NAME_TOO_LONG = "Túl hosszú felhasználónév. (Maximum 30 karakter)";

    private final AccountPage accountPage;
    private final ChangeUserNameTestHelper changeUserNameTestHelper;
    private final FieldValidator fieldValidator;

    public void testTooLongUserName() {
        changeUserNameTestHelper.setUpWithCurrentPassword();

        WebElement userNameField = accountPage.getChangeUserNameField();
            userNameField.sendKeys(TOO_LONG_USER_NAME);

        fieldValidator.verifyError(
            accountPage.getInvalidChangeUserNameField(),
            ERROR_MESSAGE_USER_NAME_TOO_LONG,
            userNameField,
            accountPage.getChangeUserNameButton()
        );
    }
}
