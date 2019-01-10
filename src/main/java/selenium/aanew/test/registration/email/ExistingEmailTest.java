package selenium.aanew.test.registration.email;

import lombok.Builder;
import org.openqa.selenium.WebElement;
import selenium.aanew.logic.domain.SeleniumUser;
import selenium.aanew.logic.flow.Logout;
import selenium.aanew.logic.flow.Registration;
import selenium.aanew.logic.page.IndexPage;
import selenium.aanew.logic.validator.FieldValidator;
import selenium.aanew.test.registration.email.helper.EmailTestHelper;

@Builder
public class ExistingEmailTest {
    private static final String ERROR_MESSAGE_EXISTING_EMAIL = "Már van regisztrált felhasználó a megadott e-mail címmel.";

    private final Registration registration;
    private final Logout logout;
    private final EmailTestHelper emailTestHelper;
    private final IndexPage indexPage;
    private final FieldValidator fieldValidator;

    public void testExistingEmail() {
        SeleniumUser otherUser = registration.registerUser();
        logout.logOut();

        SeleniumUser user = SeleniumUser.create();
        emailTestHelper.fillFields(user);

        WebElement emailField = indexPage.getRegistrationEmailField();
        emailField.sendKeys(otherUser.getEmail());

        fieldValidator.verifyError(
            indexPage.getInvalidEmailField(),
            ERROR_MESSAGE_EXISTING_EMAIL,
            emailField,
            indexPage.getRegisterButton()
        );
    }
}
