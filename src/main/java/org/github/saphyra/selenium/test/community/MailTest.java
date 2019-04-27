package org.github.saphyra.selenium.test.community;

import org.junit.Test;
import org.github.saphyra.selenium.SeleniumTestApplication;
import org.github.saphyra.selenium.logic.flow.CreateCharacter;
import org.github.saphyra.selenium.logic.flow.Login;
import org.github.saphyra.selenium.logic.flow.Logout;
import org.github.saphyra.selenium.logic.flow.Navigate;
import org.github.saphyra.selenium.logic.flow.Registration;
import org.github.saphyra.selenium.logic.flow.SelectCharacter;
import org.github.saphyra.selenium.logic.page.CommunityPage;
import org.github.saphyra.selenium.logic.page.OverviewPage;
import org.github.saphyra.selenium.logic.validator.NotificationValidator;
import org.github.saphyra.selenium.test.community.helper.CommunityTestHelper;
import org.github.saphyra.selenium.test.community.helper.CommunityTestInitializer;
import org.github.saphyra.selenium.test.community.helper.MailTestHelper;
import org.github.saphyra.selenium.test.community.helper.SendMailHelper;
import org.github.saphyra.selenium.test.community.mail.mark.ReadMailTest;
import org.github.saphyra.selenium.test.community.mail.archive.ArchiveMailTest;
import org.github.saphyra.selenium.test.community.mail.archive.BulkArchiveMailTest;
import org.github.saphyra.selenium.test.community.mail.archive.BulkRestoreMailTest;
import org.github.saphyra.selenium.test.community.mail.archive.RestoreMailTest;
import org.github.saphyra.selenium.test.community.mail.delete.BulkDeleteArchivedMailsTest;
import org.github.saphyra.selenium.test.community.mail.delete.BulkDeleteByAddresseeTest;
import org.github.saphyra.selenium.test.community.mail.delete.BulkDeleteBySenderTest;
import org.github.saphyra.selenium.test.community.mail.delete.DeleteArchivedMailTest;
import org.github.saphyra.selenium.test.community.mail.delete.DeleteByAddresseeTest;
import org.github.saphyra.selenium.test.community.mail.delete.DeleteBySenderTest;
import org.github.saphyra.selenium.test.community.mail.filter.FilterTestShouldNotShowOwnCharacters;
import org.github.saphyra.selenium.test.community.mail.filter.FilterTestShouldShowMatchingCharacters;
import org.github.saphyra.selenium.test.community.mail.mark.BulkMarkMailsAsReadTest;
import org.github.saphyra.selenium.test.community.mail.mark.BulkMarkMailsAsUnreadTest;
import org.github.saphyra.selenium.test.community.mail.mark.MarkMailAsReadTest;
import org.github.saphyra.selenium.test.community.mail.mark.MarkMailAsUnreadTest;
import org.github.saphyra.selenium.test.community.mail.send.ReplyMailTest;
import org.github.saphyra.selenium.test.community.mail.send.SendMailChangedAddresseeTest;
import org.github.saphyra.selenium.test.community.mail.send.SendMailEmptyAddresseeTest;
import org.github.saphyra.selenium.test.community.mail.send.SendMailEmptyMessageTest;
import org.github.saphyra.selenium.test.community.mail.send.SendMailEmptySubjectTest;
import org.github.saphyra.selenium.test.community.mail.send.SuccessfullySentMailTest;

public class MailTest extends SeleniumTestApplication {
    private CommunityTestInitializer communityTestInitializer;
    private CommunityTestHelper communityTestHelper;
    private CommunityPage communityPage;
    private MailTestHelper mailTestHelper;
    private SendMailHelper sendMailHelper;
    private NotificationValidator notificationValidator;

    @Override
    protected void init() {
        communityTestHelper = new CommunityTestHelper(
            new Login(driver, messageCodes),
            new SelectCharacter(driver),
            new OverviewPage(driver),
            new Navigate(driver)
        );

        communityTestInitializer = new CommunityTestInitializer(
            new Registration(driver, messageCodes),
            new CreateCharacter(driver, messageCodes),
            new Logout(driver, messageCodes)
        );

        communityPage = new CommunityPage(driver, messageCodes);
        mailTestHelper = new MailTestHelper(communityPage, driver, messageCodes);
        notificationValidator = new NotificationValidator(driver);
        sendMailHelper = new SendMailHelper(communityPage, notificationValidator, messageCodes);
    }

    @Test
    public void testFilterShouldNotShowOwnCharacters() {
        FilterTestShouldNotShowOwnCharacters.builder()
            .communityTestInitializer(communityTestInitializer)
            .communityTestHelper(communityTestHelper)
            .communityPage(communityPage)
            .mailTestHelper(mailTestHelper)
            .build()
            .testFilterShouldNotShowOwnCharacters();
    }

