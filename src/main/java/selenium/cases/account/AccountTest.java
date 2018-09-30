package selenium.cases.account;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import selenium.domain.SeleniumUser;
import selenium.flow.Login;
import selenium.flow.Logout;
import selenium.flow.Navigate;
import selenium.flow.Registration;
import selenium.page.AccountPage;

@Slf4j
public class AccountTest {

    private final WebDriver driver;
    private final Navigate navigate;
    private final Login login;
    private final Logout logout;
    private final AccountPage accountPage;
    private final SeleniumUser user;
    private final SeleniumUser originalUser;
    private final SeleniumUser otherUser;

    public AccountTest(WebDriver driver, SeleniumUser user, SeleniumUser otherUser) {
        this.driver = driver;
        this.user = user;
        this.accountPage = new AccountPage(driver);
        this.login = new Login(driver);
        this.logout = new Logout(driver);
        this.navigate = new Navigate(driver);
        this.originalUser = user.cloneUser();
        this.otherUser = otherUser;
    }

    public static void run(WebDriver driver) {
        Registration registration = new Registration(driver);
        SeleniumUser otherUser = registration.registerUser();
        new Logout(driver).logOut();
        SeleniumUser user = registration.registerUser();

        AccountTest test = new AccountTest(driver, user, otherUser);
        test.testChangeUserName();
    }

    private void testChangeUserName() {
        init();
    }

    private void init(){
        logout.logOut();
        login.login(user);
        navigate.toAccountPage();
    }
}
