package com.github.saphyra.selenium.test.account.changeusername;

import com.github.saphyra.selenium.logic.domain.localization.Page;
import com.github.saphyra.selenium.logic.page.AccountPage;
import com.github.saphyra.selenium.logic.validator.FieldValidator;
import com.github.saphyra.selenium.test.account.changeusername.helper.ChangeUserNameTestHelper;
import lombok.Builder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.github.saphyra.selenium.logic.util.LocalizationUtil.getAdditionalContent;

@Builder
public class EmptyPasswordTest {
    private static final String MESSAGE_CODE_EMPTY_PASSWORD = "empty-password";

    private final WebDriver driver;
    private final ChangeUserNameTestHelper changeUserNameTestHelper;
    private final AccountPage accountPage;
    private final FieldValidator fieldValidator;

    public void testEmptyPassword() {
        changeUserNameTestHelper.setUpWithRandomUserName();

        WebElement changePasswordField = accountPage.getChangeUserNamePasswordField();

        fieldValidator.verifyError(
            accountPage.getInvalidChangeUserNamePasswordField(),
            getAdditionalContent(driver, Page.ACCOUNT, MESSAGE_CODE_EMPTY_PASSWORD),
            changePasswordField,
            accountPage.getChangeUserNameButton()
        );
    }
}
