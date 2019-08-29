package com.github.saphyra.selenium.test.account.changepassword;

import com.github.saphyra.selenium.logic.domain.SeleniumUser;
import com.github.saphyra.selenium.logic.domain.localization.Page;
import com.github.saphyra.selenium.logic.flow.Navigate;
import com.github.saphyra.selenium.logic.page.AccountPage;
import com.github.saphyra.selenium.logic.validator.FieldValidator;
import com.github.saphyra.selenium.test.account.changepassword.helper.ChangePasswordTestHelper;
import lombok.Builder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.github.saphyra.selenium.logic.util.LocalizationUtil.getAdditionalContent;
import static com.github.saphyra.selenium.logic.util.Util.crop;
import static com.github.saphyra.skyxplore.userdata.user.domain.UserRegistrationRequest.PASSWORD_MIN_LENGTH;

@Builder
public class TooShortPasswordTest {
    private static final String MESSAGE_CODE_PASSWORD_TOO_SHORT = "new-password-too-short";

    private final WebDriver driver;
    private final AccountPage accountPage;
    private final FieldValidator fieldValidator;
    private final Navigate navigate;
    private final ChangePasswordTestHelper changePasswordTestHelper;

    public void testTooShortPassword() {
        SeleniumUser user = changePasswordTestHelper.setUpWithCurrentPassword();

        WebElement newPasswordField = accountPage.getNewPasswordField();

        newPasswordField.sendKeys(crop(user.getPassword(), PASSWORD_MIN_LENGTH - 1));

        fieldValidator.verifyError(
            accountPage.getInvalidNewPasswordField(),
            getAdditionalContent(driver, Page.ACCOUNT, MESSAGE_CODE_PASSWORD_TOO_SHORT),
            newPasswordField,
            accountPage.getChangePasswordButton(),
            accountPage.getInvalidNewConfirmPasswordField(),
            accountPage.getCurrentInvalidNewPasswordField()
        );
    }
}
