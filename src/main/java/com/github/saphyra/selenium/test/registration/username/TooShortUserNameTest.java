package com.github.saphyra.selenium.test.registration.username;

import static com.github.saphyra.selenium.logic.util.Util.crop;

import org.openqa.selenium.WebElement;

import com.github.saphyra.selenium.logic.domain.SeleniumUser;
import com.github.saphyra.selenium.logic.domain.localization.MessageCodes;
import com.github.saphyra.selenium.logic.validator.FieldValidator;
import com.github.saphyra.skyxplore.userdata.user.domain.UserRegistrationRequest;
import lombok.Builder;
import com.github.saphyra.selenium.logic.page.IndexPage;
import com.github.saphyra.selenium.test.registration.username.helper.UserNameTestHelper;

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
        userNameField.sendKeys(crop(user.getUserName(), UserRegistrationRequest.USER_NAME_MIN_LENGTH - 1));

        fieldValidator.verifyError(
            indexPage.getInvalidUserNameField(),
            messageCodes.get(MESSAGE_CODE_TOO_SHORT_USERNAME),
            userNameField,
            indexPage.getRegisterButton()
        );

    }
}
