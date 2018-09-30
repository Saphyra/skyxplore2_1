package selenium.flow;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.domain.SeleniumUser;
import selenium.page.IndexPage;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static selenium.util.LinkUtil.CHARACTER_SELECT;
import static selenium.util.LinkUtil.HOST;
import static selenium.util.LocatorUtil.getNotificationElementsLocator;

public class Registration {
    public static final String SUCCESSFUL_REGISTRATION_NOTIFICATION = "Sikeres regisztráció!";

    private final WebDriver driver;
    private final IndexPage indexPage;

    public Registration(WebDriver driver){
        this(driver, new IndexPage(driver));
    }

    public Registration(WebDriver driver, IndexPage indexPage){
        this.driver = driver;
        this.indexPage = indexPage;
    }

    public SeleniumUser registerUser(){
        SeleniumUser user = SeleniumUser.create();
        registerUser(user);
        return user;
    }

    public void registerUser(SeleniumUser user) {
        assertEquals(HOST, driver.getCurrentUrl());
        fillRegistrationForm(user);
        sendForm();
        validateRegistration();
    }

    private void fillRegistrationForm(SeleniumUser user) {
        WebElement userNameElement = indexPage.getRegistrationUserNameField();
        userNameElement.sendKeys(user.getUserName());

        WebElement passwordElement = indexPage.getRegistrationPasswordField();
        passwordElement.sendKeys(user.getPassword());

        WebElement confirmPasswordElement = indexPage.getRegistrationConfirmPasswordField();
        confirmPasswordElement.sendKeys(user.getPassword());

        WebElement emailElement = indexPage.getRegistrationEmailField();
        emailElement.sendKeys(user.getEmail());
    }

    private void sendForm() {
        indexPage.getRegisterButton().click();
    }

    private void validateRegistration(){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getNotificationElementsLocator()));

        assertEquals(CHARACTER_SELECT, driver.getCurrentUrl());
        List<WebElement> notifications = driver.findElements(getNotificationElementsLocator());
        assertTrue(notifications.stream().anyMatch(w -> w.getText().equals(SUCCESSFUL_REGISTRATION_NOTIFICATION)));
    }
}
