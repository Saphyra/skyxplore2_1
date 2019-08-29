package com.github.saphyra.selenium.test.account.changepassword;

import com.github.saphyra.selenium.logic.domain.SeleniumUser;
import com.github.saphyra.selenium.logic.domain.localization.Page;
import com.github.saphyra.selenium.logic.page.AccountPage;
import com.github.saphyra.selenium.logic.validator.FieldValidator;
import com.github.saphyra.selenium.test.account.changepassword.helper.ChangePasswordTestHelper;
import lombok.Builder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.github.saphyra.selenium.logic.util.LocalizationUtil.getAdditionalContent;

@Builder
public class BadConfirmPasswordTest {
    private static final String MESSAGE_CODE_BAD_CONFIRM_PASSWORD = "bad-confirm-password";

    private final WebDriver driver;
    private final ChangePasswordTestHelper changePasswordTestHelper;
    private final AccountPage accountPage;
    private final FieldValidator fieldValidator;

    public void testBadConfirmPassword() {
        changePasswordTestHelper.setUpWithCurrentPassword();

        WebElement newPasswordField = accountPage.getNewPasswordField();
        newPasswordField.sendKeys(SeleniumUser.createRandomPassword());

        WebElement newConfirmPasswordField = accountPage.getNewConfirmPasswordField();
        newConfirmPasswordField.sendKeys(SeleniumUser.createRandomPassword());

        String errorMessage = getAdditionalContent(driver, Page.ACCOUNT, MESSAGE_CODE_BAD_CONFIRM_PASSWORD);
        fieldValidator.verifyError(
            accountPage.getInvalidNewPasswordField(),
            errorMessage,
            newPasswordField,
            accountPage.getChangePasswordButton(),
            accountPage.getCurrentInvalidNewPasswordField()
        );

        fieldValidator.verifyError(
            accountPage.getInvalidNewConfirmPasswordField(),
            errorMessage,
            newConfirmPasswordField,
            accountPage.getChangePasswordButton()
        );
    }
}
