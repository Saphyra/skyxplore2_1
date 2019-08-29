package com.github.saphyra.selenium.test.account.changepassword;

import com.github.saphyra.selenium.logic.domain.SeleniumUser;
import com.github.saphyra.selenium.logic.domain.localization.Page;
import com.github.saphyra.selenium.logic.page.AccountPage;
import com.github.saphyra.selenium.logic.validator.FieldValidator;
import com.github.saphyra.selenium.test.account.changepassword.helper.ChangePasswordTestHelper;
import lombok.Builder;
import org.openqa.selenium.WebDriver;

import static com.github.saphyra.selenium.logic.util.LocalizationUtil.getAdditionalContent;

@Builder
public class EmptyCurrentPasswordTest {
    private static final String MESSAGE_CODE_EMPTY_PASSWORD = "empty-password";

    private final WebDriver driver;
    private final ChangePasswordTestHelper changePasswordTestHelper;
    private final AccountPage accountPage;
    private final FieldValidator fieldValidator;

    public void testEmptyCurrentPassword() {
        SeleniumUser user = changePasswordTestHelper.registerAndNavigateToAccount();

        accountPage.getNewPasswordField().sendKeys(user.getPassword());
        accountPage.getNewConfirmPasswordField().sendKeys(user.getPassword());

        fieldValidator.verifyError(
            accountPage.getCurrentInvalidNewPasswordField(),
            getAdditionalContent(driver, Page.ACCOUNT, MESSAGE_CODE_EMPTY_PASSWORD),
            accountPage.getCurrentNewPasswordField(),
            accountPage.getChangePasswordButton(),
            accountPage.getInvalidNewPasswordField(),
            accountPage.getInvalidNewConfirmPasswordField()
        );
    }
}
