package org.github.saphyra.selenium.test.account.changeusername;

import lombok.Builder;
import org.openqa.selenium.WebElement;
import org.github.saphyra.selenium.logic.domain.MessageCodes;
import org.github.saphyra.selenium.logic.domain.SeleniumUser;
import org.github.saphyra.selenium.logic.page.AccountPage;
import org.github.saphyra.selenium.logic.validator.FieldValidator;
import org.github.saphyra.selenium.test.account.changeusername.helper.ChangeUserNameTestHelper;

import static org.github.saphyra.selenium.logic.util.Util.crop;
import static skyxplore.controller.request.user.UserRegistrationRequest.USER_NAME_MIN_LENGTH;

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
