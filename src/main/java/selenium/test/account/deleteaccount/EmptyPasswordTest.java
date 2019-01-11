package selenium.test.account.deleteaccount;

import org.openqa.selenium.WebElement;

import lombok.Builder;
import selenium.logic.page.AccountPage;
import selenium.logic.validator.FieldValidator;
import selenium.test.account.deleteaccount.helper.DeleteAccountTestHelper;

@Builder
public class EmptyPasswordTest {
    private static final String ERROR_MESSAGE_EMPTY_PASSWORD = "Jelszó megadása kötelező!";

    private final DeleteAccountTestHelper deleteAccountTestHelper;
    private final AccountPage accountPage;
    private final FieldValidator fieldValidator;

    public void testEmptyPassword() {
        deleteAccountTestHelper.registerAndNavigateToAccount();

        WebElement passwordField = accountPage.getDeleteAccountPasswordField();
        passwordField.sendKeys();

        fieldValidator.verifyError(
            accountPage.getInvalidDeleteAccountPasswordField(),
            ERROR_MESSAGE_EMPTY_PASSWORD,
            passwordField,
            accountPage.getDeleteAccountButton()
        );
    }
}
