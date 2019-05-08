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
import com.github.saphyra.selenium.test.community.friendship.AcceptFriendRequestTest;
import com.github.saphyra.selenium.test.community.friendship.CancelFriendRequestTest;
import com.github.saphyra.selenium.test.community.friendship.DeclineFriendRequestTest;
import com.github.saphyra.selenium.test.community.friendship.DeleteFriendTest;
import com.github.saphyra.selenium.test.community.friendship.FilterTestShouldNotShowOwnCharacters;
import com.github.saphyra.selenium.test.community.friendship.FilterTestShouldNotShowWhenAlreadyFriend;
import com.github.saphyra.selenium.test.community.friendship.FilterTestShouldNotShowWhenFriendRequestSent;
import com.github.saphyra.selenium.test.community.friendship.FilterTestShouldShowOnlyMatchingCharacterNames;
import com.github.saphyra.selenium.test.community.friendship.SendFriendRequestTest;
import com.github.saphyra.selenium.test.community.friendship.SendMailToFriendTest;
import com.github.saphyra.selenium.test.community.helper.CommunityTestHelper;
import com.github.saphyra.selenium.test.community.helper.CommunityTestInitializer;
import com.github.saphyra.selenium.test.community.helper.FriendshipTestHelper;
import com.github.saphyra.selenium.test.community.helper.MailTestHelper;
import com.github.saphyra.selenium.test.community.helper.SendMailHelper;

public class FriendshipTest extends SeleniumTestApplication {
    private CommunityTestInitializer communityTestInitializer;
    private CommunityTestHelper communityTestHelper;
    private FriendshipTestHelper friendshipTestHelper;
    private CommunityPage communityPage;
    private NotificationValidator notificationValidator;
    private MailTestHelper mailTestHelper;
    private SendMailHelper sendMailHelper;

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

        notificationValidator = new NotificationValidator(driver);
        friendshipTestHelper = new FriendshipTestHelper(driver, communityPage, notificationValidator);
        mailTestHelper = new MailTestHelper(communityPage, driver, messageCodes, getPageLocalization("community"));
        sendMailHelper = new SendMailHelper(communityPage, notificationValidator, messageCodes);
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
    public void testFilterShouldNotShowWhenFriendRequestSent() {
        FilterTestShouldNotShowWhenFriendRequestSent.builder()
            .communityTestHelper(communityTestHelper)
            .communityTestInitializer(communityTestInitializer)
            .friendshipTestHelper(friendshipTestHelper)
            .build()
            .testFilterShouldNotShowWhenFriendRequestSent();
    }

    @Test
    public void testFilterShouldNotShowFriends() {
        FilterTestShouldNotShowWhenAlreadyFriend.builder()
            .communityTestHelper(communityTestHelper)
            .communityTestInitializer(communityTestInitializer)
            .friendshipTestHelper(friendshipTestHelper)
            .build()
            .testFilterShouldNotShowFriends();
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
    public void testDeclineFriendRequest() {
        DeclineFriendRequestTest.builder()
            .communityTestHelper(communityTestHelper)
            .communityTestInitializer(communityTestInitializer)
            .friendshipTestHelper(friendshipTestHelper)
            .communityPage(communityPage)
            .build()
            .testDeclineFriendRequest();
    }

    @Test
    public void testAcceptFriendRequest() {
        AcceptFriendRequestTest.builder()
            .communityTestHelper(communityTestHelper)
            .communityTestInitializer(communityTestInitializer)
            .friendshipTestHelper(friendshipTestHelper)
            .communityPage(communityPage)
            .build()
            .testAcceptFriendRequest();
    }

    @Test
    public void testDeleteFriend() {
        DeleteFriendTest.builder()
            .communityTestHelper(communityTestHelper)
            .communityTestInitializer(communityTestInitializer)
            .friendshipTestHelper(friendshipTestHelper)
            .communityPage(communityPage)
            .notificationValidator(notificationValidator)
            .messageCodes(messageCodes)
            .build()
            .testDeleteFriend();
    }

    @Test
    public void testSendMailToFriend() {
        SendMailToFriendTest.builder()
            .communityTestHelper(communityTestHelper)
            .communityTestInitializer(communityTestInitializer)
            .friendshipTestHelper(friendshipTestHelper)
            .communityPage(communityPage)
            .sendMailHelper(sendMailHelper)
            .mailTestHelper(mailTestHelper)
            .build()
            .testSendMailToFriend();
    }
}
