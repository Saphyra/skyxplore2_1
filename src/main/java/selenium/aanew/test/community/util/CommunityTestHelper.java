package selenium.aanew.test.community.util;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.WebElement;

import lombok.RequiredArgsConstructor;
import selenium.aanew.logic.domain.SeleniumAccount;
import selenium.aanew.logic.domain.SeleniumCharacter;
import selenium.aanew.logic.flow.Login;
import selenium.aanew.logic.flow.Navigate;
import selenium.aanew.logic.flow.SelectCharacter;
import selenium.aanew.logic.page.OverviewPage;

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
        int displayedNumber = 0;
        if (!notificationText.isEmpty()) {
            displayedNumber = Integer.valueOf(notificationText.split("\\(")[1].split("\\)")[0]);
        }
        assertEquals(numberOfNotifications, displayedNumber);
    }
}
