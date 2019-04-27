package org.github.saphyra.selenium.logic.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.github.saphyra.selenium.logic.validator.NotificationValidator;

import static org.junit.Assert.assertTrue;
import static org.github.saphyra.selenium.logic.util.Util.hasClass;

@RequiredArgsConstructor
@Slf4j
public class Mail {
    private static final String SELECTOR_SENDER = ".mail-header tr:first-child td:nth-child(2) span:nth-child(3)";
    private static final String CLASS_MAIL_UNREAD = "unread-mail";
    private static final String SELECTOR_ARCHIVE_BUTTON = "tr:first-child td:nth-child(4) button:nth-child(2)";
    private static final String SELECTOR_SELECT_FIELD = "tr:first-child td:first-child input";
    private static final String SELECTOR_SUBJECT = "tr:nth-child(2) td:first-child";
    private static final String SELECTOR_RESTORE_BUTTON = "tr:first-child td:nth-child(4) button:nth-child(2)";
    private static final String MESSAGE_CODE_MAIL_ARCHIVED = "MAILS_ARCHIVED";
    private static final String MESSAGE_CODE_MAIL_RESTORED = "MAILS_RESTORED";
    private static final String SELECTOR_DELETE_BUTTON = "tr:first-child td:nth-child(4) button:first-child";
    private static final String MESSAGE_CODE_MAILS_DELETED = "MAILS_DELETED";
    private static final String SELECTOR_MARK_AS_READ_BUTTON = "tr:first-child td:nth-child(4) button:last-child";
    private static final String SELECTOR_MARK_AS_UNREAD_BUTTON = "tr:first-child td:nth-child(4) button:last-child";
    private static final String SELECTOR_REPLY_BUTTON = ".mail-body button:last-child";

    @Getter
    private final WebElement element;
    private final WebDriver driver;
    private final MessageCodes messageCodes;

    public String getSender() {
        return element.findElement(By.cssSelector(SELECTOR_SENDER)).getText();
    }

    public String getAddressee() {
        String sender = getSender();
        log.info("Sender: {}", sender);
        return sender;
    }

    public boolean isRead() {
        return !hasClass(element, CLASS_MAIL_UNREAD);
    }

    public void read() {
        element.click();
    }

    public void archive(NotificationValidator notificationValidator) {
        element.findElement(By.cssSelector(SELECTOR_ARCHIVE_BUTTON)).click();
        notificationValidator.verifyNotificationVisibility(messageCodes.get(MESSAGE_CODE_MAIL_ARCHIVED));
    }

    public void select() {
        WebElement selectField = this.element.findElement(By.cssSelector(SELECTOR_SELECT_FIELD));
        selectField.click();
        assertTrue(selectField.isSelected());
    }

    public String getSubject() {
        //TODO get subject from additionalContent
        return element.findElement(By.cssSelector(SELECTOR_SUBJECT)).getText().split("Tárgy: ")[1];
    }

    public void restore(NotificationValidator notificationValidator) {
        element.findElement(By.cssSelector(SELECTOR_RESTORE_BUTTON)).click();
        notificationValidator.verifyNotificationVisibility(messageCodes.get(MESSAGE_CODE_MAIL_RESTORED));
    }

    public void delete(NotificationValidator notificationValidator) {
        element.findElement(By.cssSelector(SELECTOR_DELETE_BUTTON)).click();
        new WebDriverWait(driver, 10).until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
        notificationValidator.verifyNotificationVisibility(messageCodes.get(MESSAGE_CODE_MAILS_DELETED));
    }

    public void markAsRead() {
        element.findElement(By.cssSelector(SELECTOR_MARK_AS_READ_BUTTON)).click();
    }

    public void markAsUnread() {
        element.findElement(By.cssSelector(SELECTOR_MARK_AS_UNREAD_BUTTON)).click();
    }

    public void reply() {
        if (!isRead()) {
            read();
        }
        element.findElement(By.cssSelector(SELECTOR_REPLY_BUTTON)).click();
    }
}
