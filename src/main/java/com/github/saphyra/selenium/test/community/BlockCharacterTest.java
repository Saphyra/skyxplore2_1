package com.github.saphyra.selenium.test.community;

import org.junit.Ignore;
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
import com.github.saphyra.selenium.test.community.block.BlockCharacterShouldUnableReplyMailTest;
import com.github.saphyra.selenium.test.community.block.BlockCharacterTestShouldDeleteFriendRequest;
import com.github.saphyra.selenium.test.community.block.BlockCharacterTestShouldDeleteFriendship;
import com.github.saphyra.selenium.test.community.block.BlockCharacterTestShouldNotSendFriendRequest;
import com.github.saphyra.selenium.test.community.block.BlockCharacterTestShouldNotSendMail;
import com.github.saphyra.selenium.test.community.block.FilterTestShouldNotShowAlreadyBlocked;
import com.github.saphyra.selenium.test.community.block.FilterTestShouldNotShowOwnCharacters;
import com.github.saphyra.selenium.test.community.block.FilterTestShouldShowOnlyMatchingCharacter;
import com.github.saphyra.selenium.test.community.block.UnblockCharacterTest;
import com.github.saphyra.selenium.test.community.block.BlockFriendRequestSenderTest;
import com.github.saphyra.selenium.test.community.helper.BlockTestHelper;
import com.github.saphyra.selenium.test.community.helper.CommunityTestHelper;
import com.github.saphyra.selenium.test.community.helper.CommunityTestInitializer;
import com.github.saphyra.selenium.test.community.helper.FriendshipTestHelper;
import com.github.saphyra.selenium.test.community.helper.MailTestHelper;
import com.github.saphyra.selenium.test.community.helper.SendMailHelper;

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

        blockTestHelper = new BlockTestHelper(driver, communityPage, notificationValidator);

        sendMailHelper = new SendMailHelper(driver, communityPage, notificationValidator);
        friendshipTestHelper = new FriendshipTestHelper(driver, communityPage, notificationValidator);
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
    public void testFilterShouldNotShowAlreadyBlocked() {
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
    public void testBlockCharacterShouldNotSendFriendRequest() {
        BlockCharacterTestShouldNotSendFriendRequest.builder()
            .communityTestHelper(communityTestHelper)
            .communityTestInitializer(communityTestInitializer)
            .blockTestHelper(blockTestHelper)
            .friendshipTestHelper(friendshipTestHelper)
            .build()
            .testBlockCharacterShouldNotSendFriendRequest();
    }

    @Test
    public void testBlockCharacterShouldDeleteFriendRequest() {
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
    public void testBlockCharacterShouldDeleteFriendship() {
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
    @Ignore
    //TODO restore
    public void testBlockCharacterShouldUnableToReplyMail() {
        BlockCharacterShouldUnableReplyMailTest.builder()
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
    public void testBlockFriendRequestSender(){
        BlockFriendRequestSenderTest.builder()
            .communityTestHelper(communityTestHelper)
            .communityTestInitializer(communityTestInitializer)
            .friendshipTestHelper(friendshipTestHelper)
            .communityPage(communityPage)
            .blockTestHelper(blockTestHelper)
            .build()
            .testBlockFriendRequestSender();
    }

    @Test
    public void testUnblockCharacter() {
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
