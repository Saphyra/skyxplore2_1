package selenium.domain;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

@RequiredArgsConstructor
public class Friend {
    private final WebElement element;

    public String getCharacterName() {
        return element.findElement(By.cssSelector("div:first-child")).getText();
    }
}
