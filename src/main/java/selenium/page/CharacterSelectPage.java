package selenium.page;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertEquals;
import static selenium.util.LinkUtil.CHARACTER_SELECT;

@RequiredArgsConstructor
public class CharacterSelectPage {
    public static final String ACCOUNT_BUTTON_SELECTOR = "footer button:nth-child(2)";

    private final WebDriver driver;

    public WebElement getAccountPageButton() {
        assertEquals(CHARACTER_SELECT, driver.getCurrentUrl());
        return driver.findElement(By.cssSelector(ACCOUNT_BUTTON_SELECTOR));
    }
}
