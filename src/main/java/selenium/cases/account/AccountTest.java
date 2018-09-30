package selenium.cases.account;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import selenium.domain.SeleniumUser;
import selenium.flow.Login;
import selenium.flow.Navigate;
import selenium.flow.Registration;
import selenium.page.AccountPage;

@Slf4j
public class AccountTest {

    private final WebDriver driver;
    private final Navigate navigate;
    private final Login login;
    private final AccountPage accountPage;
    private final SeleniumUser user;

    public AccountTest(WebDriver driver, SeleniumUser user) {
        this.driver = driver;
        this.user = user;
        this.accountPage = new AccountPage(driver);
        this.login = new Login(driver);
        this.navigate = new Navigate(driver);
    }

    public static void run(WebDriver driver) {
        Registration registration = new Registration(driver);
        SeleniumUser user = registration.registerUser();

        AccountTest test = new AccountTest(driver, user);
    }
}
