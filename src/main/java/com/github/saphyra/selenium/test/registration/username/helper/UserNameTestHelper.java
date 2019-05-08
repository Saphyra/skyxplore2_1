package com.github.saphyra.selenium.test.registration.username.helper;

import com.github.saphyra.selenium.logic.domain.SeleniumUser;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.saphyra.selenium.logic.page.IndexPage;

@RequiredArgsConstructor
public class UserNameTestHelper {
    private final WebDriver driver;
    private final IndexPage indexPage;

    public void setUpForUserNameTest(SeleniumUser user) {
        WebElement passwordElement = indexPage.getRegistrationPasswordField();
        passwordElement.sendKeys(user.getPassword());

        WebElement confirmPasswordElement = indexPage.getRegistrationConfirmPasswordField();
        confirmPasswordElement.sendKeys(user.getPassword());

        WebElement emailElement = indexPage.getRegistrationEmailField();
        emailElement.sendKeys(user.getEmail());

        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.invisibilityOf(indexPage.getInvalidPasswordField()));
        webDriverWait.until(ExpectedConditions.invisibilityOf(indexPage.getInvalidConfirmPasswordField()));
        webDriverWait.until(ExpectedConditions.invisibilityOf(indexPage.getInvalidEmailField()));
    }
}
