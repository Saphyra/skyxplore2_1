package selenium.aanew.logic.domain;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

@RequiredArgsConstructor
public class Friend {
    private static final String SELECTOR_CHARACTER_NAME = "div:first-child";

    private final WebElement element;

    public String getCharacterName() {
        return element.findElement(By.cssSelector(SELECTOR_CHARACTER_NAME)).getText();
    }
}
