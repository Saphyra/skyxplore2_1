package selenium.test.account.changeemail;

import lombok.Builder;
import org.openqa.selenium.WebElement;
import selenium.logic.page.AccountPage;
import selenium.logic.validator.FieldValidator;
import selenium.test.account.changeemail.helper.ChangeEmailTestHelper;

@Builder
public class InvalidEmailTest {
    private static final String ERROR_MESSAGE_INVALID_EMAIL = "Érvénytelen e-mail cím";
    private static final String INVALID_EMAIL = "aa.a";

    private final ChangeEmailTestHelper changeEmailTestHelper;
    private final FieldValidator fieldValidator;
    private final AccountPage accountPage;

    public void testInvalidEmail() {
        changeEmailTestHelper.setUpWithCurrentPassword();

        WebElement emailField = accountPage.getChangeEmailField();
        emailField.sendKeys(INVALID_EMAIL);

        fieldValidator.verifyError(
            accountPage.getInvalidChangeEmailField(),
            ERROR_MESSAGE_INVALID_EMAIL,
            emailField,
            accountPage.getChangeEmailButton(),
            accountPage.getInvalidChangeEmailPasswordField()
        );
    }
}
