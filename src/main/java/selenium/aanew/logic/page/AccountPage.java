package selenium.page;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@RequiredArgsConstructor
public class AccountPage {
    private static final String ELEMENT_NEW_PASSWORD_FIELD = "newpassword1";
    private static final String ELEMENT_INVALID_NEW_PASSWORD_FIELD = "invalid_newpassword1";
    private static final String ELEMENT_NEW_CONFIRM_PASSWORD_FIELD = "newpassword2";
    private static final String ELEMENT_INVALID_NEW_CONFIRM_PASSWORD_FIELD = "invalid_newpassword2";
    private static final String ELEMENT_CURRENT_NEW_PASSWORD_FIELD = "newpasswordoldpassword";
    private static final String ELEMENT_CURRENT_INVALID_NEW_PASSWORD_FIELD = "invalid_newpasswordoldpassword";

    private static final String ELEMENT_CHANGE_USER_NAME = "newusername";
    private static final String ELEMENT_INVALID_CHANGE_USER_NAME = "invalid_newusername";
    private static final String ELEMENT_CHANGE_USER_NAME_PASSWORD = "newusernamepassword";
    private static final String ELEMENT_INVALID_CHANGE_USER_NAME_PASSWORD = "invalid_newusernamepassword";

    private static final String ELEMENT_CHANGE_EMAIL_FIELD = "newemail";
    private static final String ELEMENT_INVALID_CHANGE_EMAIL_FIELD = "invalid_newemail";
    private static final String ELEMENT_CHANGE_EMAIL_PASSWORD_FIELD = "newemailpassword";
    private static final String ELEMENT_INVALID_CHANGE_EMAIL_PASSWORD_FIELD = "invalid_newemailpassword";

    private static final String ELEMENT_DELETE_ACCOUNT_PASSWORD_FIELD = "deleteaccountpassword";
    private static final String ELEMENT_INVALID_DELETE_ACCOUNT_PASSWORD_FIELD = "invalid_deleteaccountpassword";

    private static final String ELEMENT_CHANGE_PASSWORD_BUTTON = "newpasswordbutton";
    private static final String ELEMENT_CHANGE_USER_NAME_BUTTON = "newusernamebutton";
    private static final String ELEMENT_CHANGE_EMAIL_BUTTON = "newemailbutton";
    private static final String ELEMENT_DELETE_ACCOUNT_BUTTON = "deleteaccountbutton";

    private final WebDriver driver;

    public WebElement getNewPasswordField() {
        return driver.findElement(By.id(ELEMENT_NEW_PASSWORD_FIELD));
    }

    public WebElement getInvalidNewPasswordField() {
        return driver.findElement(By.id(ELEMENT_INVALID_NEW_PASSWORD_FIELD));
    }

    public WebElement getNewConfirmPasswordField() {
        return driver.findElement(By.id(ELEMENT_NEW_CONFIRM_PASSWORD_FIELD));
    }

    public WebElement getInvalidNewConfirmPasswordField() {
        return driver.findElement(By.id(ELEMENT_INVALID_NEW_CONFIRM_PASSWORD_FIELD));
    }

    public WebElement getCurrentNewPasswordField() {
        return driver.findElement(By.id(ELEMENT_CURRENT_NEW_PASSWORD_FIELD));
    }

    public WebElement getCurrentInvalidNewPasswordField() {
        return driver.findElement(By.id(ELEMENT_CURRENT_INVALID_NEW_PASSWORD_FIELD));
    }

    public WebElement getChangeUserNameField() {
        return driver.findElement(By.id(ELEMENT_CHANGE_USER_NAME));
    }

    public WebElement getInvalidChangeUserNameField() {
        return driver.findElement(By.id(ELEMENT_INVALID_CHANGE_USER_NAME));
    }

    public WebElement getChangeUserNamePasswordField() {
        return driver.findElement(By.id(ELEMENT_CHANGE_USER_NAME_PASSWORD));
    }

    public WebElement getInvalidChangeUserNamePasswordField() {
        return driver.findElement(By.id(ELEMENT_INVALID_CHANGE_USER_NAME_PASSWORD));
    }

    public WebElement getChangeEmailField() {
        return driver.findElement(By.id(ELEMENT_CHANGE_EMAIL_FIELD));
    }

    public WebElement getInvalidChangeEmailField() {
        return driver.findElement(By.id(ELEMENT_INVALID_CHANGE_EMAIL_FIELD));
    }

    public WebElement getChangeEmailPasswordField() {
        return driver.findElement(By.id(ELEMENT_CHANGE_EMAIL_PASSWORD_FIELD));
    }

    public WebElement getInvalidChangeEmailPasswordField() {
        return driver.findElement(By.id(ELEMENT_INVALID_CHANGE_EMAIL_PASSWORD_FIELD));
    }

    public WebElement getChangePasswordButton() {
        return driver.findElement(By.id(ELEMENT_CHANGE_PASSWORD_BUTTON));
    }

    public WebElement getDeleteAccountPasswordField() {
        return driver.findElement(By.id(ELEMENT_DELETE_ACCOUNT_PASSWORD_FIELD));
    }

    public WebElement getInvalidDeleteAccountPasswordField(){
        return driver.findElement(By.id(ELEMENT_INVALID_DELETE_ACCOUNT_PASSWORD_FIELD));
    }

    public WebElement getChangeUserNameButton() {
        return driver.findElement(By.id(ELEMENT_CHANGE_USER_NAME_BUTTON));
    }

    public WebElement getChangeEmailButton() {
        return driver.findElement(By.id(ELEMENT_CHANGE_EMAIL_BUTTON));
    }

    public WebElement getDeleteAccountButton() {
        return driver.findElement(By.id(ELEMENT_DELETE_ACCOUNT_BUTTON));
    }
}
