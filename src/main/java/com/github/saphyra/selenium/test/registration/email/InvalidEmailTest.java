package com.github.saphyra.selenium.test.registration.email;

import java.util.Map;

import com.github.saphyra.selenium.logic.domain.SeleniumUser;
import com.github.saphyra.selenium.logic.page.IndexPage;
import com.github.saphyra.selenium.logic.validator.FieldValidator;
import com.github.saphyra.selenium.test.registration.email.helper.EmailTestHelper;
import org.openqa.selenium.WebElement;

import lombok.Builder;

@Builder
public class InvalidEmailTest {
    private static final String INVALID_EMAIL = "aa.a@";
    private static final String MESSAGE_CODE_INVALID_EMAIL = "INVALID_EMAIL";

    private final EmailTestHelper emailTestHelper;
    private final IndexPage indexPage;
    private final FieldValidator fieldValidator;
    private final Map<String, String> messageCodes;

    public void testInvalidEmail() {
        SeleniumUser user = SeleniumUser.create();
        emailTestHelper.fillFields(user);

        WebElement emailField = indexPage.getRegistrationEmailField();
        emailField.sendKeys(INVALID_EMAIL);

        fieldValidator.verifyError(
            indexPage.getInvalidEmailField(),
            messageCodes.get(MESSAGE_CODE_INVALID_EMAIL),
            emailField,
            indexPage.getRegisterButton()
        );
    }
}
