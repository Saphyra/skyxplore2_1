package com.github.saphyra.selenium.test.registration.email;

import com.github.saphyra.selenium.logic.domain.SeleniumUser;
import com.github.saphyra.selenium.logic.domain.localization.Page;
import com.github.saphyra.selenium.logic.page.IndexPage;
import com.github.saphyra.selenium.logic.validator.FieldValidator;
import com.github.saphyra.selenium.test.registration.email.helper.EmailTestHelper;
import lombok.Builder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.github.saphyra.selenium.logic.util.LocalizationUtil.getAdditionalContent;

@Builder
public class InvalidEmailTest {
    private static final String INVALID_EMAIL = "aa.a@";
    private static final String MESSAGE_CODE_INVALID_EMAIL = "invalid-email";

    private final WebDriver driver;
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
            getAdditionalContent(driver, Page.INDEX, MESSAGE_CODE_INVALID_EMAIL),
            emailField,
            indexPage.getRegisterButton()
        );
    }
}
