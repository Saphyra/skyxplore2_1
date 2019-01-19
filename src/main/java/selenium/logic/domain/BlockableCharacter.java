package selenium.logic.domain;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

@RequiredArgsConstructor
public class BlockableCharacter {
    private static final String SELECTOR_CHARACTER_NAME = "div:first-child";
    private static final String SELECTOR_BLOCK_BUTTON = ".blockcharacterbutton";
    private final WebElement element;

    public String getCharacterName() {
        return element.findElement(By.cssSelector(SELECTOR_CHARACTER_NAME)).getText();
    }

    public void block() {
        element.findElement(By.cssSelector(SELECTOR_BLOCK_BUTTON)).click();
    }
}