    @Test
    public void testFilterShouldShowMatchingCharacters() {
        FilterTestShouldShowMatchingCharacters.builder()
            .communityTestInitializer(communityTestInitializer)
            .communityTestHelper(communityTestHelper)
            .communityPage(communityPage)
            .mailTestHelper(mailTestHelper)
            .build()
            .testFilterShouldShowMatchingCharacters();
    }

    @Test
    public void testSendMailEmptySubject() {
        SendMailEmptySubjectTest.builder()
            .communityTestInitializer(communityTestInitializer)
            .communityTestHelper(communityTestHelper)
            .communityPage(communityPage)
            .sendMailHelper(sendMailHelper)
            .messageCodes(messageCodes)
            .build()
            .testSendMailEmptySubject();
    }

    @Test
    public void testSendMailEmptyMessage() {
        SendMailEmptyMessageTest.builder()
            .communityTestInitializer(communityTestInitializer)
            .communityTestHelper(communityTestHelper)
            .communityPage(communityPage)
            .sendMailHelper(sendMailHelper)
            .messageCodes(messageCodes)
            .build()
            .testSendMailEmptyMessage();
    }

    @Test
    public void testSendMailEmptyAddressee() {
        SendMailEmptyAddresseeTest.builder()
            .communityTestInitializer(communityTestInitializer)
            .communityTestHelper(communityTestHelper)
            .communityPage(communityPage)
            .sendMailHelper(sendMailHelper)
            .messageCodes(messageCodes)
            .build()
            .testSendMailEmptyAddressee();
    }

    @Test
    public void testSendMailChangedAddressee() {
        SendMailChangedAddresseeTest.builder()
            .communityTestInitializer(communityTestInitializer)
            .communityTestHelper(communityTestHelper)
            .communityPage(communityPage)
            .sendMailHelper(sendMailHelper)
            .messageCodes(messageCodes)
            .build()
            .testSendMailChangedAddressee();
    }

    @Test
    public void testSuccessfullySentMail() {
        SuccessfullySentMailTest.builder()
            .communityTestInitializer(communityTestInitializer)
            .communityTestHelper(communityTestHelper)
            .communityPage(communityPage)
            .sendMailHelper(sendMailHelper)
            .mailTestHelper(mailTestHelper)
            .messageCodes(messageCodes)
            .build()
            .testSuccessfullySentMail();
    }

    @Test
    public void testReadMail() {
        ReadMailTest.builder()
            .communityTestInitializer(communityTestInitializer)
            .communityTestHelper(communityTestHelper)
            .communityPage(communityPage)
            .sendMailHelper(sendMailHelper)
            .mailTestHelper(mailTestHelper)
            .build()
            .testReadMail();
    }

    @Test
    public void testArchiveMail() {
        ArchiveMailTest.builder()
            .communityTestInitializer(communityTestInitializer)
            .communityTestHelper(communityTestHelper)
            .communityPage(communityPage)
            .sendMailHelper(sendMailHelper)
            .mailTestHelper(mailTestHelper)
            .notificationValidator(notificationValidator)
            .build()
            .testArchiveMail();
    }

    @Test
    public void testBulkArchiveMail() {
        BulkArchiveMailTest.builder()
            .communityTestInitializer(communityTestInitializer)
            .communityTestHelper(communityTestHelper)
            .communityPage(communityPage)
            .sendMailHelper(sendMailHelper)
            .mailTestHelper(mailTestHelper)
            .notificationValidator(notificationValidator)
            .messageCodes(messageCodes)
            .build()
            .testBulkArchiveMail();
    }

    @Test
    public void testRestoreMail() {
        RestoreMailTest.builder()
            .communityTestInitializer(communityTestInitializer)
            .communityTestHelper(communityTestHelper)
            .communityPage(communityPage)
            .sendMailHelper(sendMailHelper)
            .mailTestHelper(mailTestHelper)
            .notificationValidator(notificationValidator)
            .build()
            .testRestoreMail();
    }

    @Test
    public void testBulkRestoreMail() {
        BulkRestoreMailTest.builder()
            .communityTestInitializer(communityTestInitializer)
            .communityTestHelper(communityTestHelper)
            .communityPage(communityPage)
            .sendMailHelper(sendMailHelper)
            .mailTestHelper(mailTestHelper)
            .notificationValidator(notificationValidator)
            .messageCodes(messageCodes)
            .build()
            .testBulkRestoreMail();
    }

