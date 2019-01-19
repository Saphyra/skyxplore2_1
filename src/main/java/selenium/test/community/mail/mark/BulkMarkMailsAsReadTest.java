package selenium.test.community.mail.mark;

import lombok.Builder;
import selenium.logic.domain.Mail;
import selenium.logic.domain.SeleniumAccount;
import selenium.logic.domain.SeleniumCharacter;
import selenium.logic.page.CommunityPage;
import selenium.logic.validator.NotificationValidator;
import selenium.test.community.helper.MailTestHelper;
import selenium.test.community.helper.SendMailHelper;
import selenium.test.community.helper.CommunityTestHelper;
import selenium.test.community.helper.CommunityTestInitializer;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Builder
public class BulkMarkMailsAsReadTest {
    private static final String NOTIFICATION_MAILS_MARKED_AS_READ = "Üzenetek olvasottnak jelölve.";

    private final CommunityTestInitializer communityTestInitializer;
    private final CommunityTestHelper communityTestHelper;
    private final CommunityPage communityPage;
    private final SendMailHelper sendMailHelper;
    private final MailTestHelper mailTestHelper;
    private final NotificationValidator notificationValidator;

    public void testBulkMarkMailsAsRead() {
        List<SeleniumAccount> accounts = communityTestInitializer.registerAccounts(new int[]{1, 1});

        SeleniumAccount account = accounts.get(0);
        SeleniumCharacter character = account.getCharacter(0);
        communityTestHelper.goToCommunityPageOf(account, character);

        SeleniumAccount otherAccount = accounts.get(1);
        SeleniumCharacter otherCharacter = otherAccount.getCharacter(0);
        sendMailHelper.sendMailTo(otherCharacter);
        sendMailHelper.sendMailTo(otherCharacter);

        communityTestHelper.goToCommunityPageOf(otherAccount, otherCharacter, 2);

        mailTestHelper.getReceivedMails().forEach(Mail::select);

        mailTestHelper.selectBulkMarkAsReadOption();
        communityPage.getExecuteBulkEditButtonForReceivedMails().click();

        notificationValidator.verifyNotificationVisibility(NOTIFICATION_MAILS_MARKED_AS_READ);
        mailTestHelper.getReceivedMails().forEach(mail -> assertTrue(mail.isRead()));

        assertEquals(0, mailTestHelper.getNumberOfUnreadMails());
    }
}
