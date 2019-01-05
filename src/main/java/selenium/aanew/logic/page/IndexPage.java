package selenium.aanew.logic.page;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@RequiredArgsConstructor
public class IndexPage {
    private static final String ELEMENT_LOGIN_USER_NAME = "login_username";
    private static final String ELEMENT_LOGIN_PASSWORD = "login_password";

    private static final String ELEMENT_REGISTRATION_USER_NAME = "registration_username";
    private static final String ELEMENT_INVALID_USER_NAME = "invalid_username";

    private static final String ELEMENT_REGISTRATION_PASSWORD = "registration_password";
    private static final String ELEMENT_INVALID_PASSWORD = "invalid_password";

    private static final String ELEMENT_REGISTRATION_CONFIRM_PASSWORD = "registration_confirm_password";
    private static final String ELEMENT_INVALID_CONFIRM_PASSWORD = "invalid_confirm_password";

    private static final String ELEMENT_REGISTRATION_EMAIL = "registration_email";
    private static final String ELEMENT_INVALID_EMAIL = "invalid_email";

    private static final String ELEMENT_REGISTRATION_BUTTON = "registration_button";
    private static final String SELECTOR_LOGIN_BUTTON = "login_button";

    private final WebDriver driver;

    public WebElement getLoginUserNameField() {
        return driver.findElement(By.id(ELEMENT_LOGIN_USER_NAME));
    }

    public WebElement getLoginPasswordField() {
        return driver.findElement(By.id(ELEMENT_LOGIN_PASSWORD));
    }

    public WebElement getLoginButton() {
        return driver.findElement(By.id(SELECTOR_LOGIN_BUTTON));
    }

    public WebElement getRegistrationUserNameField() {
        return driver.findElement(By.id(ELEMENT_REGISTRATION_USER_NAME));
    }

    public WebElement getInvalidUserNameField() {
        return driver.findElement(By.id(ELEMENT_INVALID_USER_NAME));
    }

    public WebElement getRegistrationPasswordField() {
        return driver.findElement(By.id(ELEMENT_REGISTRATION_PASSWORD));
    }

    public WebElement getInvalidPasswordField() {
        return driver.findElement(By.id(ELEMENT_INVALID_PASSWORD));
    }

    public WebElement getRegistrationConfirmPasswordField() {
        return driver.findElement(By.id(ELEMENT_REGISTRATION_CONFIRM_PASSWORD));
    }

    public WebElement getInvalidConfirmPasswordField() {
        return driver.findElement(By.id(ELEMENT_INVALID_CONFIRM_PASSWORD));
    }

    public WebElement getRegistrationEmailField() {
        return driver.findElement(By.id(ELEMENT_REGISTRATION_EMAIL));
    }

    public WebElement getInvalidEmailField() {
        return driver.findElement(By.id(ELEMENT_INVALID_EMAIL));
    }

    public WebElement getRegisterButton() {
        return driver.findElement(By.id(ELEMENT_REGISTRATION_BUTTON));
    }
}
