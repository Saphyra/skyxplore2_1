package selenium.logic.domain;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BlockableCharacter {
    private static final String SELECTOR_CHARACTER_NAME = "div:first-child";
    private static final String SELECTOR_BLOCK_BUTTON = ".block-character-button";

    private final WebElement element;
    private final WebDriver driver;

    public String getCharacterName() {
        return element.findElement(By.cssSelector(SELECTOR_CHARACTER_NAME)).getText();
    }

    public void block() {
        element.findElement(By.cssSelector(SELECTOR_BLOCK_BUTTON)).click();
        new WebDriverWait(driver, 2).until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }
}
