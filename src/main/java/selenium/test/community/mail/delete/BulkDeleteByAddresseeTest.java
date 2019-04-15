package selenium.test.community.mail.delete;

import lombok.Builder;
import org.openqa.selenium.WebDriver;
import selenium.logic.domain.Mail;
import selenium.logic.domain.SeleniumAccount;
import selenium.logic.domain.SeleniumCharacter;
import selenium.logic.page.CommunityPage;
import selenium.logic.validator.NotificationValidator;
import selenium.test.community.helper.CommunityTestHelper;
import selenium.test.community.helper.CommunityTestInitializer;
import selenium.test.community.helper.MailTestHelper;
import selenium.test.community.helper.SendMailHelper;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Builder
public class BulkDeleteByAddresseeTest {
    private static final String NOTIFICATION_MAILS_DELETED = "Üzenetek törölve.";

    private final WebDriver driver;
    private final CommunityTestInitializer communityTestInitializer;
    private final CommunityTestHelper communityTestHelper;
    private final CommunityPage communityPage;
    private final SendMailHelper sendMailHelper;
    private final MailTestHelper mailTestHelper;
    private final NotificationValidator notificationValidator;

    public void testBulkDeleteByAddressee() {
        List<SeleniumAccount> accounts = communityTestInitializer.registerAccounts(new int[]{1, 1});

        SeleniumAccount account = accounts.get(0);
        SeleniumCharacter character = account.getCharacter(0);
        communityTestHelper.goToCommunityPageOf(account, character);

        SeleniumAccount otherAccount = accounts.get(1);
        SeleniumCharacter otherCharacter = otherAccount.getCharacter(0);
        sendMailHelper.sendMailTo(otherCharacter);
        sendMailHelper.sendMailTo(otherCharacter);

        communityTestHelper.goToCommunityPageOf(otherAccount, otherCharacter, 2);

        mailTestHelper.getIncomingMails().forEach(Mail::select);

        mailTestHelper.selectBulkDeleteOptionForReceivedMails();
        communityPage.getExecuteBulkEditButtonForReceivedMails().click();

        driver.switchTo().alert().accept();

        notificationValidator.verifyNotificationVisibility(NOTIFICATION_MAILS_DELETED);

        assertTrue(mailTestHelper.getIncomingMails().isEmpty());

        communityTestHelper.goToCommunityPageOf(account, character);
        assertEquals(2, mailTestHelper.getSentMails().size());
    }
}
