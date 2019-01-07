package selenium.aanew.test.account.changeemail;

import lombok.Builder;
import selenium.aanew.logic.page.AccountPage;
import selenium.aanew.logic.validator.FieldValidator;
import selenium.aanew.test.account.changeemail.helper.ChangeEmailTestHelper;

@Builder
public class EmptyPasswordTest {
    private static final String ERROR_MESSAGE_EMPTY_PASSWORD = "Jelszó megadása kötelező!";

    private final ChangeEmailTestHelper changeEmailTestHelper;
    private final AccountPage accountPage;
    private final FieldValidator fieldValidator;

    public void testEmptyPassword() {
        changeEmailTestHelper.setUpWithRandomEmail();

        accountPage.getChangeEmailPasswordField().clear();

        fieldValidator.verifyError(
            accountPage.getInvalidChangeEmailPasswordField(),
            ERROR_MESSAGE_EMPTY_PASSWORD,
            accountPage.getChangeEmailPasswordField(),
            accountPage.getChangeEmailButton(),
            accountPage.getInvalidChangeEmailField()
        );
    }
}
