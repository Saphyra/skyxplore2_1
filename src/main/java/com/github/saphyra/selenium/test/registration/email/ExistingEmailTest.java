package com.github.saphyra.selenium.test.registration.email;

import com.github.saphyra.selenium.logic.domain.SeleniumUser;
import com.github.saphyra.selenium.logic.validator.FieldValidator;
import lombok.Builder;
import org.openqa.selenium.WebElement;

import com.github.saphyra.selenium.logic.flow.Logout;
import com.github.saphyra.selenium.logic.flow.Registration;
import com.github.saphyra.selenium.logic.page.IndexPage;
import com.github.saphyra.selenium.test.registration.email.helper.EmailTestHelper;

import java.util.Map;

@Builder
public class ExistingEmailTest {
    private static final String MESSAGE_CODE_EXISTING_EMAIL = "EMAIL_ALREADY_EXISTS";

    private final Registration registration;
    private final Logout logout;
    private final EmailTestHelper emailTestHelper;
    private final IndexPage indexPage;
    private final FieldValidator fieldValidator;
    private final Map<String, String> messageCodes;

    public void testExistingEmail() {
        SeleniumUser otherUser = registration.registerUser();
        logout.logOut();

        SeleniumUser user = SeleniumUser.create();
        emailTestHelper.fillFields(user);

        WebElement emailField = indexPage.getRegistrationEmailField();
        emailField.sendKeys(otherUser.getEmail());

        fieldValidator.verifyError(
            indexPage.getInvalidEmailField(),
            messageCodes.get(MESSAGE_CODE_EXISTING_EMAIL),
            emailField,
            indexPage.getRegisterButton()
        );
    }
}
