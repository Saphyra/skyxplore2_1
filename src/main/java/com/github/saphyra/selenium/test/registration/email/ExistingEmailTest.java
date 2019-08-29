package com.github.saphyra.selenium.test.registration.email;

import com.github.saphyra.selenium.logic.domain.SeleniumUser;
import com.github.saphyra.selenium.logic.domain.localization.Page;
import com.github.saphyra.selenium.logic.flow.Logout;
import com.github.saphyra.selenium.logic.flow.Registration;
import com.github.saphyra.selenium.logic.page.IndexPage;
import com.github.saphyra.selenium.logic.validator.FieldValidator;
import com.github.saphyra.selenium.test.registration.email.helper.EmailTestHelper;
import lombok.Builder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.github.saphyra.selenium.logic.util.LocalizationUtil.getAdditionalContent;

@Builder
public class ExistingEmailTest {
    private static final String MESSAGE_CODE_EXISTING_EMAIL = "email-already-exists";

    private final WebDriver driver;
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
            getAdditionalContent(driver, Page.INDEX, MESSAGE_CODE_EXISTING_EMAIL),
            emailField,
            indexPage.getRegisterButton()
        );
    }
}
