package selenium.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AccountPage {
    private static final String ELEMENT_NEW_USER_NAME = "newusername";
    private static final String ELEMENT_INVALID_NEW_USER_NAME = "invalid_newusername";
    private static final String ELEMENT_NEW_USER_NAME_PASSWORD = "newusernamepassword";
    private static final String ELEMENT_INVALID_NEW_USER_NAME_PASSWORD = "invalid_newusernamepassword";

    private static final String ELEMENT_CHANGE_USER_NAME_BUTTON = "newusernamebutton";

    private final WebDriver driver;

    public AccountPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getNewUserNameField() {
        return driver.findElement(By.id(ELEMENT_NEW_USER_NAME));
    }

    public WebElement getInvalidNewUserNameField() {
        return driver.findElement(By.id(ELEMENT_INVALID_NEW_USER_NAME));
    }

    public WebElement getNewUserNamePasswordField() {
        return driver.findElement(By.id(ELEMENT_NEW_USER_NAME_PASSWORD));
    }

    public WebElement getInvalidNewUserNamePasswordField() {
        return driver.findElement(By.id(ELEMENT_INVALID_NEW_USER_NAME_PASSWORD));
    }

    public WebElement getChangeUserNameButton() {
        return driver.findElement(By.id(ELEMENT_CHANGE_USER_NAME_BUTTON));
    }
}
