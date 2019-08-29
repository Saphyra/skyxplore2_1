package com.github.saphyra.selenium.test.account.changepassword;

import com.github.saphyra.selenium.logic.domain.SeleniumUser;
import com.github.saphyra.selenium.logic.domain.localization.Page;
import com.github.saphyra.selenium.logic.page.AccountPage;
import com.github.saphyra.selenium.logic.validator.NotificationValidator;
import com.github.saphyra.selenium.test.account.changepassword.helper.ChangePasswordTestHelper;
import lombok.Builder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.github.saphyra.selenium.logic.util.LocalizationUtil.getAdditionalContent;

@Builder
public class WrongPasswordTest {
    private static final String MESSAGE_CODE_BAD_PASSWORD = "wrong-password";

    private final WebDriver driver;
    private final ChangePasswordTestHelper changePasswordTestHelper;
    private final AccountPage accountPage;
    private final NotificationValidator notificationValidator;

    public void testBadPassword() {
        changePasswordTestHelper.registerAndNavigateToAccount();

        String newPassword = SeleniumUser.createRandomPassword();

        WebElement newPasswordField = accountPage.getNewPasswordField();
        newPasswordField.sendKeys(newPassword);

        WebElement confirmPasswordField = accountPage.getNewConfirmPasswordField();
        confirmPasswordField.sendKeys(newPassword);

        WebElement currentPasswordField = accountPage.getCurrentNewPasswordField();
        currentPasswordField.sendKeys(SeleniumUser.createRandomPassword());

        changePasswordTestHelper.sendForm();

        notificationValidator.verifyOnlyOneNotification(getAdditionalContent(driver, Page.ACCOUNT, MESSAGE_CODE_BAD_PASSWORD));

        changePasswordTestHelper.verifyInputFieldsAreEmpty();
    }
}
