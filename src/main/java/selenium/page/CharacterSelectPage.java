package selenium.page;

import static org.junit.Assert.assertEquals;
import static selenium.util.LinkUtil.CHARACTER_SELECT;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CharacterSelectPage {
    private static final String ACCOUNT_BUTTON_SELECTOR = "footer button:nth-child(2)";

    private static final String CHARACTERS_SELECTOR = "#characters tr td:first-child";

    private static final String ELEMENT_NEW_CHARACTER_NAME = "newcharactername";
    private static final String ELEMENT_INVALID_NEW_CHARACTER_NAME = "invalid_newcharactername";
    private static final String ELEMENT_NEW_CHARACTER_BUTTON = "newcharacterbutton";

    private final WebDriver driver;

    public WebElement getAccountPageButton() {
        assertEquals(CHARACTER_SELECT, driver.getCurrentUrl());
        return driver.findElement(By.cssSelector(ACCOUNT_BUTTON_SELECTOR));
    }

    public WebElement getNewCharacterNameField(){
        return driver.findElement(By.id(ELEMENT_NEW_CHARACTER_NAME));
    }

    public WebElement getCreateCharacterButton() {
        return driver.findElement(By.id(ELEMENT_NEW_CHARACTER_BUTTON));
    }

    public WebElement getInvalidNewCharacterNameField() {
        return driver.findElement(By.id(ELEMENT_INVALID_NEW_CHARACTER_NAME));
    }

    public List<WebElement> getCharacterList() {
        return driver.findElements(By.cssSelector(CHARACTERS_SELECTOR));
    }
}
