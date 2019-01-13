package selenium.logic.domain;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

@RequiredArgsConstructor
public class Mail {
    public static final String SELECTOR_SENDER = ".mailheader tr:first-child td:nth-child(2)";
    private final WebElement element;

    public String getSender() {
        return element.findElement(By.cssSelector(SELECTOR_SENDER)).getText().split(": ")[1];
    }

    public String getAddressee() {
        return getSender();
    }
}
