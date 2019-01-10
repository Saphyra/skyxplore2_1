package selenium.aanew.test.registration.username;

import lombok.Builder;
import org.openqa.selenium.WebElement;
import selenium.aanew.logic.domain.SeleniumUser;
import selenium.aanew.logic.flow.Logout;
import selenium.aanew.logic.flow.Registration;
import selenium.aanew.logic.page.IndexPage;
import selenium.aanew.logic.validator.FieldValidator;
import selenium.aanew.test.registration.username.helper.UserNameTestHelper;

@Builder
public class ExistingUserNameTest {
    private static final String ERROR_MESSAGE_EXISTING_USER_NAME = "Felhasználónév foglalt.";

    private final UserNameTestHelper userNameTestHelper;
    private final Registration registration;
    private final Logout logout;
    private final IndexPage indexPage;
    private final FieldValidator fieldValidator;

    public void testExistingUserName() {
        SeleniumUser otherUser = registration.registerUser();
        logout.logOut();

        SeleniumUser user = SeleniumUser.create();
        userNameTestHelper.setUpForUserNameTest(user);

        WebElement userNameField = indexPage.getRegistrationUserNameField();
        userNameField.sendKeys(otherUser.getUserName());

        fieldValidator.verifyError(
            indexPage.getInvalidUserNameField(),
            ERROR_MESSAGE_EXISTING_USER_NAME,
            userNameField,
            indexPage.getRegisterButton()
        );
    }
}
