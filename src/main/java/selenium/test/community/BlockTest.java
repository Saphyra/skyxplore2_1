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
import selenium.test.community.block.BlockCharacterTestShouldNotSendFriendRequest;
import selenium.test.community.block.BlockCharacterTestShouldNotSendMail;
import selenium.test.community.block.FilterTestShouldNotShowAlreadyBlocked;
import selenium.test.community.block.FilterTestShouldNotShowOwnCharacters;
import selenium.test.community.block.FilterTestShouldShowOnlyMatchingCharacter;
import selenium.test.community.helper.BlockTestHelper;
import selenium.test.community.helper.CommunityTestHelper;
import selenium.test.community.helper.CommunityTestInitializer;
import selenium.test.community.helper.FriendshipTestHelper;
import selenium.test.community.helper.SendMailHelper;

public class BlockTest extends SeleniumTestApplication {
    private CommunityTestInitializer communityTestInitializer;
    private CommunityTestHelper communityTestHelper;
    private BlockTestHelper blockTestHelper;
    private SendMailHelper sendMailHelper;
    private FriendshipTestHelper friendshipTestHelper;
    private CommunityPage communityPage;

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
        NotificationValidator notificationValidator = new NotificationValidator(driver);

        blockTestHelper = new BlockTestHelper(communityPage, notificationValidator);

        sendMailHelper = new SendMailHelper(communityPage, notificationValidator);
        friendshipTestHelper = new FriendshipTestHelper(communityPage, notificationValidator);
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
}
