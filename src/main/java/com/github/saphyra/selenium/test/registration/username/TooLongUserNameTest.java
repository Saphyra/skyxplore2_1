package com.github.saphyra.selenium.test.registration.username;

import com.github.saphyra.selenium.logic.domain.SeleniumUser;
import com.github.saphyra.selenium.logic.domain.localization.Page;
import com.github.saphyra.selenium.logic.page.IndexPage;
import com.github.saphyra.selenium.logic.validator.FieldValidator;
import com.github.saphyra.selenium.test.registration.username.helper.UserNameTestHelper;
import com.github.saphyra.skyxplore.userdata.user.domain.UserRegistrationRequest;
import lombok.Builder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.github.saphyra.selenium.logic.util.LocalizationUtil.getAdditionalContent;

@Builder
public class TooLongUserNameTest {
    private static final String TOO_LONG_USER_NAME;

    static {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i <= UserRegistrationRequest.USER_NAME_MAX_LENGTH + 1; i++) {
            builder.append("a");
        }
        TOO_LONG_USER_NAME = builder.toString();
    }


    private static final String MESSAGE_CODE_TOO_LONG_USERNAME = "username-too-long";

    private final WebDriver driver;
    private final UserNameTestHelper userNameTestHelper;
    private final IndexPage indexPage;
    private final FieldValidator fieldValidator;

    public void testTooLongUserName() {
        SeleniumUser user = SeleniumUser.create();
        userNameTestHelper.setUpForUserNameTest(user);

        WebElement userNameField = indexPage.getRegistrationUserNameField();
        userNameField.sendKeys(TOO_LONG_USER_NAME);

        fieldValidator.verifyError(
            indexPage.getInvalidUserNameField(),
            getAdditionalContent(driver, Page.INDEX, MESSAGE_CODE_TOO_LONG_USERNAME),
            userNameField,
            indexPage.getRegisterButton()
        );
    }
}
