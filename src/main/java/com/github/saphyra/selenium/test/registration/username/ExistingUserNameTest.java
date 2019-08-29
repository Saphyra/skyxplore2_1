package com.github.saphyra.selenium.test.registration.username;

import com.github.saphyra.selenium.logic.domain.SeleniumUser;
import com.github.saphyra.selenium.logic.domain.localization.Page;
import com.github.saphyra.selenium.logic.flow.Logout;
import com.github.saphyra.selenium.logic.flow.Registration;
import com.github.saphyra.selenium.logic.page.IndexPage;
import com.github.saphyra.selenium.logic.validator.FieldValidator;
import com.github.saphyra.selenium.test.registration.username.helper.UserNameTestHelper;
import lombok.Builder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.github.saphyra.selenium.logic.util.LocalizationUtil.getAdditionalContent;

@Builder
public class ExistingUserNameTest {
    private static final String MESSAGE_CODE_USERNAME_EXISTS = "username-already-exists";

    private final WebDriver driver;
    private final UserNameTestHelper userNameTestHelper;
    private final Registration registration;
    private final Logout logout;
    private final IndexPage indexPage;
    private final FieldValidator fieldValidator;

    public void testExistingUserName() {
        SeleniumUser otherUser = registration.registerUser();
        logout.logOut();

        SeleniumUser user = SeleniumUser.create();
        userNameTestHelper.setUpForUserNameTest(user);

        WebElement userNameField = indexPage.getRegistrationUserNameField();
        userNameField.sendKeys(otherUser.getUserName());

        fieldValidator.verifyError(
            indexPage.getInvalidUserNameField(),
            getAdditionalContent(driver, Page.INDEX, MESSAGE_CODE_USERNAME_EXISTS),
            userNameField,
            indexPage.getRegisterButton()
        );
    }
}
