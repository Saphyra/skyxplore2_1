package com.github.saphyra.selenium.test.account.changepassword;

import com.github.saphyra.selenium.logic.domain.localization.Page;
import com.github.saphyra.selenium.logic.flow.Navigate;
import com.github.saphyra.selenium.logic.flow.Registration;
import com.github.saphyra.selenium.logic.page.AccountPage;
import com.github.saphyra.selenium.logic.validator.FieldValidator;
import com.github.saphyra.selenium.test.account.changepassword.helper.ChangePasswordTestHelper;
import lombok.Builder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.github.saphyra.selenium.logic.util.LocalizationUtil.getAdditionalContent;
import static com.github.saphyra.skyxplore.userdata.user.domain.UserRegistrationRequest.PASSWORD_MAX_LENGTH;

@Builder
public class TooLongPasswordTest {
    private static final String TOO_LONG_PASSWORD;

    static {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i <= PASSWORD_MAX_LENGTH + 1; i++) {
            builder.append("a");
        }
        TOO_LONG_PASSWORD = builder.toString();
    }

    private static final String MESSAGE_CODE_PASSWORD_TOO_LONG = "password-too-long";

    private final WebDriver driver;
    private final ChangePasswordTestHelper changePasswordTestHelper;
    private final Registration registration;
    private final Navigate navigate;
    private final AccountPage accountPage;
    private final FieldValidator fieldValidator;

    public void testTooLongPassword() {
        changePasswordTestHelper.setUpWithCurrentPassword();

        WebElement newPasswordField = accountPage.getNewPasswordField();
        newPasswordField.sendKeys(TOO_LONG_PASSWORD);

        fieldValidator.verifyError(
            accountPage.getInvalidNewPasswordField(),
            getAdditionalContent(driver, Page.ACCOUNT, MESSAGE_CODE_PASSWORD_TOO_LONG),
            newPasswordField,
            accountPage.getChangePasswordButton(),
            accountPage.getInvalidNewConfirmPasswordField(),
            accountPage.getCurrentInvalidNewPasswordField()
        );
    }
}
