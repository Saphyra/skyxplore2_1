package org.github.saphyra.selenium.test.registration.username;

import static org.github.saphyra.selenium.logic.util.Util.crop;
import static org.github.saphyra.skyxplore.user.domain.UserRegistrationRequest.USER_NAME_MIN_LENGTH;

import org.openqa.selenium.WebElement;

import lombok.Builder;
import org.github.saphyra.selenium.logic.domain.localization.MessageCodes;
import org.github.saphyra.selenium.logic.domain.SeleniumUser;
import org.github.saphyra.selenium.logic.page.IndexPage;
import org.github.saphyra.selenium.logic.validator.FieldValidator;
import org.github.saphyra.selenium.test.registration.username.helper.UserNameTestHelper;

@Builder
public class TooShortUserNameTest {
    private static final String MESSAGE_CODE_TOO_SHORT_USERNAME = "USERNAME_TOO_SHORT";

    private final UserNameTestHelper userNameTestHelper;
    private final IndexPage indexPage;
    private final FieldValidator fieldValidator;
    private final MessageCodes messageCodes;

    public void testTooShortUserName() {
        SeleniumUser user = SeleniumUser.create();
        userNameTestHelper.setUpForUserNameTest(user);

        WebElement userNameField = indexPage.getRegistrationUserNameField();
        userNameField.sendKeys(crop(user.getUserName(), USER_NAME_MIN_LENGTH - 1));

        fieldValidator.verifyError(
            indexPage.getInvalidUserNameField(),
            messageCodes.get(MESSAGE_CODE_TOO_SHORT_USERNAME),
            userNameField,
            indexPage.getRegisterButton()
        );

    }
}
