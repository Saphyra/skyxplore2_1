package com.github.saphyra.selenium.test.registration.username;

import org.openqa.selenium.WebElement;

import com.github.saphyra.selenium.logic.domain.SeleniumUser;
import com.github.saphyra.selenium.logic.validator.FieldValidator;
import lombok.Builder;
import com.github.saphyra.selenium.logic.flow.Logout;
import com.github.saphyra.selenium.logic.flow.Registration;
import com.github.saphyra.selenium.logic.page.IndexPage;
import com.github.saphyra.selenium.test.registration.username.helper.UserNameTestHelper;

@Builder
public class ExistingUserNameTest {
    private static final String MESSAGE_CODE_USERNAME_EXISTS = "USERNAME_ALREADY_EXISTS";

    private final UserNameTestHelper userNameTestHelper;
    private final Registration registration;
    private final Logout logout;
    private final IndexPage indexPage;
    private final FieldValidator fieldValidator;
    private final MessageCodes messageCodes;

    public void testExistingUserName() {
        SeleniumUser otherUser = registration.registerUser();
        logout.logOut();

        SeleniumUser user = SeleniumUser.create();
        userNameTestHelper.setUpForUserNameTest(user);

        WebElement userNameField = indexPage.getRegistrationUserNameField();
        userNameField.sendKeys(otherUser.getUserName());

        fieldValidator.verifyError(
            indexPage.getInvalidUserNameField(),
            messageCodes.get(MESSAGE_CODE_USERNAME_EXISTS),
            userNameField,
            indexPage.getRegisterButton()
        );
    }
}
