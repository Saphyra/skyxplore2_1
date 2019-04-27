package selenium.test.community.mail.delete;

import lombok.Builder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.logic.domain.MessageCodes;
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

@Builder
public class BulkDeleteBySenderTest {
    private static final String MESSAGE_CODE_MAILS_DELETED = "MAILS_DELETED";

    private final WebDriver driver;
    private final CommunityTestInitializer communityTestInitializer;
    private final CommunityTestHelper communityTestHelper;
    private final CommunityPage communityPage;
    private final SendMailHelper sendMailHelper;
    private final MailTestHelper mailTestHelper;
    private final NotificationValidator notificationValidator;
    private final MessageCodes messageCodes;

    public void testBulkDeleteBySender() {
        List<SeleniumAccount> accounts = communityTestInitializer.registerAccounts(new int[]{1, 1});

        SeleniumAccount account = accounts.get(0);
        SeleniumCharacter character = account.getCharacter(0);
        communityTestHelper.goToCommunityPageOf(account, character);

        SeleniumAccount otherAccount = accounts.get(1);
        SeleniumCharacter otherCharacter = otherAccount.getCharacter(0);
        sendMailHelper.sendMailTo(otherCharacter);
        sendMailHelper.sendMailTo(otherCharacter);

        communityPage.getSentMailsPageButton().click();
        communityPage.getSelectAllSentMailsButton().click();

        mailTestHelper.selectBulkDeleteOptionForSentMails();
        communityPage.getExecuteBulkEditButtonForSentMails().click();

        new WebDriverWait(driver, 10).until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
        notificationValidator.verifyNotificationVisibility(messageCodes.get(MESSAGE_CODE_MAILS_DELETED));

        mailTestHelper.verifyNoSentMails();

        communityTestHelper.goToCommunityPageOf(otherAccount, otherCharacter, 2);
        assertEquals(2, mailTestHelper.getIncomingMails().size());
    }
}
