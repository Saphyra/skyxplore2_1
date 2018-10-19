package selenium.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OverviewPage {
    private static final String ELEMENT_CHARACTER_SELECT_PAGE_BUTTON = "footer button:nth-child(2)";

    private final WebDriver driver;

    public WebElement getCharacterSelectPageButton() {
        return driver.findElement(By.cssSelector(ELEMENT_CHARACTER_SELECT_PAGE_BUTTON));
    }
}
