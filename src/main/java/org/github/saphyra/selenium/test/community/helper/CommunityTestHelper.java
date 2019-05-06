package org.github.saphyra.selenium.test.community.helper;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebElement;
import org.github.saphyra.selenium.logic.domain.SeleniumAccount;
import org.github.saphyra.selenium.logic.domain.SeleniumCharacter;
import org.github.saphyra.selenium.logic.flow.Login;
import org.github.saphyra.selenium.logic.flow.Navigate;
import org.github.saphyra.selenium.logic.flow.SelectCharacter;
import org.github.saphyra.selenium.logic.page.OverviewPage;

import static org.junit.Assert.assertEquals;

@RequiredArgsConstructor
public class CommunityTestHelper {
    private final Login login;
    private final SelectCharacter selectCharacter;
    private final OverviewPage overviewPage;
    private final Navigate navigate;

    public void goToCommunityPageOf(SeleniumAccount account, SeleniumCharacter character) {
        goToCommunityPageOf(account, character, 0);
    }

    public void goToCommunityPageOf(SeleniumAccount account, SeleniumCharacter character, int numberOfNotifications) {
        login.login(account.getUser());
        selectCharacter.selectCharacter(character);
        verifyNotificationNum(numberOfNotifications);
        navigate.toCommunityPage();
    }

    private void verifyNotificationNum(int numberOfNotifications) {
        WebElement notificationElement = overviewPage.getNotificationNumberElement();
        String notificationText = notificationElement.getText();

        assertEquals(numberOfNotifications, (int) Integer.valueOf(notificationText));
    }
}
