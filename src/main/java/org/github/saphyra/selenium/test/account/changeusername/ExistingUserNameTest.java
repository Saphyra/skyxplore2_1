package org.github.saphyra.selenium.test.account.changeusername;

import lombok.Builder;
import org.openqa.selenium.WebElement;
import org.github.saphyra.selenium.logic.domain.localization.MessageCodes;
import org.github.saphyra.selenium.logic.domain.SeleniumUser;
import org.github.saphyra.selenium.logic.flow.Registration;
import org.github.saphyra.selenium.logic.page.AccountPage;
import org.github.saphyra.selenium.logic.validator.FieldValidator;
import org.github.saphyra.selenium.test.account.changeusername.helper.ChangeUserNameTestHelper;

@Builder
public class ExistingUserNameTest {
    private static final String MESSAGE_CODE_USERNAME_ALREADY_EXISTS = "USERNAME_ALREADY_EXISTS";

    private final Registration registration;
    private final ChangeUserNameTestHelper changeUserNameTestHelper;
    private final AccountPage accountPage;
    private final FieldValidator fieldValidator;
    private final MessageCodes messageCodes;

    public void testExistingUserName() {
        SeleniumUser otherUser = registration.registerUser();
        changeUserNameTestHelper.setUpWithCurrentPassword();

        WebElement userNameField = accountPage.getChangeUserNameField();
        userNameField.sendKeys(otherUser.getUserName());

        fieldValidator.verifyError(
            accountPage.getInvalidChangeUserNameField(),
            messageCodes.get(MESSAGE_CODE_USERNAME_ALREADY_EXISTS),
            userNameField,
            accountPage.getChangeUserNameButton()
        );
    }
}
