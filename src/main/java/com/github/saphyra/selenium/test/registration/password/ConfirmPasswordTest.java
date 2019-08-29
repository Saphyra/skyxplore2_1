package com.github.saphyra.selenium.test.registration.password;

import com.github.saphyra.selenium.logic.domain.SeleniumUser;
import com.github.saphyra.selenium.logic.domain.localization.Page;
import com.github.saphyra.selenium.logic.page.IndexPage;
import com.github.saphyra.selenium.logic.validator.FieldValidator;
import com.github.saphyra.selenium.test.registration.password.helper.PasswordTestHelper;
import lombok.Builder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.github.saphyra.selenium.logic.util.LocalizationUtil.getAdditionalContent;

@Builder
public class ConfirmPasswordTest {
    private static final String MESSAGE_CODE_INCORRECT_CONFIRM_PASSWORD = "incorrect-confirm-password";

    private final WebDriver driver;
    private final PasswordTestHelper passwordTestHelper;
    private final IndexPage indexPage;
    private final FieldValidator fieldValidator;

    public void testConfirmPassword() {
        SeleniumUser user = SeleniumUser.create();
        passwordTestHelper.setUpForPasswordTest(user);

        WebElement passwordField = indexPage.getRegistrationPasswordField();
        passwordField.sendKeys(user.getPassword());

        WebElement confirmPasswordField = indexPage.getRegistrationConfirmPasswordField();
        confirmPasswordField.sendKeys(SeleniumUser.createRandomPassword());

        String errorMessage = getAdditionalContent(driver, Page.INDEX, MESSAGE_CODE_INCORRECT_CONFIRM_PASSWORD);
        fieldValidator.verifyError(
            indexPage.getInvalidPasswordField(),
            errorMessage,
            passwordField,
            indexPage.getRegisterButton()
        );

        fieldValidator.verifyError(
            indexPage.getInvalidConfirmPasswordField(),
            errorMessage,
            confirmPasswordField,
            indexPage.getRegisterButton()
        );
    }
}
