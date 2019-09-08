package com.github.saphyra.selenium.test.account.changeusername;

import com.github.saphyra.selenium.logic.domain.SeleniumUser;
import com.github.saphyra.selenium.logic.domain.localization.Page;
import com.github.saphyra.selenium.logic.page.AccountPage;
import com.github.saphyra.selenium.logic.validator.FieldValidator;
import com.github.saphyra.selenium.test.account.changeusername.helper.ChangeUserNameTestHelper;
import lombok.Builder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.github.saphyra.selenium.logic.util.LocalizationUtil.getAdditionalContent;
import static com.github.saphyra.selenium.logic.util.Util.crop;
import static com.github.saphyra.skyxplore.userdata.user.domain.UserRegistrationRequest.USER_NAME_MIN_LENGTH;

@Builder
public class TooShortUserNameTest {
    private static final String MESSAGE_CODE_USERNAME_TOO_SHORT = "username-too-short";

    private final WebDriver driver;
    private final ChangeUserNameTestHelper changeUserNameTestHelper;
    private final AccountPage accountPage;
    private final FieldValidator fieldValidator;

    public void testTooShortUserName() {
        changeUserNameTestHelper.setUpWithCurrentPassword();

        WebElement userNameField = accountPage.getChangeUserNameField();
        userNameField.sendKeys(crop(SeleniumUser.createRandomUserName(), USER_NAME_MIN_LENGTH - 1));

        fieldValidator.verifyError(
            accountPage.getInvalidChangeUserNameField(),
            getAdditionalContent(driver, Page.ACCOUNT, MESSAGE_CODE_USERNAME_TOO_SHORT),
            userNameField,
            accountPage.getChangeUserNameButton()
        );
    }
}
