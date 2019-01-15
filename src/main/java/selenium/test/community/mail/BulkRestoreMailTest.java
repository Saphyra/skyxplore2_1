package selenium.test.community.mail;

import lombok.Builder;
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

@Builder
public class BulkRestoreMailTest {
    private static final String NOTIFICATION_MAILS_RESTORED = "Üzenetek visszaállítva.";
    private final CommunityTestInitializer communityTestInitializer;
    private final CommunityTestHelper communityTestHelper;
    private final CommunityPage communityPage;
    private final SendMailHelper sendMailHelper;
    private final MailTestHelper mailTestHelper;
    private final NotificationValidator notificationValidator;

    public void testBulkRestoreMail() {
        List<SeleniumAccount> accounts = communityTestInitializer.registerAccounts(new int[]{1, 1});

        SeleniumAccount account = accounts.get(0);
        SeleniumCharacter character = account.getCharacter(0);
        communityTestHelper.goToCommunityPageOf(account, character);

        SeleniumAccount otherAccount = accounts.get(1);
        SeleniumCharacter otherCharacter = otherAccount.getCharacter(0);
        sendMailHelper.sendMailTo(otherCharacter);
        sendMailHelper.sendMailTo(otherCharacter);

        communityTestHelper.goToCommunityPageOf(otherAccount, otherCharacter, 2);

        List<Mail> mails;
        do {
            mails = mailTestHelper.getReceivedMails();
            mails.stream().findFirst().orElseThrow(() -> new RuntimeException("Mail not found.")).archive(notificationValidator);
        } while (mails.size() > 1);

        mailTestHelper.getArchivedMails().forEach(Mail::select);
        mailTestHelper.selectBulkRestoreOption();

        communityPage.getExecuteBulkEditButtonForArchivedMails().click();

        notificationValidator.verifyNotificationVisibility(NOTIFICATION_MAILS_RESTORED);
        assertEquals(0, mailTestHelper.getArchivedMails().size());

        assertEquals(2, mailTestHelper.getReceivedMails().size());
    }
}
