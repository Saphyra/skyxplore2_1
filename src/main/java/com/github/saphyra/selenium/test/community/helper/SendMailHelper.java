package com.github.saphyra.selenium.test.community.helper;

import static com.github.saphyra.selenium.logic.util.LocalizationUtil.getAdditionalContent;
import static com.github.saphyra.selenium.logic.util.WaitUtil.waitUntil;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.saphyra.selenium.logic.domain.SeleniumCharacter;
import com.github.saphyra.selenium.logic.domain.localization.Page;
import com.github.saphyra.selenium.logic.page.CommunityPage;
import com.github.saphyra.selenium.logic.validator.NotificationValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SendMailHelper {
    private static final String DEFAULT_MESSAGE = "message";
    public static final String DEFAULT_SUBJECT = "subject";
    private static final String MESSAGE_CODE_MAIL_SENT = "mail-sent";

    private final WebDriver driver;
    private final CommunityPage communityPage;
    private final NotificationValidator notificationValidator;

    public void sendMailTo(SeleniumCharacter character) {
        openWriteMailPage();

        setSubject();
        setMessage();
        setAddressee(character);
        sendMail();
        verifyMailSent(getAdditionalContent(driver, Page.COMMUNITY, MESSAGE_CODE_MAIL_SENT));
    }

    public void openWriteMailPage() {
        if (!communityPage.getSendMailContainer().isDisplayed()) {
            communityPage.getWriteNewMailButton().click();
        }
        waitUntil(() -> communityPage.getSendMailContainer().isDisplayed(), "Waiting until writeMail page is opened...");
    }

    public SendMailHelper setSubject(String subject) {
        openWriteMailPage();
        communityPage.getMailSubjectField().sendKeys(subject);
        return this;
    }

    public SendMailHelper setMessage() {

        setMessage(DEFAULT_MESSAGE);
        return this;
    }

    public SendMailHelper setMessage(String message) {
        openWriteMailPage();
        communityPage.getMessageField().sendKeys(message);
        return this;
    }

    @SuppressWarnings("UnusedReturnValue")
    public SendMailHelper verifyAddresseeNotFound(SeleniumCharacter addressee) {
        openWriteMailPage();
        WebElement addresseeInputField = communityPage.getAddresseeInputField();
        addresseeInputField.clear();
        addresseeInputField.sendKeys(addressee.getCharacterName());

        assertTrue(
            communityPage.getAddresseeElements().stream()
                .noneMatch(element -> element.getText().equals(addressee.getCharacterName()))
        );

        return this;
    }

    public SendMailHelper setAddressee(SeleniumCharacter addressee) {
        openWriteMailPage();
        communityPage.getAddresseeInputField().sendKeys(addressee.getCharacterName());
        communityPage.getAddresseeElements().stream()
            .filter(element -> element.getText().equals(addressee.getCharacterName()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("AddresseeNotFound with name " + addressee.getCharacterName()))
            .click();
        return this;
    }

    public void verifyCannotSendMail(String notification) {
        sendMail();
        notificationValidator.verifyOnlyOneNotification(notification);
        assertTrue(communityPage.getSendMailContainer().isDisplayed());
    }

    public void verifyMailSent(String notification) {
        waitUntil(() -> !communityPage.getSendMailContainer().isDisplayed(), "Waiting until writeMail page is closed...");
        notificationValidator.verifyOnlyOneNotification(notification);
        assertFalse(communityPage.getSendMailContainer().isDisplayed());
    }

    public SendMailHelper sendMail() {
        communityPage.getSendMailButton().click();
        return this;
    }

    public SendMailHelper setSubject() {
        setSubject(DEFAULT_SUBJECT);
        return this;
    }
}
