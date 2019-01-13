package selenium.test.community.mail.helper;

import lombok.RequiredArgsConstructor;
import selenium.logic.domain.SeleniumCharacter;
import selenium.logic.page.CommunityPage;
import selenium.logic.validator.NotificationValidator;

@RequiredArgsConstructor
public class SendMailHelper {
    public static final String DEFAULT_MESSAGE = "message";
    public static final String DEFAULT_SUBJECT = "subject";

    private final CommunityPage communityPage;
    private final NotificationValidator notificationValidator;

    public SendMailHelper setSubject(String subject) {
        communityPage.getMailSubjectField().sendKeys(subject);
        return this;
    }

    public SendMailHelper setMessage() {
        setMessage(DEFAULT_MESSAGE);
        return this;
    }

    public SendMailHelper setMessage(String message) {
        communityPage.getMessageField().sendKeys(message);
        return this;
    }

    public SendMailHelper setAddressee(SeleniumCharacter addressee) {
        communityPage.getAddresseeInputField().sendKeys(addressee.getCharacterName());
        communityPage.getAddresseeElements().stream()
            .filter(element -> element.getText().equals(addressee.getCharacterName()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("AddresseeNotFound"))
            .click();
        return this;
    }

    public void verifyCannotSendMail(String notification){
        communityPage.getSendMailButton().click();
        notificationValidator.verifyOnlyOneNotification(notification);
    }

    public SendMailHelper setSubject() {
        setSubject(DEFAULT_SUBJECT);
        return this;
    }
}
