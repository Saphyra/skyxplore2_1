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
import selenium.test.community.friendship.AcceptFriendRequestTest;
import selenium.test.community.friendship.CancelFriendRequestTest;
import selenium.test.community.friendship.DeclineFriendRequestTest;
import selenium.test.community.friendship.FilterTestShouldNotShowOwnCharacters;
import selenium.test.community.friendship.FilterTestShouldShowOnlyMatchingCharacterNames;
import selenium.test.community.friendship.SendFriendRequestTest;
import selenium.test.community.friendship.helper.FriendshipTestHelper;
import selenium.test.community.util.CommunityTestHelper;
import selenium.test.community.util.CommunityTestInitializer;

public class FriendshipTest extends SeleniumTestApplication {
    private CommunityTestInitializer communityTestInitializer;
    private CommunityTestHelper communityTestHelper;
    private FriendshipTestHelper friendshipTestHelper;
    private NotificationValidator notificationValidator;
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
        notificationValidator = new NotificationValidator(driver);
        friendshipTestHelper = new FriendshipTestHelper(communityPage, notificationValidator);
    }

    @Test
    public void testFilterShouldNotShowOwnCharacters() {
        FilterTestShouldNotShowOwnCharacters.builder()
            .communityTestHelper(communityTestHelper)
            .communityTestInitializer(communityTestInitializer)
            .friendshipTestHelper(friendshipTestHelper)
            .build()
            .testFilterShouldNotShowOwnCharacters();
    }

    @Test
    public void testFilterShouldShowOnlyMatchingCharacterNames() {
        FilterTestShouldShowOnlyMatchingCharacterNames.builder()
            .communityTestHelper(communityTestHelper)
            .communityTestInitializer(communityTestInitializer)
            .friendshipTestHelper(friendshipTestHelper)
            .build()
            .testFilterShouldShowOnlyMatchingCharacterNames();
    }

    @Test
    public void testSendFriendRequest() {
        SendFriendRequestTest.builder()
            .communityTestHelper(communityTestHelper)
            .communityTestInitializer(communityTestInitializer)
            .friendshipTestHelper(friendshipTestHelper)
            .communityPage(communityPage)
            .build()
            .testSendFriendRequest();
    }

    @Test
    public void testCancelFriendRequest() {
        CancelFriendRequestTest.builder()
            .communityTestHelper(communityTestHelper)
            .communityTestInitializer(communityTestInitializer)
            .friendshipTestHelper(friendshipTestHelper)
            .communityPage(communityPage)
            .build()
            .testCancelFriendRequest();
    }

    @Test
    public void testDeclineFriendRequestTest() {
        DeclineFriendRequestTest.builder()
            .communityTestHelper(communityTestHelper)
            .communityTestInitializer(communityTestInitializer)
            .friendshipTestHelper(friendshipTestHelper)
            .communityPage(communityPage)
            .build()
            .testDeclineFriendRequest();
    }

    @Test
    public void testAcceptFriendRequest(){
        AcceptFriendRequestTest.builder()
            .communityTestHelper(communityTestHelper)
            .communityTestInitializer(communityTestInitializer)
            .friendshipTestHelper(friendshipTestHelper)
            .communityPage(communityPage)
            .build()
            .testAcceptFriendRequest();
    }
}
