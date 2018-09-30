package selenium.cases;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import selenium.domain.SeleniumUser;
import selenium.flow.Logout;
import selenium.flow.Registration;
import selenium.page.IndexPage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static selenium.util.LinkUtil.HOST;
import static selenium.util.StringUtil.crop;
import static skyxplore.controller.request.user.UserRegistrationRequest.*;

public class RegistrationEdgeCasesTest {
    private static final String TOO_LONG_USER_NAME;
    static {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i <= USER_NAME_MAX_LENGTH + 1; i++) {
            builder.append("a");
        }
        TOO_LONG_USER_NAME = builder.toString();
    }

    private static final String TOO_LONG_PASSWORD;
    static {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i <= PASSWORD_MAX_LENGTH + 1; i++) {
            builder.append("a");
        }
        TOO_LONG_PASSWORD = builder.toString();
    }

    private static final String INVALID_EMAIL = "abcc";

    private static final String ERROR_MESSAGE_TOO_SHORT_USER_NAME = "Felhasználónév túl rövid (Minimum 3 karakter).";
    private static final String ERROR_MESSAGE_TOO_LONG_USER_NAME = "Felhasználónév túl hosszú (Maximum 30 karakter).";
    private static final String ERROR_MESSAGE_EXISTING_USER_NAME = "Felhasználónév foglalt.";

    private static final String ERROR_MESSAGE_TOO_SHORT_PASSWORD = "Jelszó túl rövid (Minimum 6 karakter).";
    private static final String ERROR_MESSAGE_TOO_LONG_PASSWORD = "Jelszó túl hosszú (Maximum 30 karakter).";
    private static final String ERROR_MESSAGE_BAD_CONFIRM_PASSWORD = "A jelszavak nem egyeznek.";

    private static final String ERROR_MESSAGE_INVALID_EMAIL = "Érvénytelen e-mail cím.";
    private static final String ERROR_MESSAGE_EXISTING_EMAIL = "Már van regisztrált felhasználó a megadott e-mail címmel.";

    private final WebDriver driver;
    private final IndexPage indexPage;
    private final SeleniumUser currentUser;
    private final SeleniumUser newUser = SeleniumUser.create();

    private RegistrationEdgeCasesTest(WebDriver driver, SeleniumUser user) {
        this.driver = driver;
        this.currentUser = user;
        this.indexPage = new IndexPage(driver);
    }

    public static void run(WebDriver driver) {
        SeleniumUser currentUser = new Registration(driver).registerUser();
        new Logout(driver).logOut();

        RegistrationEdgeCasesTest testCase = new RegistrationEdgeCasesTest(driver, currentUser);
        testCase.validateUserName();
        testCase.validatePasswords();
        testCase.validateEmail();
    }

    private void cleanUp() {
        indexPage.getRegistrationUserNameField().clear();
        indexPage.getRegistrationPasswordField().clear();
        indexPage.getRegistrationConfirmPasswordField().clear();
        indexPage.getRegistrationEmailField().clear();
    }

    //==========USER NAME==============
    private void validateUserName() {
        validateTooShortUserName();
        validateTooLongUserName();
        validateExistingUserName();
    }

    private void validateTooShortUserName() {
        setUpForUserNameValidation();

        WebElement userNameField = indexPage.getRegistrationUserNameField();
        userNameField.sendKeys(crop(newUser.getUserName(), USER_NAME_MIN_LENGTH - 1));

        verifyRegistrationError(
            indexPage.getInvalidUserNameField(),
            ERROR_MESSAGE_TOO_SHORT_USER_NAME,
            userNameField
        );
    }

    private void validateTooLongUserName() {
        setUpForUserNameValidation();

        WebElement userNameField = indexPage.getRegistrationUserNameField();
        userNameField.sendKeys(TOO_LONG_USER_NAME);

        verifyRegistrationError(
            indexPage.getInvalidUserNameField(),
            ERROR_MESSAGE_TOO_LONG_USER_NAME,
            userNameField
        );
    }

    private void validateExistingUserName() {
        setUpForUserNameValidation();

        WebElement userNameField = indexPage.getRegistrationUserNameField();
        userNameField.sendKeys(currentUser.getUserName());

        verifyRegistrationError(
            indexPage.getInvalidUserNameField(),
            ERROR_MESSAGE_EXISTING_USER_NAME,
            userNameField
        );
    }

    private void setUpForUserNameValidation() {
        cleanUp();

        WebElement passwordElement = indexPage.getRegistrationPasswordField();
        passwordElement.sendKeys(newUser.getPassword());

        WebElement confirmPasswordElement = indexPage.getRegistrationConfirmPasswordField();
        confirmPasswordElement.sendKeys(newUser.getPassword());

        WebElement emailElement = indexPage.getRegistrationEmailField();
        emailElement.sendKeys(newUser.getEmail());
    }

    //==========PASSWORD==============
    private void validatePasswords() {
        validateTooShortPassword();
        validateTooLongPassword();
        validateConfirmPassword();
    }

    private void validateTooShortPassword() {
        setUpForPasswordValidation();

        WebElement passwordField = indexPage.getRegistrationPasswordField();
        passwordField.sendKeys(crop(newUser.getPassword(), PASSWORD_MIN_LENGTH - 1));

        verifyRegistrationError(
            indexPage.getInvalidPasswordField(),
            ERROR_MESSAGE_TOO_SHORT_PASSWORD,
            passwordField
        );
    }

    private void validateTooLongPassword() {
        setUpForPasswordValidation();

        WebElement passwordField = indexPage.getRegistrationPasswordField();
        passwordField.sendKeys(TOO_LONG_PASSWORD);

        verifyRegistrationError(
            indexPage.getInvalidPasswordField(),
            ERROR_MESSAGE_TOO_LONG_PASSWORD,
            passwordField
        );
    }

    private void validateConfirmPassword() {
        setUpForPasswordValidation();

        WebElement passwordField = indexPage.getRegistrationPasswordField();
        passwordField.sendKeys(newUser.getPassword());

        WebElement confirmPasswordField = indexPage.getRegistrationConfirmPasswordField();
        confirmPasswordField.sendKeys(currentUser.getPassword());

        verifyRegistrationError(
            indexPage.getInvalidPasswordField(),
            ERROR_MESSAGE_BAD_CONFIRM_PASSWORD,
            passwordField
        );

        verifyRegistrationError(
            indexPage.getInvalidConfirmPasswordField(),
            ERROR_MESSAGE_BAD_CONFIRM_PASSWORD,
            confirmPasswordField
        );
    }

    private void setUpForPasswordValidation() {
        cleanUp();

        WebElement userNameField = indexPage.getRegistrationUserNameField();
        userNameField.sendKeys(newUser.getUserName());

        WebElement emailElement = indexPage.getRegistrationEmailField();
        emailElement.sendKeys(newUser.getEmail());
    }

    //============EMAIL===============
    private void validateEmail(){
        validateInvalidEmail();
        validateExistingEmail();
    }

    private void validateInvalidEmail() {
        setUpForEmailValidation();

        WebElement emailField = indexPage.getRegistrationEmailField();
        emailField.sendKeys(INVALID_EMAIL);

        verifyRegistrationError(
            indexPage.getInvalidEmailField(),
            ERROR_MESSAGE_INVALID_EMAIL,
            emailField
        );
    }

    private void validateExistingEmail() {
        setUpForEmailValidation();

        WebElement emailField = indexPage.getRegistrationEmailField();
        emailField.sendKeys(currentUser.getEmail());

        verifyRegistrationError(
            indexPage.getInvalidEmailField(),
            ERROR_MESSAGE_EXISTING_EMAIL,
            emailField
        );
    }

    private void setUpForEmailValidation() {
        cleanUp();

        WebElement userNameField = indexPage.getRegistrationUserNameField();
        userNameField.sendKeys(newUser.getUserName());

        WebElement passwordElement = indexPage.getRegistrationPasswordField();
        passwordElement.sendKeys(newUser.getPassword());

        WebElement confirmPasswordElement = indexPage.getRegistrationConfirmPasswordField();
        confirmPasswordElement.sendKeys(newUser.getPassword());
    }

    //========= VERIFICATION==========
    private void verifyRegistrationError(WebElement errorField, String errorMessage, WebElement target) {
        assertEquals(errorMessage, errorField.getAttribute("title"));
        verifyRegistrationNotPossible(target);
    }

    private void verifyRegistrationNotPossible(WebElement target) {
        verifyRegistrationButtonDisabled();
        target.sendKeys(Keys.ENTER);
        assertEquals(HOST, driver.getCurrentUrl());
    }

    private void verifyRegistrationButtonDisabled() {
        WebElement registrationButton = indexPage.getRegisterButton();
        assertFalse(registrationButton.isEnabled());
    }
}
