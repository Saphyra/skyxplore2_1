package selenium.test.account.changeusername;

import org.openqa.selenium.WebElement;

import lombok.Builder;
import selenium.logic.domain.SeleniumUser;
import selenium.logic.flow.Registration;
import selenium.logic.page.AccountPage;
import selenium.logic.validator.FieldValidator;
import selenium.test.account.changeusername.helper.ChangeUserNameTestHelper;

@Builder
public class ExistingUserNameTest {
    private static final String ERROR_MESSAGE_USER_NAME_EXISTS = "Felhasználónév foglalt.";

    private final Registration registration;
    private final ChangeUserNameTestHelper changeUserNameTestHelper;
    private final AccountPage accountPage;
    private final FieldValidator fieldValidator;

    public void testExistingUserName() {
        SeleniumUser otherUser = registration.registerUser();
        changeUserNameTestHelper.setUpWithCurrentPassword();

        WebElement userNameField = accountPage.getChangeUserNameField();
        userNameField.sendKeys(otherUser.getUserName());

        fieldValidator.verifyError(
            accountPage.getInvalidChangeUserNameField(),
            ERROR_MESSAGE_USER_NAME_EXISTS,
            userNameField,
            accountPage.getChangeUserNameButton()
        );
    }
}
