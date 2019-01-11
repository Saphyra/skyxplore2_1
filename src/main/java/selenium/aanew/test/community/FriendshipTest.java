package selenium.aanew.test.community;

import org.junit.Test;

import selenium.aanew.SeleniumTestApplication;
import selenium.aanew.logic.flow.CreateCharacter;
import selenium.aanew.logic.flow.Login;
import selenium.aanew.logic.flow.Logout;
import selenium.aanew.logic.flow.Navigate;
import selenium.aanew.logic.flow.Registration;
import selenium.aanew.logic.flow.SelectCharacter;
import selenium.aanew.logic.page.CommunityPage;
import selenium.aanew.logic.page.OverviewPage;
import selenium.aanew.test.community.friendship.FilterTestShouldNotShowOwnCharacters;
import selenium.aanew.test.community.friendship.FilterTestShouldShowOnlyMatchingCharacterNames;
import selenium.aanew.test.community.friendship.helper.FilterTestHelper;
import selenium.aanew.test.community.util.CommunityTestHelper;
import selenium.aanew.test.community.util.CommunityTestInitializer;

public class FriendshipTest extends SeleniumTestApplication {
    private CommunityTestInitializer communityTestInitializer;
    private CommunityTestHelper communityTestHelper;
    private FilterTestHelper filterTestHelper;

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

        filterTestHelper = new FilterTestHelper(new CommunityPage(driver));
    }

    @Test
    public void testFilterShouldNotShowOwnCharacters(){
        FilterTestShouldNotShowOwnCharacters.builder()
            .communityTestHelper(communityTestHelper)
            .communityTestInitializer(communityTestInitializer)
            .filterTestHelper(filterTestHelper)
            .build()
            .testFilterShouldNotShowOwnCharacters();
    }

    @Test
    public void testFilterShouldShowOnlyMatchingCharacterNames(){
        FilterTestShouldShowOnlyMatchingCharacterNames.builder()
            .communityTestHelper(communityTestHelper)
            .communityTestInitializer(communityTestInitializer)
            .filterTestHelper(filterTestHelper)
            .build()
            .testFilterShouldShowOnlyMatchingCharacterNames();
    }
}
