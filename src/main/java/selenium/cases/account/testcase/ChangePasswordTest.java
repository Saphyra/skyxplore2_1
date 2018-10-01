package selenium.cases.account.testcase;

import lombok.Builder;
import org.openqa.selenium.WebDriver;
import selenium.domain.SeleniumUser;
import selenium.flow.Login;
import selenium.flow.Logout;
import selenium.flow.Navigate;
import selenium.page.AccountPage;
import selenium.util.FieldValidator;

@Builder
public class ChangePasswordTest {
    private final WebDriver driver;
    private final Navigate navigate;
    private final Login login;
    private final Logout logout;
    private final AccountPage accountPage;
    private final SeleniumUser user;
    private final SeleniumUser originalUser;
    private final SeleniumUser otherUser;
    private final FieldValidator fieldValidator;
}
