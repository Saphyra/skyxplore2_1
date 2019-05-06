package org.github.saphyra.selenium.test.account.changeusername;

import org.openqa.selenium.WebElement;

import lombok.Builder;
import org.github.saphyra.selenium.logic.domain.localization.MessageCodes;
import org.github.saphyra.selenium.logic.page.AccountPage;
import org.github.saphyra.selenium.logic.validator.FieldValidator;
import org.github.saphyra.selenium.test.account.changeusername.helper.ChangeUserNameTestHelper;

@Builder
public class EmptyPasswordTest {
    private static final String MESSAGE_CODE_PASSWORD_IS_EMPTY = "CURRENT_PASSWORD_IS_EMPTY";

    private final ChangeUserNameTestHelper changeUserNameTestHelper;
    private final AccountPage accountPage;
    private final FieldValidator fieldValidator;
    private final MessageCodes messageCodes;

    public void testEmptyPassword() {
        changeUserNameTestHelper.setUpWithRandomUserName();

        WebElement changePasswordField = accountPage.getChangeUserNamePasswordField();

        fieldValidator.verifyError(
            accountPage.getInvalidChangeUserNamePasswordField(),
            messageCodes.get(MESSAGE_CODE_PASSWORD_IS_EMPTY),
            changePasswordField,
            accountPage.getChangeUserNameButton()
        );
    }
}
