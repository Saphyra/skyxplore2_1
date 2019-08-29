package com.github.saphyra.selenium.test.registration.password;

import com.github.saphyra.selenium.logic.domain.SeleniumUser;
import com.github.saphyra.selenium.logic.domain.localization.Page;
import com.github.saphyra.selenium.logic.page.IndexPage;
import com.github.saphyra.selenium.logic.validator.FieldValidator;
import com.github.saphyra.selenium.test.registration.password.helper.PasswordTestHelper;
import com.github.saphyra.skyxplore.userdata.user.domain.UserRegistrationRequest;
import lombok.Builder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.github.saphyra.selenium.logic.util.LocalizationUtil.getAdditionalContent;
import static com.github.saphyra.selenium.logic.util.Util.crop;

@Builder
public class TooShortPasswordTest {
    private static final String MESSAGE_CODE_PASSWORD_TOO_SHORT = "password-too-short";

    private final WebDriver driver;
    private final PasswordTestHelper passwordTestHelper;
    private final IndexPage indexPage;
    private final FieldValidator fieldValidator;

    public void testTooShortPassword() {
        SeleniumUser user = SeleniumUser.create();
        passwordTestHelper.setUpForPasswordTest(user);

        WebElement passwordField = indexPage.getRegistrationPasswordField();
        passwordField.sendKeys(crop(user.getPassword(), UserRegistrationRequest.PASSWORD_MIN_LENGTH - 1));

        fieldValidator.verifyError(
            indexPage.getInvalidPasswordField(),
            getAdditionalContent(driver, Page.INDEX, MESSAGE_CODE_PASSWORD_TOO_SHORT),
            passwordField,
            indexPage.getRegisterButton()
        );
    }
}
