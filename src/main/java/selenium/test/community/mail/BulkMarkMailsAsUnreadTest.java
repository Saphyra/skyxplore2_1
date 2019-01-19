package selenium.test.community.mail;

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
import static org.junit.Assert.assertFalse;

@Builder
public class BulkMarkMailsAsUnreadTest {
    private static final String NOTIFICATION_MAILS_MARKED_AS_UNREAD = "Üzenetek olvasatlannak jelölve.";

    private final CommunityTestInitializer communityTestInitializer;
    private final CommunityTestHelper communityTestHelper;
    private final CommunityPage communityPage;
    private final SendMailHelper sendMailHelper;
    private final MailTestHelper mailTestHelper;
    private final NotificationValidator notificationValidator;

    public void testBulkMarkMailsAsUnread() {
        List<SeleniumAccount> accounts = communityTestInitializer.registerAccounts(new int[]{1, 1});

        SeleniumAccount account = accounts.get(0);
        SeleniumCharacter character = account.getCharacter(0);
        communityTestHelper.goToCommunityPageOf(account, character);

        SeleniumAccount otherAccount = accounts.get(1);
        SeleniumCharacter otherCharacter = otherAccount.getCharacter(0);
        sendMailHelper.sendMailTo(otherCharacter);
        sendMailHelper.sendMailTo(otherCharacter);

        communityTestHelper.goToCommunityPageOf(otherAccount, otherCharacter, 2);

        mailTestHelper.getReceivedMails().forEach(Mail::read);

        mailTestHelper.getReceivedMails().forEach(Mail::select);

        mailTestHelper.selectBulkMarkAsUnreadOption();
        communityPage.getExecuteBulkEditButtonForReceivedMails().click();

        notificationValidator.verifyNotificationVisibility(NOTIFICATION_MAILS_MARKED_AS_UNREAD);

        mailTestHelper.getReceivedMails().forEach(mail -> assertFalse(mail.isRead()));

        assertEquals(2, mailTestHelper.getNumberOfUnreadMails());
    }
}
