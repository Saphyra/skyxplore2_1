package selenium.test.community.mail;

import lombok.Builder;
import selenium.logic.domain.Mail;
import selenium.logic.domain.SeleniumAccount;
import selenium.logic.domain.SeleniumCharacter;
import selenium.logic.page.CommunityPage;
import selenium.test.community.helper.MailTestHelper;
import selenium.test.community.helper.SendMailHelper;
import selenium.test.community.helper.CommunityTestHelper;
import selenium.test.community.helper.CommunityTestInitializer;

import java.util.List;

import static org.junit.Assert.assertTrue;

@Builder
public class SuccessfullySentMailTest {
    public static final String NOTIFICATION_MESSAGE_SENT = "Üzenet elküldve.";

    private final CommunityTestInitializer communityTestInitializer;
    private final CommunityTestHelper communityTestHelper;
    private final CommunityPage communityPage;
    private final SendMailHelper sendMailHelper;
    private final MailTestHelper mailTestHelper;

    public void testSuccessfullySentMail() {
        List<SeleniumAccount> accounts = communityTestInitializer.registerAccounts(new int[]{1, 1});

        SeleniumAccount account = accounts.get(0);
        SeleniumCharacter character = account.getCharacter(0);

        SeleniumAccount otherAccount = accounts.get(1);
        SeleniumCharacter otherCharacter = otherAccount.getCharacter(0);

        communityTestHelper.goToCommunityPageOf(account, character);

        communityPage.getWriteNewMailButton().click();

        sendMailHelper.setSubject()
            .setAddressee(otherCharacter)
            .setMessage()
            .verifyMailSent(NOTIFICATION_MESSAGE_SENT);

        verifyMailAtSentMails(otherCharacter);
        verifyMailArrived(otherAccount, character);
    }

    private void verifyMailAtSentMails(SeleniumCharacter addressee) {
        List<Mail> sentMails = mailTestHelper.getSentMails();
        assertTrue(
            sentMails.stream().anyMatch(mail -> mail.getAddressee().equals(addressee.getCharacterName()))
        );
    }

    private void verifyMailArrived(SeleniumAccount otherAccount, SeleniumCharacter character) {
        communityTestHelper.goToCommunityPageOf(otherAccount, otherAccount.getCharacter(0), 1);

        List<Mail> receivedMails = mailTestHelper.getReceivedMails();
        assertTrue(
            receivedMails.stream().anyMatch(mail -> mail.getSender().equals(character.getCharacterName()))
        );
    }
}
