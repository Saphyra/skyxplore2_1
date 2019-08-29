package com.github.saphyra.selenium.test.account.deleteaccount;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import lombok.Builder;
import com.github.saphyra.selenium.logic.page.AccountPage;
import com.github.saphyra.selenium.logic.validator.FieldValidator;
import com.github.saphyra.selenium.test.account.deleteaccount.helper.DeleteAccountTestHelper;

@Builder
public class EmptyPasswordTest {
    private static final String MESSAGE_CODE_EMPTY_PASSWORD = "empty-password";

    private  final WebDriver driver;
    private final DeleteAccountTestHelper deleteAccountTestHelper;
    private final AccountPage accountPage;
    private final FieldValidator fieldValidator;

    public void testEmptyPassword() {
        deleteAccountTestHelper.registerAndNavigateToAccount();

        WebElement passwordField = accountPage.getDeleteAccountPasswordField();
        passwordField.sendKeys();

        fieldValidator.verifyError(
            accountPage.getInvalidDeleteAccountPasswordField(),
            getAdditionalContent(driver, Page.ACCOUNT, MESSAGE_CODE_EMPTY_PASSWORD),
            passwordField,
            accountPage.getDeleteAccountButton()
        );
    }
}
