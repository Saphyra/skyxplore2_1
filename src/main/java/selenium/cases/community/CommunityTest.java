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
