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
import selenium.test.community.block.BlockCharacterShouldUnableReplyMail;
import selenium.test.community.block.BlockCharacterTestShouldDeleteFriendRequest;
import selenium.test.community.block.BlockCharacterTestShouldDeleteFriendship;
import selenium.test.community.block.BlockCharacterTestShouldNotSendFriendRequest;
import selenium.test.community.block.BlockCharacterTestShouldNotSendMail;
import selenium.test.community.block.FilterTestShouldNotShowAlreadyBlocked;
import selenium.test.community.block.FilterTestShouldNotShowOwnCharacters;
import selenium.test.community.block.FilterTestShouldShowOnlyMatchingCharacter;
import selenium.test.community.block.UnblockCharacterTest;
import selenium.test.community.helper.BlockTestHelper;
import selenium.test.community.helper.CommunityTestHelper;
import selenium.test.community.helper.CommunityTestInitializer;
import selenium.test.community.helper.FriendshipTestHelper;
import selenium.test.community.helper.MailTestHelper;
import selenium.test.community.helper.SendMailHelper;

public class BlockCharacterTest extends SeleniumTestApplication {
    private CommunityTestInitializer communityTestInitializer;
    private CommunityTestHelper communityTestHelper;
    private BlockTestHelper blockTestHelper;
    private SendMailHelper sendMailHelper;
    private FriendshipTestHelper friendshipTestHelper;
    private CommunityPage communityPage;
    private NotificationValidator notificationValidator;
    private MailTestHelper mailTestHelper;

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
            new CreateCharacter(driver),
            new Logout(driver, messageCodes)
        );

        communityPage = new CommunityPage(driver);
        notificationValidator = new NotificationValidator(driver);

        blockTestHelper = new BlockTestHelper(driver, communityPage, notificationValidator);

        sendMailHelper = new SendMailHelper(communityPage, notificationValidator);
        friendshipTestHelper = new FriendshipTestHelper(communityPage, notificationValidator);
        mailTestHelper = new MailTestHelper(communityPage, driver);
    }

    @Test
    public void testFilterShouldNotShowOwnCharacters() {
        FilterTestShouldNotShowOwnCharacters.builder()
            .blockTestHelper(blockTestHelper)
            .communityTestHelper(communityTestHelper)
            .communityTestInitializer(communityTestInitializer)
            .build()
            .testFilterShouldNotShowOwnCharacters();
    }

    @Test
    public void testFilterShouldShowOnlyMatchingCharacters() {
        FilterTestShouldShowOnlyMatchingCharacter.builder()
            .blockTestHelper(blockTestHelper)
            .communityTestHelper(communityTestHelper)
            .communityTestInitializer(communityTestInitializer)
            .build()
            .testFilterShouldShowOnlyMatchingCharacters();
    }

    @Test
    public void testFilterShouldNotShowAlreadyBlocked(){
        FilterTestShouldNotShowAlreadyBlocked.builder()
            .blockTestHelper(blockTestHelper)
            .communityTestHelper(communityTestHelper)
            .communityTestInitializer(communityTestInitializer)
            .build()
            .testFilterShouldNotShowAlreadyBlocked();
    }

    @Test
    public void testBlockCharacterShouldNotSendMail() {
        BlockCharacterTestShouldNotSendMail.builder()
            .communityTestHelper(communityTestHelper)
            .communityTestInitializer(communityTestInitializer)
            .blockTestHelper(blockTestHelper)
            .sendMailHelper(sendMailHelper)
            .build()
            .testBlockCharacterShouldNotSendMail();
    }

    @Test
    public void testBlockCharacterShouldNotSendFriendRequest(){
        BlockCharacterTestShouldNotSendFriendRequest.builder()
            .communityTestHelper(communityTestHelper)
            .communityTestInitializer(communityTestInitializer)
            .blockTestHelper(blockTestHelper)
            .friendshipTestHelper(friendshipTestHelper)
            .build()
            .testBlockCharacterShouldNotSendFriendRequest();
    }

    @Test
    public void testBlockCharacterShouldDeleteFriendRequest(){
        BlockCharacterTestShouldDeleteFriendRequest.builder()
            .communityTestHelper(communityTestHelper)
            .communityTestInitializer(communityTestInitializer)
            .blockTestHelper(blockTestHelper)
            .friendshipTestHelper(friendshipTestHelper)
            .communityPage(communityPage)
            .build()
            .testBlockCharacterShouldDeleteFriendRequest();
    }

    @Test
    public void testBlockCharacterShouldDeleteFriendship(){
        BlockCharacterTestShouldDeleteFriendship.builder()
            .communityTestHelper(communityTestHelper)
            .communityTestInitializer(communityTestInitializer)
            .blockTestHelper(blockTestHelper)
            .friendshipTestHelper(friendshipTestHelper)
            .communityPage(communityPage)
            .build()
            .testBlockCharacterShouldDeleteFriendship();
    }

    @Test
    public void testBlockCharacterShouldUnableToReplyMail(){
        BlockCharacterShouldUnableReplyMail.builder()
            .communityTestHelper(communityTestHelper)
            .communityTestInitializer(communityTestInitializer)
            .blockTestHelper(blockTestHelper)
            .mailTestHelper(mailTestHelper)
            .notificationValidator(notificationValidator)
            .communityPage(communityPage)
            .sendMailHelper(sendMailHelper)
            .build()
            .testBlockCharacterShouldUnableToReplyMail();
    }

    @Test
    public void testUnblockCharacter(){
        UnblockCharacterTest.builder()
            .communityTestHelper(communityTestHelper)
            .communityTestInitializer(communityTestInitializer)
            .blockTestHelper(blockTestHelper)
            .friendshipTestHelper(friendshipTestHelper)
            .notificationValidator(notificationValidator)
            .sendMailHelper(sendMailHelper)
            .build()
            .testUnblockCharacter();
    }
}
