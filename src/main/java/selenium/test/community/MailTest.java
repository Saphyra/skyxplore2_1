package selenium.test.community;

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
import selenium.test.community.mail.ArchiveMailTest;
import selenium.test.community.mail.BulkArchiveMailTest;
import selenium.test.community.mail.BulkDeleteByAddresseeTest;
import selenium.test.community.mail.BulkDeleteBySenderTest;
import selenium.test.community.mail.BulkRestoreMailTest;
import selenium.test.community.mail.DeleteArchivedMailTest;
import selenium.test.community.mail.DeleteByAddresseeTest;
import selenium.test.community.mail.DeleteBySenderTest;
import selenium.test.community.mail.FilterTestShouldNotShowOwnCharacters;
import selenium.test.community.mail.FilterTestShouldShowMatchingCharacters;
import selenium.test.community.mail.ReadMailTest;
import selenium.test.community.mail.RestoreMailTest;
import selenium.test.community.mail.SendMailChangedAddresseeTest;
import selenium.test.community.mail.SendMailEmptyAddresseeTest;
import selenium.test.community.mail.SendMailEmptyMessageTest;
import selenium.test.community.mail.SendMailEmptySubjectTest;
import selenium.test.community.mail.SuccessfullySentMailTest;
import selenium.test.community.mail.helper.MailTestHelper;
import selenium.test.community.mail.helper.SendMailHelper;
import selenium.test.community.util.CommunityTestHelper;
import selenium.test.community.util.CommunityTestInitializer;

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
        sendMailHelper = new SendMailHelper(communityPage, notificationValidator);
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
    public void testDeleteArchivedMail(){
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
}
