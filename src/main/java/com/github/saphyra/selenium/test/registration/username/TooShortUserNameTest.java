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
import static com.github.saphyra.selenium.logic.util.Util.crop;

@Builder
public class TooShortUserNameTest {
    private static final String MESSAGE_CODE_TOO_SHORT_USERNAME = "username-too-short";

    private final WebDriver driver;
    private final UserNameTestHelper userNameTestHelper;
    private final IndexPage indexPage;
    private final FieldValidator fieldValidator;

    public void testTooShortUserName() {
        SeleniumUser user = SeleniumUser.create();
        userNameTestHelper.setUpForUserNameTest(user);

        WebElement userNameField = indexPage.getRegistrationUserNameField();
        userNameField.sendKeys(crop(user.getUserName(), UserRegistrationRequest.USER_NAME_MIN_LENGTH - 1));

        fieldValidator.verifyError(
            indexPage.getInvalidUserNameField(),
            getAdditionalContent(driver, Page.INDEX, MESSAGE_CODE_TOO_SHORT_USERNAME),
            userNameField,
            indexPage.getRegisterButton()
        );
    }
}
