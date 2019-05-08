package com.github.saphyra.selenium.test.account.changeusername;

import com.github.saphyra.selenium.test.account.changeusername.helper.ChangeUserNameTestHelper;
import lombok.Builder;
import org.openqa.selenium.WebElement;
import com.github.saphyra.selenium.logic.domain.localization.MessageCodes;
import com.github.saphyra.selenium.logic.domain.SeleniumUser;
import com.github.saphyra.selenium.logic.page.AccountPage;
import com.github.saphyra.selenium.logic.validator.FieldValidator;

import static com.github.saphyra.selenium.logic.util.Util.crop;
import static com.github.saphyra.skyxplore.user.domain.UserRegistrationRequest.USER_NAME_MIN_LENGTH;

@Builder
public class TooShortUserNameTest {
    private static final String MESSAGE_CODE_USERNAME_TOO_SHORT = "USERNAME_TOO_SHORT";

    private final ChangeUserNameTestHelper changeUserNameTestHelper;
    private final AccountPage accountPage;
    private final FieldValidator fieldValidator;
    private final MessageCodes messageCodes;

    public void testTooShortUserName() {
        changeUserNameTestHelper.setUpWithCurrentPassword();

        WebElement userNameField = accountPage.getChangeUserNameField();
        userNameField.sendKeys(crop(SeleniumUser.createRandomUserName(), USER_NAME_MIN_LENGTH - 1));

        fieldValidator.verifyError(
            accountPage.getInvalidChangeUserNameField(),
            messageCodes.get(MESSAGE_CODE_USERNAME_TOO_SHORT),
            userNameField,
            accountPage.getChangeUserNameButton()
        );
    }
}
