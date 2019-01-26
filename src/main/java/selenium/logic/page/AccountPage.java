package selenium.logic.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AccountPage {
    private static final String ELEMENT_NEW_PASSWORD_FIELD = "new-password";
    private static final String ELEMENT_INVALID_NEW_PASSWORD_FIELD = "invalid-new-password";
    private static final String ELEMENT_NEW_CONFIRM_PASSWORD_FIELD = "new-password-confirmation";
    private static final String ELEMENT_INVALID_NEW_CONFIRM_PASSWORD_FIELD = "invalid-new-password-confirmation";
    private static final String ELEMENT_CURRENT_NEW_PASSWORD_FIELD = "current-password-for-password-change";
    private static final String ELEMENT_CURRENT_INVALID_NEW_PASSWORD_FIELD = "invalid-current-password-for-password-change";

    private static final String ELEMENT_CHANGE_USER_NAME = "new-username";
    private static final String ELEMENT_INVALID_CHANGE_USER_NAME = "invalid-new-username";
    private static final String ELEMENT_CHANGE_USER_NAME_PASSWORD = "change-username-password";
    private static final String ELEMENT_INVALID_CHANGE_USER_NAME_PASSWORD = "invalid-change-username-password";

    private static final String ELEMENT_CHANGE_EMAIL_FIELD = "newemail";
    private static final String ELEMENT_INVALID_CHANGE_EMAIL_FIELD = "invalid_newemail";
    private static final String ELEMENT_CHANGE_EMAIL_PASSWORD_FIELD = "newemailpassword";
    private static final String ELEMENT_INVALID_CHANGE_EMAIL_PASSWORD_FIELD = "invalid_newemailpassword";

    private static final String ELEMENT_DELETE_ACCOUNT_PASSWORD_FIELD = "delete-account-password";
    private static final String ELEMENT_INVALID_DELETE_ACCOUNT_PASSWORD_FIELD = "invalid-delete-account-password";

    private static final String ELEMENT_CHANGE_PASSWORD_BUTTON = "change-password-button";
    private static final String ELEMENT_CHANGE_USER_NAME_BUTTON = "change-username-button";
    private static final String ELEMENT_CHANGE_EMAIL_BUTTON = "newemailbutton";
    private static final String ELEMENT_DELETE_ACCOUNT_BUTTON = "delete-account-button";

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
