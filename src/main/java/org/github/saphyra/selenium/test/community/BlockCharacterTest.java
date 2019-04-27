package org.github.saphyra.selenium.test.community;

import org.junit.Test;

import org.github.saphyra.selenium.SeleniumTestApplication;
import org.github.saphyra.selenium.logic.flow.CreateCharacter;
import org.github.saphyra.selenium.logic.flow.Login;
import org.github.saphyra.selenium.logic.flow.Logout;
import org.github.saphyra.selenium.logic.flow.Navigate;
import org.github.saphyra.selenium.logic.flow.Registration;
import org.github.saphyra.selenium.logic.flow.SelectCharacter;
import org.github.saphyra.selenium.logic.page.CommunityPage;
import org.github.saphyra.selenium.logic.page.OverviewPage;
import org.github.saphyra.selenium.logic.validator.NotificationValidator;
import org.github.saphyra.selenium.test.community.block.BlockCharacterShouldUnableReplyMail;
import org.github.saphyra.selenium.test.community.block.BlockCharacterTestShouldDeleteFriendRequest;
import org.github.saphyra.selenium.test.community.block.BlockCharacterTestShouldDeleteFriendship;
import org.github.saphyra.selenium.test.community.block.BlockCharacterTestShouldNotSendFriendRequest;
import org.github.saphyra.selenium.test.community.block.BlockCharacterTestShouldNotSendMail;
import org.github.saphyra.selenium.test.community.block.FilterTestShouldNotShowAlreadyBlocked;
import org.github.saphyra.selenium.test.community.block.FilterTestShouldNotShowOwnCharacters;
import org.github.saphyra.selenium.test.community.block.FilterTestShouldShowOnlyMatchingCharacter;
import org.github.saphyra.selenium.test.community.block.UnblockCharacterTest;
import org.github.saphyra.selenium.test.community.block.BlockFriendRequestSenderTest;
import org.github.saphyra.selenium.test.community.helper.BlockTestHelper;
import org.github.saphyra.selenium.test.community.helper.CommunityTestHelper;
import org.github.saphyra.selenium.test.community.helper.CommunityTestInitializer;
import org.github.saphyra.selenium.test.community.helper.FriendshipTestHelper;
import org.github.saphyra.selenium.test.community.helper.MailTestHelper;
import org.github.saphyra.selenium.test.community.helper.SendMailHelper;

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
            new CreateCharacter(driver, messageCodes),
            new Logout(driver, messageCodes)
        );

        communityPage = new CommunityPage(driver, messageCodes);
        notificationValidator = new NotificationValidator(driver);

        blockTestHelper = new BlockTestHelper(driver, communityPage, notificationValidator, messageCodes);

        sendMailHelper = new SendMailHelper(communityPage, notificationValidator, messageCodes);
        friendshipTestHelper = new FriendshipTestHelper(driver, communityPage, notificationValidator);
        mailTestHelper = new MailTestHelper(communityPage, driver, messageCodes);
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
    public void testBlockCharacterShouldUnableToReplyMail() {
        BlockCharacterShouldUnableReplyMail.builder()
            .communityTestHelper(communityTestHelper)
            .communityTestInitializer(communityTestInitializer)
            .blockTestHelper(blockTestHelper)
            .mailTestHelper(mailTestHelper)
            .notificationValidator(notificationValidator)
            .communityPage(communityPage)
            .sendMailHelper(sendMailHelper)
            .messageCodes(messageCodes)
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
