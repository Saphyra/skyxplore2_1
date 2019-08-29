package com.github.saphyra.selenium.test.community.mail.delete;

import com.github.saphyra.selenium.logic.domain.SeleniumAccount;
import com.github.saphyra.selenium.logic.domain.SeleniumCharacter;
import com.github.saphyra.selenium.logic.domain.localization.Page;
import com.github.saphyra.selenium.logic.page.CommunityPage;
import com.github.saphyra.selenium.logic.validator.NotificationValidator;
import com.github.saphyra.selenium.test.community.helper.CommunityTestHelper;
import com.github.saphyra.selenium.test.community.helper.CommunityTestInitializer;
import com.github.saphyra.selenium.test.community.helper.MailTestHelper;
import com.github.saphyra.selenium.test.community.helper.SendMailHelper;
import lombok.Builder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static com.github.saphyra.selenium.logic.util.LocalizationUtil.getAdditionalContent;
import static org.junit.Assert.assertEquals;

@Builder
public class BulkDeleteByAddresseeTest {
    private static final String MESSAGE_CODE_MAILS_DELETED = "mails-deleted";

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

        communityPage.getSelectAllIncomingMailsButton().click();

        mailTestHelper.selectBulkDeleteOptionForReceivedMails();
        communityPage.getExecuteBulkEditButtonForReceivedMails().click();

        new WebDriverWait(driver, 10).until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();

        notificationValidator.verifyNotificationVisibility(getAdditionalContent(driver, Page.COMMUNITY, MESSAGE_CODE_MAILS_DELETED));

        mailTestHelper.verifyNoIncomingMails();

        communityTestHelper.goToCommunityPageOf(account, character);
        assertEquals(2, mailTestHelper.getSentMails().size());
    }
}
