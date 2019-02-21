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
import selenium.test.community.friendship.AcceptFriendRequestTest;
import selenium.test.community.friendship.CancelFriendRequestTest;
import selenium.test.community.friendship.DeclineFriendRequestTest;
import selenium.test.community.friendship.FilterTestShouldNotShowOwnCharacters;
import selenium.test.community.friendship.FilterTestShouldNotShowWhenAlreadyFriend;
import selenium.test.community.friendship.FilterTestShouldNotShowWhenFriendRequestSent;
import selenium.test.community.friendship.FilterTestShouldShowOnlyMatchingCharacterNames;
import selenium.test.community.friendship.SendFriendRequestTest;
import selenium.test.community.helper.CommunityTestHelper;
import selenium.test.community.helper.CommunityTestInitializer;
import selenium.test.community.helper.FriendshipTestHelper;

public class FriendshipTest extends SeleniumTestApplication {
    private CommunityTestInitializer communityTestInitializer;
    private CommunityTestHelper communityTestHelper;
    private FriendshipTestHelper friendshipTestHelper;
    private CommunityPage communityPage;

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
        friendshipTestHelper = new FriendshipTestHelper(communityPage, new NotificationValidator(driver));
    }

    @Test
    @Ignore
    public void testFilterShouldNotShowOwnCharacters() {
        FilterTestShouldNotShowOwnCharacters.builder()
            .communityTestHelper(communityTestHelper)
            .communityTestInitializer(communityTestInitializer)
            .friendshipTestHelper(friendshipTestHelper)
            .build()
            .testFilterShouldNotShowOwnCharacters();
    }

    @Test
    @Ignore
    public void testFilterShouldShowOnlyMatchingCharacterNames() {
        FilterTestShouldShowOnlyMatchingCharacterNames.builder()
            .communityTestHelper(communityTestHelper)
            .communityTestInitializer(communityTestInitializer)
            .friendshipTestHelper(friendshipTestHelper)
            .build()
            .testFilterShouldShowOnlyMatchingCharacterNames();
    }

    @Test
    @Ignore
    public void testFilterShouldNotShowWhenFriendRequestSent() {
        FilterTestShouldNotShowWhenFriendRequestSent.builder()
            .communityTestHelper(communityTestHelper)
            .communityTestInitializer(communityTestInitializer)
            .friendshipTestHelper(friendshipTestHelper)
            .build()
            .testFilterShouldNotShowWhenFriendRequestSent();
    }

    @Test
    @Ignore
    public void testFilterShouldNotShowFriends() {
        FilterTestShouldNotShowWhenAlreadyFriend.builder()
            .communityTestHelper(communityTestHelper)
            .communityTestInitializer(communityTestInitializer)
            .friendshipTestHelper(friendshipTestHelper)
            .build()
            .testFilterShouldNotShowFriends();
    }

    @Test
    @Ignore
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
    @Ignore
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
    @Ignore
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
    @Ignore
    public void testAcceptFriendRequest() {
        AcceptFriendRequestTest.builder()
            .communityTestHelper(communityTestHelper)
            .communityTestInitializer(communityTestInitializer)
            .friendshipTestHelper(friendshipTestHelper)
            .communityPage(communityPage)
            .build()
            .testAcceptFriendRequest();
    }
}
