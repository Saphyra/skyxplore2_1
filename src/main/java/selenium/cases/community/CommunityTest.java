package selenium.cases.community;

import org.openqa.selenium.WebDriver;
import selenium.cases.community.domain.SeleniumAccount;
import selenium.cases.community.testcase.FriendshipTest;
import selenium.domain.SeleniumCharacter;
import selenium.domain.SeleniumUser;
import selenium.flow.CreateCharacter;
import selenium.flow.Login;
import selenium.flow.Navigate;
import selenium.flow.Registration;
import selenium.flow.SelectCharacter;
import selenium.page.CommunityPage;
import selenium.page.OverviewPage;
import selenium.validator.NotificationValidator;

import java.util.function.Supplier;

public class CommunityTest {

    private final WebDriver driver;
    private final Navigate navigate;
    private final Registration registration;
    private final CreateCharacter createCharacter;
    private final SelectCharacter selectCharacter;
    private final Login login;
    private final CommunityPage communityPage;
    private final NotificationValidator notificationValidator;
    private final OverviewPage overviewPage;

    private final Supplier<SeleniumAccount> seleniumAccountSupplier = this::registerAccount;

    private CommunityTest(WebDriver driver) {
        this.driver = driver;
        this.navigate = new Navigate(driver);
        this.registration = new Registration(driver);
        this.createCharacter = new CreateCharacter(driver);
        this.selectCharacter = new SelectCharacter(driver);
        this.login = new Login(driver);
        this.communityPage = new CommunityPage(driver);
        this.notificationValidator = new NotificationValidator(driver);
        this.overviewPage = new OverviewPage(driver);
    }

    public static void run(WebDriver driver) {
        CommunityTest testCase = new CommunityTest(driver);
        testCase.testFriendship();

        /*
        Add friend test
            - Cancel friend request
                - Request disappears both side
                - New friendRequest can be sent.
            - Accept friend request
                - Friendship appears
                - SeleniumFriendRequest disappears
                - Characters do not appear at selectable characters (from both side)
        Send Mail test
            - Selectable characters
                - Search by name
                    - Only matching ones appear
            - Send mail
                - Mail appears at sent ones
                - Character 2 gets a new mail
                    - Notifications shown
                - Character 2 reads the mail
                    - Notifications disappear
            - Archive mail
                - Mail moved to archived
            - Delete mail
                - Mail disappears
                - Other character still see the mail
        Block character test
            - block character
                - Existing friendship disappears
                - Cannot send friendRequest
                - Cannot send mail
            - unblock character
                - Friend request can be sent
                - Mail can be sent
         */
    }

    private void testFriendship() {
        FriendshipTest.builder()
            .notificationValidator(notificationValidator)
            .seleniumAccountSupplier(seleniumAccountSupplier)
            .navigate(navigate)
            .selectCharacter(selectCharacter)
            .communityPage(communityPage)
            .overviewPage(overviewPage)
            .login(login)
            .build()
            .testFilter()
            .testSendFriendRequest()
            .testCancelFriendRequest()
            .testAcceptFriendRequest();
    }

    private SeleniumAccount registerAccount() {
        navigate.toIndexPage();
        SeleniumUser user = registration.registerUser();
        SeleniumCharacter character1 = createCharacter.createCharacter();
        SeleniumCharacter character2 = createCharacter.createCharacter();
        return SeleniumAccount.builder()
            .user(user)
            .character1(character1)
            .character2(character2)
            .build();
    }
}
