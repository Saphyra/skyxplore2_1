package org.github.saphyra.selenium.test.community.mail.archive;

import static org.assertj.core.api.Assertions.assertThat;
import static org.github.saphyra.selenium.logic.util.WaitUtil.waitUntil;

import java.util.List;

import lombok.Builder;
import org.github.saphyra.selenium.logic.domain.MessageCodes;
import org.github.saphyra.selenium.logic.domain.SeleniumAccount;
import org.github.saphyra.selenium.logic.domain.SeleniumCharacter;
import org.github.saphyra.selenium.logic.page.CommunityPage;
import org.github.saphyra.selenium.logic.validator.NotificationValidator;
import org.github.saphyra.selenium.test.community.helper.CommunityTestHelper;
import org.github.saphyra.selenium.test.community.helper.CommunityTestInitializer;
import org.github.saphyra.selenium.test.community.helper.MailTestHelper;
import org.github.saphyra.selenium.test.community.helper.SendMailHelper;

@Builder
public class BulkRestoreMailTest {
    private static final String MESSAGE_CODE_MAILS_RESTORED = "MAILS_RESTORED";

    private final CommunityTestInitializer communityTestInitializer;
    private final CommunityTestHelper communityTestHelper;
    private final CommunityPage communityPage;
    private final SendMailHelper sendMailHelper;
    private final MailTestHelper mailTestHelper;
    private final NotificationValidator notificationValidator;
    private final MessageCodes messageCodes;

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

        notificationValidator.verifyNotificationVisibility(messageCodes.get(MESSAGE_CODE_MAILS_RESTORED));
        mailTestHelper.verifyNoArchivedMails();

        assertThat(mailTestHelper.getIncomingMails()).hasSize(2);
    }
}
