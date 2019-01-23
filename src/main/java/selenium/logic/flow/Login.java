package selenium.logic.flow;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.logic.domain.MessageCodes;
import selenium.logic.domain.SeleniumUser;
import selenium.logic.page.IndexPage;
import selenium.logic.validator.NotificationValidator;

import static org.junit.Assert.assertEquals;
import static selenium.logic.util.LinkUtil.CHARACTER_SELECT;
import static selenium.logic.util.LinkUtil.HOST;
import static selenium.logic.util.Util.cleanNotifications;

@Slf4j
public class Login {
    private static final String MESSAGE_CODE_BAD_CREDENTIALS = "BAD_CREDENTIALS";

    private final WebDriver driver;
    private final IndexPage indexPage;
    private final NotificationValidator notificationValidator;
    private final Navigate navigate;
    private final MessageCodes messageCodes;

    public Login(WebDriver driver, MessageCodes messageCodes) {
        this.driver = driver;
        indexPage = new IndexPage(driver);
        notificationValidator = new NotificationValidator(driver);
        navigate = new Navigate(driver);
        this.messageCodes = messageCodes;
    }

    public void login(SeleniumUser user){
        navigate.toIndexPage();
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
        new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe(CHARACTER_SELECT));
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
        notificationValidator.verifyOnlyOneNotification(messageCodes.get(MESSAGE_CODE_BAD_CREDENTIALS));
    }
}
