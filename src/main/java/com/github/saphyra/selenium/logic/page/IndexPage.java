package com.github.saphyra.selenium.logic.page;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@RequiredArgsConstructor
public class IndexPage {
    private static final String ELEMENT_LOGIN_USER_NAME = "login-username";
    private static final String ELEMENT_LOGIN_PASSWORD = "login-password";

    private static final String ELEMENT_REGISTRATION_USER_NAME = "reg-username";
    private static final String ELEMENT_INVALID_USER_NAME = "invalid-username";

    private static final String ELEMENT_REGISTRATION_PASSWORD = "reg-password";
    private static final String ELEMENT_INVALID_PASSWORD = "invalid-password";

    private static final String ELEMENT_REGISTRATION_CONFIRM_PASSWORD = "reg-confirm-password";
    private static final String ELEMENT_INVALID_CONFIRM_PASSWORD = "invalid-confirm-password";

    private static final String ELEMENT_REGISTRATION_EMAIL = "reg-email";
    private static final String ELEMENT_INVALID_EMAIL = "invalid-email";

    private static final String ELEMENT_REGISTRATION_BUTTON = "registration-button";
    private static final String SELECTOR_LOGIN_BUTTON = "login-button";

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
