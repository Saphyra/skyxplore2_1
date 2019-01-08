package selenium.aanew.test.account.changeusername;

import static selenium.aanew.logic.util.Util.crop;
import static skyxplore.controller.request.user.UserRegistrationRequest.USER_NAME_MIN_LENGTH;

import org.openqa.selenium.WebElement;

import lombok.Builder;
import selenium.aanew.logic.domain.SeleniumUser;
import selenium.aanew.logic.page.AccountPage;
import selenium.aanew.logic.validator.FieldValidator;
import selenium.aanew.test.account.changeusername.helper.ChangeUserNameTestHelper;

@Builder
public class TooShortUserNameTest {
    private static final String ERROR_MESSAGE_USER_NAME_TOO_SHORT = "Túl rövid felhasználónév. (Minimum 3 karakter)";

    private final ChangeUserNameTestHelper changeUserNameTestHelper;
    private final AccountPage accountPage;
    private final FieldValidator fieldValidator;

    public void testTooShortUserName() {
        changeUserNameTestHelper.setUpWithCurrentPassword();

        WebElement userNameField = accountPage.getChangeUserNameField();
        userNameField.sendKeys(crop(SeleniumUser.createRandomUserName(), USER_NAME_MIN_LENGTH - 1));

        fieldValidator.verifyError(
            accountPage.getInvalidChangeUserNameField(),
            ERROR_MESSAGE_USER_NAME_TOO_SHORT,
            userNameField,
            accountPage.getChangeUserNameButton()
        );
    }
}
