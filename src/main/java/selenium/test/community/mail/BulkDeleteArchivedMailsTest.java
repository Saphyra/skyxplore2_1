package selenium.test.community.mail;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.WebDriver;

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

@Builder
public class BulkDeleteArchivedMailsTest {
    private static final String NOTIFICATION_MAILS_DELETED = "Üzenetek törölve.";

    private final WebDriver driver;
    private final CommunityTestInitializer communityTestInitializer;
    private final CommunityTestHelper communityTestHelper;
    private final CommunityPage communityPage;
    private final SendMailHelper sendMailHelper;
    private final MailTestHelper mailTestHelper;
    private final NotificationValidator notificationValidator;

    public void testBulkDeleteArchivedMails() {
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
        mailTestHelper.selectBulkArchiveOption();
        communityPage.getExecuteBulkEditButtonForReceivedMails().click();

        mailTestHelper.getArchivedMails().forEach(Mail::select);

        mailTestHelper.selectBulkDeleteOptionForArchivedMails();
        communityPage.getExecuteBulkEditButtonForArchivedMails().click();

        driver.switchTo().alert().accept();

        notificationValidator.verifyNotificationVisibility(NOTIFICATION_MAILS_DELETED);

        assertTrue(mailTestHelper.getArchivedMails().isEmpty());

        communityTestHelper.goToCommunityPageOf(account, character);
        assertEquals(2, mailTestHelper.getSentMails().size());
    }
}
