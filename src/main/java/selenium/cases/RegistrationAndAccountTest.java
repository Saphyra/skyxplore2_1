package selenium.cases;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import selenium.domain.SeleniumUser;
import selenium.flow.Registration;

@Slf4j
public class RegistrationAndAccountTest {
    public static void run(WebDriver driver) {
        Registration registration = new Registration(driver);
        SeleniumUser user = SeleniumUser.create();
        registration.registerUser(user);
    }
}
