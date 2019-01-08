package selenium.aanew.test.account.changeusername;

import org.openqa.selenium.WebElement;

import lombok.Builder;
import selenium.aanew.logic.domain.SeleniumUser;
import selenium.aanew.logic.flow.Registration;
import selenium.aanew.logic.page.AccountPage;
import selenium.aanew.logic.validator.FieldValidator;
import selenium.aanew.test.account.changeusername.helper.ChangeUserNameTestHelper;

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
