package selenium.test.community;

import org.junit.Ignore;
import org.junit.Test;

import selenium.SeleniumTestApplication;
import selenium.logic.flow.CreateCharacter;
import selenium.logic.flow.Login;
import selenium.logic.flow.Logout;
import selenium.logic.flow.Navigate;
import selenium.logic.flow.Registration;
import selenium.logic.flow.SelectCharacter;
import selenium.logic.page.CommunityPage;
import selenium.logic.page.OverviewPage;
import selenium.logic.validator.NotificationValidator;
import selenium.test.community.mail.archive.ArchiveMailTest;
import selenium.test.community.mail.archive.BulkArchiveMailTest;
import selenium.test.community.mail.delete.BulkDeleteArchivedMailsTest;
import selenium.test.community.mail.delete.BulkDeleteByAddresseeTest;
import selenium.test.community.mail.delete.BulkDeleteBySenderTest;
import selenium.test.community.mail.mark.BulkMarkMailsAsReadTest;
import selenium.test.community.mail.mark.BulkMarkMailsAsUnreadTest;
import selenium.test.community.mail.archive.BulkRestoreMailTest;
import selenium.test.community.mail.delete.DeleteArchivedMailTest;
import selenium.test.community.mail.delete.DeleteByAddresseeTest;
import selenium.test.community.mail.delete.DeleteBySenderTest;
import selenium.test.community.mail.filter.FilterTestShouldNotShowOwnCharacters;
import selenium.test.community.mail.filter.FilterTestShouldShowMatchingCharacters;
import selenium.test.community.mail.mark.MarkMailAsReadTest;
import selenium.test.community.mail.mark.MarkMailAsUnreadTest;
import selenium.test.community.mail.ReadMailTest;
import selenium.test.community.mail.send.ReplyMailTest;
import selenium.test.community.mail.archive.RestoreMailTest;
import selenium.test.community.mail.send.SendMailChangedAddresseeTest;
import selenium.test.community.mail.send.SendMailEmptyAddresseeTest;
import selenium.test.community.mail.send.SendMailEmptyMessageTest;
import selenium.test.community.mail.send.SendMailEmptySubjectTest;
import selenium.test.community.mail.send.SuccessfullySentMailTest;
import selenium.test.community.helper.MailTestHelper;
import selenium.test.community.helper.SendMailHelper;
import selenium.test.community.helper.CommunityTestHelper;
import selenium.test.community.helper.CommunityTestInitializer;

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

        communityPage = new CommunityPage(driver);
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
            .notificationValidator(notificationValidator)
            .build()
            .testBulkMarkMailsAsRead();
    }

    @Test
    @Ignore
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
    @Ignore
    public void testBulkMarkMailsAsUnread() {
        BulkMarkMailsAsUnreadTest.builder()
            .communityTestInitializer(communityTestInitializer)
            .communityTestHelper(communityTestHelper)
            .communityPage(communityPage)
            .sendMailHelper(sendMailHelper)
            .mailTestHelper(mailTestHelper)
            .notificationValidator(notificationValidator)
            .build()
            .testBulkMarkMailsAsUnread();
    }

    @Test
    @Ignore
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
