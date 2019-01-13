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
import selenium.test.community.mail.FilterTestShouldNotShowOwnCharacters;
import selenium.test.community.mail.FilterTestShouldShowMatchingCharacters;
import selenium.test.community.mail.ReadMailTest;
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
        mailTestHelper = new MailTestHelper(communityPage);
        sendMailHelper = new SendMailHelper(communityPage, new NotificationValidator(driver));
    }

    @Test
    public void testFilterShouldNotShowOwnCharacters(){
        FilterTestShouldNotShowOwnCharacters.builder()
            .communityTestInitializer(communityTestInitializer)
            .communityTestHelper(communityTestHelper)
            .communityPage(communityPage)
            .mailTestHelper(mailTestHelper)
            .build()
            .testFilterShouldNotShowOwnCharacters();
    }

    @Test
    public void testFilterShouldShowMatchingCharacters(){
        FilterTestShouldShowMatchingCharacters.builder()
            .communityTestInitializer(communityTestInitializer)
            .communityTestHelper(communityTestHelper)
            .communityPage(communityPage)
            .mailTestHelper(mailTestHelper)
            .build()
            .testFilterShouldShowMatchingCharacters();
    }

    @Test
    public void testSendMailEmptySubject(){
        SendMailEmptySubjectTest.builder()
            .communityTestInitializer(communityTestInitializer)
            .communityTestHelper(communityTestHelper)
            .communityPage(communityPage)
            .sendMailHelper(sendMailHelper)
            .build()
            .testSendMailEmptySubject();
    }

    @Test
    public void testSendMailEmptyMessage(){
        SendMailEmptyMessageTest.builder()
            .communityTestInitializer(communityTestInitializer)
            .communityTestHelper(communityTestHelper)
            .communityPage(communityPage)
            .sendMailHelper(sendMailHelper)
            .build()
            .testSendMailEmptyMessage();
    }

    @Test
    public void testSendMailEmptyAddressee(){
        SendMailEmptyAddresseeTest.builder()
            .communityTestInitializer(communityTestInitializer)
            .communityTestHelper(communityTestHelper)
            .communityPage(communityPage)
            .sendMailHelper(sendMailHelper)
            .build()
            .testSendMailEmptyAddressee();
    }

    @Test
    public void testSendMailChangedAddressee(){
        SendMailChangedAddresseeTest.builder()
            .communityTestInitializer(communityTestInitializer)
            .communityTestHelper(communityTestHelper)
            .communityPage(communityPage)
            .sendMailHelper(sendMailHelper)
            .build()
            .testSendMailChangedAddressee();
    }

    @Test
    public void testSuccessfullySentMail(){
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
    public void testReadMail(){
        ReadMailTest.builder()
            .communityTestInitializer(communityTestInitializer)
            .communityTestHelper(communityTestHelper)
            .communityPage(communityPage)
            .sendMailHelper(sendMailHelper)
            .mailTestHelper(mailTestHelper)
            .build()
            .testReadMail();
    }
}
