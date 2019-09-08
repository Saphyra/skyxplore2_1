package com.github.saphyra.selenium.test.community.mail.archive;

import com.github.saphyra.selenium.logic.domain.Mail;
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
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;

import java.util.List;

import static com.github.saphyra.selenium.logic.util.LocalizationUtil.getAdditionalContent;
import static org.assertj.core.api.Assertions.assertThat;

@Builder
@Slf4j
public class BulkArchiveMailTest {
    private static final String OTHER_SUBJECT = "other_subject";
    private static final String MESSAGE_CODE_MAILS_ARCHIVED = "mails-archived";

    private final WebDriver driver;
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

        sendMailHelper.openWriteMailPage();

        sendMailHelper.setSubject(OTHER_SUBJECT)
            .setAddressee(otherCharacter)
            .setMessage()
            .sendMail();

        communityTestHelper.goToCommunityPageOf(otherAccount, otherCharacter, 2);

        communityPage.getSelectAllIncomingMailsButton().click();

        mailTestHelper.selectBulkArchiveOption();
        communityPage.getExecuteBulkEditButtonForReceivedMails().click();
        notificationValidator.verifyNotificationVisibility(getAdditionalContent(driver, Page.COMMUNITY, MESSAGE_CODE_MAILS_ARCHIVED));

        mailTestHelper.verifyNoIncomingMails();

        List<Mail> archivedMails = mailTestHelper.getArchivedMails();
        assertThat(archivedMails).hasSize(2);

        verifyContainsSubject(archivedMails, SendMailHelper.DEFAULT_SUBJECT);
        verifyContainsSubject(archivedMails, OTHER_SUBJECT);
    }

    private void verifyContainsSubject(List<Mail> archivedMails, String otherSubject) {
        boolean result = archivedMails.stream()
            .anyMatch(mail -> mail.getSubject().equals(otherSubject));
        assertThat(result).isTrue();
    }
}
