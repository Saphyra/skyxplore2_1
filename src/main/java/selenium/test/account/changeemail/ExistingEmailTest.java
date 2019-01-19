package selenium.test.account.changeemail;

import lombok.Builder;
import org.openqa.selenium.WebElement;
import selenium.logic.domain.SeleniumUser;
import selenium.logic.flow.Registration;
import selenium.logic.page.AccountPage;
import selenium.logic.validator.FieldValidator;
import selenium.test.account.changeemail.helper.ChangeEmailTestHelper;

@Builder
public class ExistingEmailTest {
    private static final String ERROR_MESSAGE_EMAIL_ALREADY_EXISTS = "E-mail cím már regisztrálva van.";

    private final Registration registration;
    private final ChangeEmailTestHelper changeEmailTestHelper;
    private final AccountPage accountPage;
    private final FieldValidator fieldValidator;

    public void testExistingEmail() {
        SeleniumUser otherUser = registration.registerUser();
        changeEmailTestHelper.setUpWithCurrentPassword();

        WebElement emailField = accountPage.getChangeEmailField();
        emailField.sendKeys(otherUser.getEmail());

        fieldValidator.verifyError(
            accountPage.getInvalidChangeEmailField(),
            ERROR_MESSAGE_EMAIL_ALREADY_EXISTS,
            emailField,
            accountPage.getChangeEmailButton(),
            accountPage.getInvalidChangeEmailPasswordField()
        );
    }
}
