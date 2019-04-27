package org.github.saphyra.selenium.test.registration.username;

import static skyxplore.controller.request.user.UserRegistrationRequest.USER_NAME_MAX_LENGTH;

import org.openqa.selenium.WebElement;

import lombok.Builder;
import org.github.saphyra.selenium.logic.domain.MessageCodes;
import org.github.saphyra.selenium.logic.domain.SeleniumUser;
import org.github.saphyra.selenium.logic.page.IndexPage;
import org.github.saphyra.selenium.logic.validator.FieldValidator;
import org.github.saphyra.selenium.test.registration.username.helper.UserNameTestHelper;

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


    private static final String MESSAGE_CODE_TOO_LONG_USERNAME = "USERNAME_TOO_LONG";

    private final UserNameTestHelper userNameTestHelper;
    private final IndexPage indexPage;
    private final FieldValidator fieldValidator;
    private final MessageCodes messageCodes;

    public void testTooLongUserName() {
        SeleniumUser user = SeleniumUser.create();
        userNameTestHelper.setUpForUserNameTest(user);

        WebElement userNameField = indexPage.getRegistrationUserNameField();
        userNameField.sendKeys(TOO_LONG_USER_NAME);

        fieldValidator.verifyError(
            indexPage.getInvalidUserNameField(),
            messageCodes.get(MESSAGE_CODE_TOO_LONG_USERNAME),
            userNameField,
            indexPage.getRegisterButton()
        );
    }
}
