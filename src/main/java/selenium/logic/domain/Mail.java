package selenium.logic.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static selenium.logic.util.Util.hasClass;

@RequiredArgsConstructor
public class Mail {
    private static final String SELECTOR_SENDER = ".mailheader tr:first-child td:nth-child(2)";
    private static final String CLASS_MAIL_UNREAD = "unreadmail";

    @Getter
    private final WebElement element;

    public String getSender() {
        return element.findElement(By.cssSelector(SELECTOR_SENDER)).getText().split(": ")[1];
    }

    public String getAddressee() {
        return getSender();
    }

    public boolean isRead() {
        return !hasClass(element, CLASS_MAIL_UNREAD);
    }

    public void read() {
        element.click();
    }
}
