package com.github.saphyra.selenium.test.community;

import org.junit.Test;
import com.github.saphyra.selenium.SeleniumTestApplication;
import com.github.saphyra.selenium.logic.flow.CreateCharacter;
import com.github.saphyra.selenium.logic.flow.Login;
import com.github.saphyra.selenium.logic.flow.Logout;
import com.github.saphyra.selenium.logic.flow.Navigate;
import com.github.saphyra.selenium.logic.flow.Registration;
import com.github.saphyra.selenium.logic.flow.SelectCharacter;
import com.github.saphyra.selenium.logic.page.CommunityPage;
import com.github.saphyra.selenium.logic.page.OverviewPage;
import com.github.saphyra.selenium.logic.validator.NotificationValidator;
import com.github.saphyra.selenium.test.community.helper.CommunityTestHelper;
import com.github.saphyra.selenium.test.community.helper.CommunityTestInitializer;
import com.github.saphyra.selenium.test.community.helper.MailTestHelper;
import com.github.saphyra.selenium.test.community.helper.SendMailHelper;
import com.github.saphyra.selenium.test.community.mail.mark.ReadMailTest;
import com.github.saphyra.selenium.test.community.mail.archive.ArchiveMailTest;
import com.github.saphyra.selenium.test.community.mail.archive.BulkArchiveMailTest;
import com.github.saphyra.selenium.test.community.mail.archive.BulkRestoreMailTest;
import com.github.saphyra.selenium.test.community.mail.archive.RestoreMailTest;
import com.github.saphyra.selenium.test.community.mail.delete.BulkDeleteArchivedMailsTest;
import com.github.saphyra.selenium.test.community.mail.delete.BulkDeleteByAddresseeTest;
import com.github.saphyra.selenium.test.community.mail.delete.BulkDeleteBySenderTest;
import com.github.saphyra.selenium.test.community.mail.delete.DeleteArchivedMailTest;
import com.github.saphyra.selenium.test.community.mail.delete.DeleteByAddresseeTest;
import com.github.saphyra.selenium.test.community.mail.delete.DeleteBySenderTest;
import com.github.saphyra.selenium.test.community.mail.filter.FilterTestShouldNotShowOwnCharacters;
import com.github.saphyra.selenium.test.community.mail.filter.FilterTestShouldShowMatchingCharacters;
import com.github.saphyra.selenium.test.community.mail.mark.BulkMarkMailsAsReadTest;
import com.github.saphyra.selenium.test.community.mail.mark.BulkMarkMailsAsUnreadTest;
import com.github.saphyra.selenium.test.community.mail.mark.MarkMailAsReadTest;
import com.github.saphyra.selenium.test.community.mail.mark.MarkMailAsUnreadTest;
import com.github.saphyra.selenium.test.community.mail.send.ReplyMailTest;
import com.github.saphyra.selenium.test.community.mail.send.SendMailChangedAddresseeTest;
import com.github.saphyra.selenium.test.community.mail.send.SendMailEmptyAddresseeTest;
import com.github.saphyra.selenium.test.community.mail.send.SendMailEmptyMessageTest;
import com.github.saphyra.selenium.test.community.mail.send.SendMailEmptySubjectTest;
import com.github.saphyra.selenium.test.community.mail.send.SuccessfullySentMailTest;

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
            new Login(driver),
            new SelectCharacter(driver),
            new OverviewPage(driver),
            new Navigate(driver)
        );

        communityTestInitializer = new CommunityTestInitializer(
            new Registration(driver),
            new CreateCharacter(driver),
            new Logout(driver)
        );

        communityPage = new CommunityPage(driver);
        mailTestHelper = new MailTestHelper(communityPage, driver);
        notificationValidator = new NotificationValidator(driver);
        sendMailHelper = new SendMailHelper(driver, communityPage, notificationValidator);
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
