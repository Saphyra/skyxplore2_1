package selenium.flow;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.domain.SeleniumUser;
import selenium.page.IndexPage;
import selenium.validator.NotificationValidator;

import static org.junit.Assert.assertEquals;
import static selenium.util.DOMUtil.cleanNotifications;
import static selenium.util.LinkUtil.CHARACTER_SELECT;
import static selenium.util.LinkUtil.HOST;
import static selenium.util.LocatorUtil.getNotificationElementsLocator;

public class Registration {
    public static final String SUCCESSFUL_REGISTRATION_NOTIFICATION = "Sikeres regisztráció!";

    private final WebDriver driver;
    private final IndexPage indexPage;
    private final NotificationValidator notificationValidator;

    public Registration(WebDriver driver){
        this(driver, new IndexPage(driver));
    }

    public Registration(WebDriver driver, IndexPage indexPage){
        this.driver = driver;
        this.indexPage = indexPage;
        this.notificationValidator = new NotificationValidator(driver);
    }

    public SeleniumUser registerUser(){
        SeleniumUser user = SeleniumUser.create();
        registerUser(user);
        return user;
    }

    public void registerUser(SeleniumUser user) {
        assertEquals(HOST, driver.getCurrentUrl());
        cleanNotifications(driver);
        fillRegistrationForm(user);
        sendForm();
        validateRegistration();
    }

    private void fillRegistrationForm(SeleniumUser user) {
        WebElement userNameElement = indexPage.getRegistrationUserNameField();
        userNameElement.clear();
        userNameElement.sendKeys(user.getUserName());

        WebElement passwordElement = indexPage.getRegistrationPasswordField();
        passwordElement.clear();
        passwordElement.sendKeys(user.getPassword());

        WebElement confirmPasswordElement = indexPage.getRegistrationConfirmPasswordField();
        confirmPasswordElement.clear();
        confirmPasswordElement.sendKeys(user.getPassword());

        WebElement emailElement = indexPage.getRegistrationEmailField();
        emailElement.clear();
        emailElement.sendKeys(user.getEmail());

        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.invisibilityOf(indexPage.getInvalidUserNameField()));
        webDriverWait.until(ExpectedConditions.invisibilityOf(indexPage.getInvalidPasswordField()));
        webDriverWait.until(ExpectedConditions.invisibilityOf(indexPage.getInvalidConfirmPasswordField()));
        webDriverWait.until(ExpectedConditions.invisibilityOf(indexPage.getInvalidEmailField()));
    }

    private void sendForm() {
        indexPage.getRegisterButton().click();
    }

    private void validateRegistration(){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getNotificationElementsLocator()));

        assertEquals(CHARACTER_SELECT, driver.getCurrentUrl());
        notificationValidator.verifyOnlyOneNotification(SUCCESSFUL_REGISTRATION_NOTIFICATION);
    }
}
