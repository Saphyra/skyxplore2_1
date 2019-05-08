package com.github.saphyra.selenium.logic.flow;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.github.saphyra.selenium.logic.domain.SeleniumUser;
import com.github.saphyra.selenium.logic.page.IndexPage;
import com.github.saphyra.selenium.logic.validator.NotificationValidator;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static com.github.saphyra.selenium.logic.util.LinkUtil.CHARACTER_SELECT;
import static com.github.saphyra.selenium.logic.util.LinkUtil.HOST;
import static com.github.saphyra.selenium.logic.util.Util.cleanNotifications;

public class Registration {
    private static final String MESSAGE_CODE_SUCCESSFUL_REGISTRATION = "REGISTRATION_SUCCESSFUL";

    private final WebDriver driver;
    private final IndexPage indexPage;
    private final NotificationValidator notificationValidator;
    private final Map<String, String> messageCodes;

    public Registration(WebDriver driver, Map<String, String> messageCodes) {
        this(driver, new IndexPage(driver), messageCodes);
    }

    public Registration(WebDriver driver, IndexPage indexPage, Map<String, String> messageCodes) {
        this.driver = driver;
        this.indexPage = indexPage;
        this.notificationValidator = new NotificationValidator(driver);
        this.messageCodes = messageCodes;
    }

    public SeleniumUser registerUser() {
        SeleniumUser user = SeleniumUser.create();
        registerUser(user);
        return user;
    }

    public void registerUser(SeleniumUser user) {
        if (!HOST.equals(driver.getCurrentUrl())) {
            new Navigate(driver).toIndexPage();
        }
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
        WebElement registerButton = indexPage.getRegisterButton();
        new WebDriverWait(driver, 2).until(ExpectedConditions.elementToBeClickable(registerButton));
        registerButton.click();
    }

    private void validateRegistration() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe(CHARACTER_SELECT));

        assertEquals(CHARACTER_SELECT, driver.getCurrentUrl());
        notificationValidator.verifyOnlyOneNotification(messageCodes.get(MESSAGE_CODE_SUCCESSFUL_REGISTRATION));
    }
}
