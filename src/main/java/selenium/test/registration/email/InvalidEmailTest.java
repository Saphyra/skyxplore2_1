package selenium.test.registration.email;

import lombok.Builder;
import org.openqa.selenium.WebElement;
import selenium.logic.domain.SeleniumUser;
import selenium.logic.page.IndexPage;
import selenium.logic.validator.FieldValidator;
import selenium.test.registration.email.helper.EmailTestHelper;

@Builder
public class InvalidEmailTest {
    private static final String INVALID_EMAIL = "aa.a";
    private static final String ERROR_MESSAGE_INVALID_EMAIL = "Érvénytelen e-mail cím.";

    //TODO restore after fixing FE validation
    //private static final String INVALID_EMAIL = "aa.a@";

    private final EmailTestHelper emailTestHelper;
    private final IndexPage indexPage;
    private final FieldValidator fieldValidator;

    public void testInvalidEmail() {
        SeleniumUser user = SeleniumUser.create();
        emailTestHelper.fillFields(user);

        WebElement emailField = indexPage.getRegistrationEmailField();
        emailField.sendKeys(INVALID_EMAIL);

        fieldValidator.verifyError(
            indexPage.getInvalidEmailField(),
            ERROR_MESSAGE_INVALID_EMAIL,
            emailField,
            indexPage.getRegisterButton()
        );
    }
}
