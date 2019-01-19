package selenium.test.community.helper;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebElement;
import selenium.logic.domain.SeleniumCharacter;
import selenium.logic.page.CommunityPage;
import selenium.logic.validator.NotificationValidator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RequiredArgsConstructor
public class SendMailHelper {
    public static final String DEFAULT_MESSAGE = "message";
    public static final String DEFAULT_SUBJECT = "subject";

    private final CommunityPage communityPage;
    private final NotificationValidator notificationValidator;

    public void sendMailTo(SeleniumCharacter character) {
        if (!communityPage.getSendMailContainer().isDisplayed()) {
            communityPage.getWriteNewMailButton().click();
        }

        setSubject();
        setMessage();
        setAddressee(character);
        sendMail();
    }

    public SendMailHelper setSubject(String subject) {
        if (!communityPage.getSendMailContainer().isDisplayed()) {
            communityPage.getWriteNewMailButton().click();
        }
        communityPage.getMailSubjectField().sendKeys(subject);
        return this;
    }

    public SendMailHelper setMessage() {

        setMessage(DEFAULT_MESSAGE);
        return this;
    }

    public SendMailHelper setMessage(String message) {
        if (!communityPage.getSendMailContainer().isDisplayed()) {
            communityPage.getWriteNewMailButton().click();
        }
        communityPage.getMessageField().sendKeys(message);
        return this;
    }

    public SendMailHelper verifyAddresseeNotFound(SeleniumCharacter addressee) {
        if (!communityPage.getSendMailContainer().isDisplayed()) {
            communityPage.getWriteNewMailButton().click();
        }
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
        if (!communityPage.getSendMailContainer().isDisplayed()) {
            communityPage.getWriteNewMailButton().click();
        }
        communityPage.getAddresseeInputField().sendKeys(addressee.getCharacterName());
        communityPage.getAddresseeElements().stream()
            .filter(element -> element.getText().equals(addressee.getCharacterName()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("AddresseeNotFound"))
            .click();
        return this;
    }

    public void verifyCannotSendMail(String notification) {
        sendMail();
        notificationValidator.verifyOnlyOneNotification(notification);
        assertTrue(communityPage.getSendMailContainer().isDisplayed());
    }

    public void verifyMailSent(String notification) {
        sendMail();
        notificationValidator.verifyOnlyOneNotification(notification);
        assertFalse(communityPage.getSendMailContainer().isDisplayed());
    }

    public void sendMail() {
        communityPage.getSendMailButton().click();
    }

    public SendMailHelper setSubject() {
        setSubject(DEFAULT_SUBJECT);
        return this;
    }
}
