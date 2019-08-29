package com.github.saphyra.selenium.test.account.changeusername;

import com.github.saphyra.selenium.logic.domain.localization.Page;
import com.github.saphyra.selenium.logic.page.AccountPage;
import com.github.saphyra.selenium.logic.validator.FieldValidator;
import com.github.saphyra.selenium.test.account.changeusername.helper.ChangeUserNameTestHelper;
import lombok.Builder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.github.saphyra.selenium.logic.util.LocalizationUtil.getAdditionalContent;
import static com.github.saphyra.skyxplore.userdata.user.domain.UserRegistrationRequest.USER_NAME_MAX_LENGTH;

@Builder
public class TooLongUserNameTest {
    private static final String TOO_LONG_USER_NAME;

    static {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i <= USER_NAME_MAX_LENGTH + 1; i++) {
            builder.append("a");
        }
        TOO_LONG_USER_NAME = builder.toString();
    }

    private static final String MESSAGE_CODE_USERNAME_TOO_LONG = "username-too-long";

    private final WebDriver driver;
    private final AccountPage accountPage;
    private final ChangeUserNameTestHelper changeUserNameTestHelper;
    private final FieldValidator fieldValidator;

    public void testTooLongUserName() {
        changeUserNameTestHelper.setUpWithCurrentPassword();

        WebElement userNameField = accountPage.getChangeUserNameField();
        userNameField.sendKeys(TOO_LONG_USER_NAME);

        fieldValidator.verifyError(
            accountPage.getInvalidChangeUserNameField(),
            getAdditionalContent(driver, Page.ACCOUNT, MESSAGE_CODE_USERNAME_TOO_LONG),
            userNameField,
            accountPage.getChangeUserNameButton()
        );
    }
}
