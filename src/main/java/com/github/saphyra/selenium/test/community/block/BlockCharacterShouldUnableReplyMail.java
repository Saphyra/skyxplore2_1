package com.github.saphyra.selenium.test.community.block;

import static org.junit.Assert.assertTrue;

import java.util.List;

import lombok.Builder;
import com.github.saphyra.selenium.logic.domain.localization.MessageCodes;
import com.github.saphyra.selenium.logic.domain.SeleniumAccount;
import com.github.saphyra.selenium.logic.domain.SeleniumCharacter;
import com.github.saphyra.selenium.logic.page.CommunityPage;
import com.github.saphyra.selenium.logic.validator.NotificationValidator;
import com.github.saphyra.selenium.test.community.helper.BlockTestHelper;
import com.github.saphyra.selenium.test.community.helper.CommunityTestHelper;
import com.github.saphyra.selenium.test.community.helper.CommunityTestInitializer;
import com.github.saphyra.selenium.test.community.helper.MailTestHelper;
import com.github.saphyra.selenium.test.community.helper.SendMailHelper;

@Builder
public class BlockCharacterShouldUnableReplyMail {
    private static final String MESSAGE_CODE_ERROR_SENDING_MAIL = "ERROR_SENDING_MAIL";

    private final CommunityTestInitializer communityTestInitializer;
    private final CommunityTestHelper communityTestHelper;
    private final BlockTestHelper blockTestHelper;
    private final SendMailHelper sendMailHelper;
    private final MailTestHelper mailTestHelper;
    private final NotificationValidator notificationValidator;
    private final CommunityPage communityPage;
    private final MessageCodes messageCodes;

    public void testBlockCharacterShouldUnableToReplyMail() {
        List<SeleniumAccount> accounts = communityTestInitializer.registerAccounts(new int[]{1, 1});

        SeleniumAccount account = accounts.get(0);
        SeleniumCharacter character = account.getCharacter(0);

        SeleniumAccount otherAccount = accounts.get(1);
        SeleniumCharacter otherCharacter = otherAccount.getCharacter(0);

        communityTestHelper.goToCommunityPageOf(account, character);
        sendMailHelper.sendMailTo(otherCharacter);
        blockTestHelper.blockCharacter(otherCharacter);

        communityTestHelper.goToCommunityPageOf(otherAccount, otherCharacter, 1);
        mailTestHelper.getMail().reply();
        sendMailHelper.setMessage()
            .sendMail();

        notificationValidator.verifyNotificationVisibility(messageCodes.get(MESSAGE_CODE_ERROR_SENDING_MAIL));
        assertTrue(communityPage.getSendMailContainer().isDisplayed());
    }
}
