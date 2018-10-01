package selenium.flow;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import selenium.domain.SeleniumUser;
import selenium.page.IndexPage;
import selenium.validator.NotificationValidator;

import static org.junit.Assert.assertEquals;
import static selenium.util.DOMUtil.cleanNotifications;
import static selenium.util.LinkUtil.CHARACTER_SELECT;
import static selenium.util.LinkUtil.HOST;

@Slf4j
public class Login {
    private static final String NOTIFICATION_LOGIN_FAILED = "Hibás felhasználónév vagy jelszó.";

    private final WebDriver driver;
    private final IndexPage indexPage;
    private final NotificationValidator notificationValidator;

    public Login(WebDriver driver) {
        this.driver = driver;
        indexPage = new IndexPage(driver);
        notificationValidator = new NotificationValidator(driver);
    }

    public void login(SeleniumUser user){
        assertEquals(HOST, driver.getCurrentUrl());
        sendRequest(user);
        validateSuccessfulLogin();
    }

    private void sendRequest(SeleniumUser user) {
        WebElement userNameField = indexPage.getLoginUserNameField();
        userNameField.clear();
        userNameField.sendKeys(user.getUserName());

        WebElement passwordField = indexPage.getLoginPasswordField();
        passwordField.clear();
        passwordField.sendKeys(user.getPassword());

        WebElement loginButton = indexPage.getLoginButton();
        loginButton.click();
    }

    private void validateSuccessfulLogin() {
        assertEquals(CHARACTER_SELECT, driver.getCurrentUrl());
    }

    public void loginFailure(SeleniumUser user) {
        cleanNotifications(driver);
        assertEquals(HOST, driver.getCurrentUrl());
        sendRequest(user);
        validateLoginFailure();
    }

    private void validateLoginFailure(){
        assertEquals(HOST, driver.getCurrentUrl());
        notificationValidator.verifyOnlyOneNotification(NOTIFICATION_LOGIN_FAILED);
    }
}
