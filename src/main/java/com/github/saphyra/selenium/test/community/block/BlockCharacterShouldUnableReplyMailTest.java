package com.github.saphyra.selenium.test.community.block;

import com.github.saphyra.selenium.logic.domain.SeleniumAccount;
import com.github.saphyra.selenium.logic.domain.SeleniumCharacter;
import com.github.saphyra.selenium.logic.page.CommunityPage;
import com.github.saphyra.selenium.logic.validator.NotificationValidator;
import com.github.saphyra.selenium.test.community.helper.BlockTestHelper;
import com.github.saphyra.selenium.test.community.helper.CommunityTestHelper;
import com.github.saphyra.selenium.test.community.helper.CommunityTestInitializer;
import com.github.saphyra.selenium.test.community.helper.MailTestHelper;
import com.github.saphyra.selenium.test.community.helper.SendMailHelper;
import com.github.saphyra.skyxplore.common.ErrorCode;
import lombok.Builder;
import org.openqa.selenium.WebDriver;

import java.util.List;

import static com.github.saphyra.selenium.logic.util.LocalizationUtil.getErrorCode;
import static org.junit.Assert.assertTrue;

@Builder
public class BlockCharacterShouldUnableReplyMailTest {
    private final WebDriver driver;
    private final CommunityTestInitializer communityTestInitializer;
    private final CommunityTestHelper communityTestHelper;
    private final BlockTestHelper blockTestHelper;
    private final SendMailHelper sendMailHelper;
    private final MailTestHelper mailTestHelper;
    private final NotificationValidator notificationValidator;
    private final CommunityPage communityPage;

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

        notificationValidator.verifyNotificationVisibility(getErrorCode(driver, ErrorCode.CHARACTER_BLOCKED));
        assertTrue(communityPage.getSendMailContainer().isDisplayed());
    }
}
