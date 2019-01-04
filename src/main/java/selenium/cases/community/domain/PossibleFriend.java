package selenium.cases.community.domain;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

@RequiredArgsConstructor
public class PossibleFriend {
    private final WebElement element;

    public String getCharacterName(){
        return element.findElement(By.cssSelector("div:first-child")).getText();
    }

    public void addFriend(){
        element.findElement(By.cssSelector("button:first-of-type")).click();
    }
}
