package selenium.test.account.changeusername;

import org.openqa.selenium.WebElement;

import lombok.Builder;
import selenium.logic.page.AccountPage;
import selenium.logic.validator.FieldValidator;
import selenium.test.account.changeusername.helper.ChangeUserNameTestHelper;

@Builder
public class EmptyPasswordTest {
    private static final String ERROR_MESSAGE_EMPTY_PASSWORD = "Jelszó megadása kötelező!";

    private final ChangeUserNameTestHelper changeUserNameTestHelper;
    private final AccountPage accountPage;
    private final FieldValidator fieldValidator;

    public void testEmptyPassword() {
        changeUserNameTestHelper.setUpWithRandomUserName();

        WebElement changePasswordField = accountPage.getChangeUserNamePasswordField();

        fieldValidator.verifyError(
            accountPage.getInvalidChangeUserNamePasswordField(),
            ERROR_MESSAGE_EMPTY_PASSWORD,
            changePasswordField,
            accountPage.getChangeUserNameButton()
        );
    }
}