    @Test
    public void testDeleteBySender() {
        DeleteBySenderTest.builder()
            .communityTestInitializer(communityTestInitializer)
            .communityTestHelper(communityTestHelper)
            .communityPage(communityPage)
            .sendMailHelper(sendMailHelper)
            .mailTestHelper(mailTestHelper)
            .notificationValidator(notificationValidator)
            .build()
            .testDeleteBySender();
    }

    @Test
    public void testBulkDeleteBySender() {
        BulkDeleteBySenderTest.builder()
            .driver(driver)
            .communityTestInitializer(communityTestInitializer)
            .communityTestHelper(communityTestHelper)
            .communityPage(communityPage)
            .sendMailHelper(sendMailHelper)
            .mailTestHelper(mailTestHelper)
            .notificationValidator(notificationValidator)
            .messageCodes(messageCodes)
            .build()
            .testBulkDeleteBySender();
    }

    @Test
    public void testDeleteByAddressee() {
        DeleteByAddresseeTest.builder()
            .communityTestInitializer(communityTestInitializer)
            .communityTestHelper(communityTestHelper)
            .communityPage(communityPage)
            .sendMailHelper(sendMailHelper)
            .mailTestHelper(mailTestHelper)
            .notificationValidator(notificationValidator)
            .build()
            .testDeleteByAddressee();
    }

    @Test
    public void testBulkDeleteByAddressee() {
        BulkDeleteByAddresseeTest.builder()
            .driver(driver)
            .communityTestInitializer(communityTestInitializer)
            .communityTestHelper(communityTestHelper)
            .communityPage(communityPage)
            .sendMailHelper(sendMailHelper)
            .mailTestHelper(mailTestHelper)
            .notificationValidator(notificationValidator)
            .messageCodes(messageCodes)
            .build()
            .testBulkDeleteByAddressee();
    }

    @Test
    public void testDeleteArchivedMail() {
        DeleteArchivedMailTest.builder()
            .communityTestInitializer(communityTestInitializer)
            .communityTestHelper(communityTestHelper)
            .communityPage(communityPage)
            .sendMailHelper(sendMailHelper)
            .mailTestHelper(mailTestHelper)
            .notificationValidator(notificationValidator)
            .build()
            .testDeleteArchivedMail();
    }

    @Test
    public void testBulkDeleteArchivedMails() {
        BulkDeleteArchivedMailsTest.builder()
            .driver(driver)
            .communityTestInitializer(communityTestInitializer)
            .communityTestHelper(communityTestHelper)
            .communityPage(communityPage)
            .sendMailHelper(sendMailHelper)
            .mailTestHelper(mailTestHelper)
            .notificationValidator(notificationValidator)
            .messageCodes(messageCodes)
            .build()
            .testBulkDeleteArchivedMails();
    }

    @Test
    public void testMarkMailAsRead() {
        MarkMailAsReadTest.builder()
            .communityTestInitializer(communityTestInitializer)
            .communityTestHelper(communityTestHelper)
            .communityPage(communityPage)
            .sendMailHelper(sendMailHelper)
            .mailTestHelper(mailTestHelper)
            .build()
            .testMarkMailAsRead();
    }

    @Test
    public void testBulkMarkMailsAsRead() {
        BulkMarkMailsAsReadTest.builder()
            .communityTestInitializer(communityTestInitializer)
            .communityTestHelper(communityTestHelper)
            .communityPage(communityPage)
            .sendMailHelper(sendMailHelper)
            .mailTestHelper(mailTestHelper)
            .build()
            .testBulkMarkMailsAsRead();
    }

    @Test
    public void testMarkMailAsUnread() {
        MarkMailAsUnreadTest.builder()
            .driver(driver)
            .communityTestInitializer(communityTestInitializer)
            .communityTestHelper(communityTestHelper)
            .communityPage(communityPage)
            .sendMailHelper(sendMailHelper)
            .mailTestHelper(mailTestHelper)
            .build()
            .testMarkMailAsUnread();
    }

    @Test
    public void testBulkMarkMailsAsUnread() {
        BulkMarkMailsAsUnreadTest.builder()
            .communityTestInitializer(communityTestInitializer)
            .communityTestHelper(communityTestHelper)
            .communityPage(communityPage)
            .sendMailHelper(sendMailHelper)
            .mailTestHelper(mailTestHelper)
            .build()
            .testBulkMarkMailsAsUnread();
    }

    @Test
    public void testReplyMail() {
        ReplyMailTest.builder()
            .communityTestInitializer(communityTestInitializer)
            .communityTestHelper(communityTestHelper)
            .communityPage(communityPage)
            .sendMailHelper(sendMailHelper)
            .mailTestHelper(mailTestHelper)
            .build()
            .testReplyMail();
    }
}
