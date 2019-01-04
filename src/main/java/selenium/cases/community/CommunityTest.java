package selenium.cases.community;

import org.openqa.selenium.WebDriver;
import selenium.cases.community.domain.SeleniumAccount;
import selenium.domain.SeleniumCharacter;
import selenium.domain.SeleniumUser;
import selenium.flow.CreateCharacter;
import selenium.flow.Navigate;
import selenium.flow.Registration;

public class CommunityTest {

    private final WebDriver driver;
    private final Navigate navigate;
    private final Registration registration;
    private final CreateCharacter createCharacter;

    private SeleniumAccount account1;
    private SeleniumAccount account2;

    private CommunityTest(WebDriver driver) {
        this.driver = driver;
        this.navigate = new Navigate(driver);
        this.registration = new Registration(driver);
        this.createCharacter = new CreateCharacter(driver);
    }

    public static void run(WebDriver driver) {
        CommunityTest testCase = new CommunityTest(driver);
        testCase.init();

        /*
        Add friend test
            - Selectable characters
                - Own character(s) do not appear
                - Search by name
                    - Only matching ones appear
            - Send friend request
                - Character 1: Request appears at sent
                - Character 2: Request appears at new ones
                    - - Notifications shown
                - Characters do not appear at selectable characters (from both side)
            - Cancel friend request
                - Request disappears both side
                - New friendRequest can be sent.
            - Accept friend request
                - Friendship appears
                - FriendRequest disappears
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

    private void init() {
        account1 = registerAccount();
        account2 = registerAccount();
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
