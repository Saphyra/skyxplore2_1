package com.github.saphyra.selenium.test.community.mail.archive;

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

import java.util.List;

import static com.github.saphyra.selenium.logic.util.LocalizationUtil.getAdditionalContent;
import static com.github.saphyra.selenium.logic.util.WaitUtil.waitUntil;
import static org.assertj.core.api.Assertions.assertThat;

@Builder
public class BulkRestoreMailTest {
    private static final String MESSAGE_CODE_MAILS_RESTORED = "mails-restored";

    private final WebDriver driver;
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


        communityPage.getSelectAllIncomingMailsButton().click();
        mailTestHelper.selectBulkArchiveOption();
        communityPage.getExecuteBulkEditButtonForReceivedMails().click();

        communityPage.getArchivedMailsPageButton().click();
        waitUntil(() -> mailTestHelper.getArchivedMails().size() > 0, "Waiting until archived mails loaded");
        communityPage.getSelectAllArchivedMailsButton().click();
        mailTestHelper.selectBulkRestoreOption();

        communityPage.getExecuteBulkEditButtonForArchivedMails().click();

        notificationValidator.verifyNotificationVisibility(getAdditionalContent(driver, Page.COMMUNITY, MESSAGE_CODE_MAILS_RESTORED));
        mailTestHelper.verifyNoArchivedMails();

        assertThat(mailTestHelper.getIncomingMails()).hasSize(2);
    }
}
