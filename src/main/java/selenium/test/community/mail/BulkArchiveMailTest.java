package selenium.test.community.mail;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import selenium.logic.domain.Mail;
import selenium.logic.domain.SeleniumAccount;
import selenium.logic.domain.SeleniumCharacter;
import selenium.logic.page.CommunityPage;
import selenium.logic.validator.NotificationValidator;
import selenium.test.community.mail.helper.MailTestHelper;
import selenium.test.community.mail.helper.SendMailHelper;
import selenium.test.community.util.CommunityTestHelper;
import selenium.test.community.util.CommunityTestInitializer;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Builder
@Slf4j
public class BulkArchiveMailTest {
    private static final String OTHER_SUBJECT = "other_subject";
    private static final String NOTIFICATION_MAILS_ARCHIVED = "Üzenetek archiválva.";

    private final CommunityTestInitializer communityTestInitializer;
    private final CommunityTestHelper communityTestHelper;
    private final CommunityPage communityPage;
    private final SendMailHelper sendMailHelper;
    private final MailTestHelper mailTestHelper;
    private final NotificationValidator notificationValidator;

    public void testBulkArchiveMail() {
        List<SeleniumAccount> accounts = communityTestInitializer.registerAccounts(new int[]{1, 1});

        SeleniumAccount account = accounts.get(0);
        SeleniumCharacter character = account.getCharacter(0);
        communityTestHelper.goToCommunityPageOf(account, character);

        SeleniumAccount otherAccount = accounts.get(1);
        SeleniumCharacter otherCharacter = otherAccount.getCharacter(0);
        sendMailHelper.sendMailTo(otherCharacter);

        sendMailHelper.setSubject(OTHER_SUBJECT)
            .setAddressee(otherCharacter)
            .setMessage()
            .sendMail();

        communityTestHelper.goToCommunityPageOf(otherAccount, otherCharacter, 2);

        List<Mail> mails = mailTestHelper.getReceivedMails();
        mails.forEach(Mail::select);

        mailTestHelper.selectBulkArchiveOption();

        communityPage.getExecuteBulkEditButtonForReceivedMails().click();
        notificationValidator.verifyNotificationVisibility(NOTIFICATION_MAILS_ARCHIVED);

        List<Mail> archivedMails = mailTestHelper.getArchivedMails();
        assertEquals(2, archivedMails.size());

        verifyContainsSubject(archivedMails, SendMailHelper.DEFAULT_SUBJECT);
        verifyContainsSubject(archivedMails, OTHER_SUBJECT);
    }

    private void verifyContainsSubject(List<Mail> archivedMails, String otherSubject) {
        assertTrue(
            archivedMails.stream()
                .anyMatch(mail -> mail.getSubject().equals(otherSubject))
        );
    }
}
