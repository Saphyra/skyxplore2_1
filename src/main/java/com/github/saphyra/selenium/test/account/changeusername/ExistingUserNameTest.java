package com.github.saphyra.selenium.test.account.changeusername;

import static com.github.saphyra.selenium.logic.util.LocalizationUtil.getAdditionalContent;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.saphyra.selenium.logic.domain.SeleniumUser;
import com.github.saphyra.selenium.logic.domain.localization.Page;
import com.github.saphyra.selenium.logic.flow.Registration;
import com.github.saphyra.selenium.logic.page.AccountPage;
import com.github.saphyra.selenium.logic.validator.FieldValidator;
import com.github.saphyra.selenium.test.account.changeusername.helper.ChangeUserNameTestHelper;
import lombok.Builder;

@Builder
public class ExistingUserNameTest {
    private static final String MESSAGE_CODE_USERNAME_ALREADY_EXISTS = "username-already-exists";

    private final WebDriver driver;
    private final Registration registration;
    private final ChangeUserNameTestHelper changeUserNameTestHelper;
    private final AccountPage accountPage;
    private final FieldValidator fieldValidator;

    public void testExistingUserName() {
        SeleniumUser otherUser = registration.registerUser();
        changeUserNameTestHelper.setUpWithCurrentPassword();

        WebElement userNameField = accountPage.getChangeUserNameField();
        userNameField.sendKeys(otherUser.getUserName());

        fieldValidator.verifyError(
            accountPage.getInvalidChangeUserNameField(),
            getAdditionalContent(driver, Page.ACCOUNT, MESSAGE_CODE_USERNAME_ALREADY_EXISTS),
            userNameField,
            accountPage.getChangeUserNameButton()
        );
    }
}
