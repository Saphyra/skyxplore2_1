package com.github.saphyra.selenium.test.account.changeemail;

import com.github.saphyra.selenium.logic.domain.localization.Page;
import com.github.saphyra.selenium.logic.page.AccountPage;
import com.github.saphyra.selenium.logic.validator.FieldValidator;
import com.github.saphyra.selenium.test.account.changeemail.helper.ChangeEmailTestHelper;
import lombok.Builder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.github.saphyra.selenium.logic.util.LocalizationUtil.getAdditionalContent;

@Builder
public class InvalidEmailTest {
    private static final String MESSAGE_CODE_INVALID_EMAIL = "invalid-email";
    private static final String INVALID_EMAIL = "aa.a";

    private final WebDriver driver;
    private final ChangeEmailTestHelper changeEmailTestHelper;
    private final FieldValidator fieldValidator;
    private final AccountPage accountPage;

    public void testInvalidEmail() {
        changeEmailTestHelper.setUpWithCurrentPassword();

        WebElement emailField = accountPage.getChangeEmailField();
        emailField.sendKeys(INVALID_EMAIL);

        fieldValidator.verifyError(
            accountPage.getInvalidChangeEmailField(),
            getAdditionalContent(driver, Page.ACCOUNT, MESSAGE_CODE_INVALID_EMAIL),
            emailField,
            accountPage.getChangeEmailButton(),
            accountPage.getInvalidChangeEmailPasswordField()
        );
    }
}
