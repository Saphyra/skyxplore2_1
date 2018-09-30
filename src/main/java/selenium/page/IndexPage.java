package selenium.page;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@RequiredArgsConstructor
public class IndexPage {
    public static final String ELEMENT_REGISTRATION_USER_NAME = "registration_username";
    public static final String ELEMENT_REGISTRATION_PASSWORD = "registration_password";
    public static final String ELEMENT_REGISTRATION_CONFIRM_PASSWORD = "registration_confirm_password";
    public static final String ELEMENT_REGISTRATION_EMAIL = "registration_email";
    public static final String ELEMENT_REEGISTRATION_BUTTON = "registration_button";

    private final WebDriver driver;

    public WebElement getRegistrationUserNameField(){
        return driver.findElement(By.id(ELEMENT_REGISTRATION_USER_NAME));
    }

    public WebElement getRegistrationPasswordField() {
        return driver.findElement(By.id(ELEMENT_REGISTRATION_PASSWORD));
    }

    public WebElement getRegistrationConfirmPasswordField() {
        return driver.findElement(By.id(ELEMENT_REGISTRATION_CONFIRM_PASSWORD));
    }

    public WebElement getRegistrationEmailField() {
        return driver.findElement(By.id(ELEMENT_REGISTRATION_EMAIL));
    }

    public WebElement getRegisterButton() {
        return driver.findElement(By.id(ELEMENT_REEGISTRATION_BUTTON));
    }
}
