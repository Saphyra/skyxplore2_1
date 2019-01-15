package selenium.logic.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertTrue;
import static selenium.logic.util.Util.hasClass;

@RequiredArgsConstructor
@Slf4j
public class Mail {
    private static final String SELECTOR_SENDER = ".mailheader tr:first-child td:nth-child(2)";
    private static final String CLASS_MAIL_UNREAD = "unreadmail";
    private static final String SELECTOR_ARCHIVE_BUTTON = "tr:first-child td:nth-child(4) button:nth-child(2)";
    private static final String SELECTOR_SELECT_FIELD = "tr:first-child td:first-child input";
    private static final String SELECTOR_SUBJECT = "tr:nth-child(2) td:first-child";
    private static final String SELECTOR_RESTORE_BUTTON = "tr:first-child td:nth-child(4) button:nth-child(2)";

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

    public void archive() {
        element.findElement(By.cssSelector(SELECTOR_ARCHIVE_BUTTON)).click();
    }

    public void select() {
        WebElement selectField = this.element.findElement(By.cssSelector(SELECTOR_SELECT_FIELD));
        selectField.click();
        assertTrue(selectField.isSelected());
    }

    public String getSubject() {
        return element.findElement(By.cssSelector(SELECTOR_SUBJECT)).getText().split(": ")[1];
    }

    public void restore() {
        element.findElement(By.cssSelector(SELECTOR_RESTORE_BUTTON)).click();
    }
}
