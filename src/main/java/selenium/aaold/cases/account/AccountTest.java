package selenium.aaold.cases.account;

import static selenium.aanew.logic.util.LinkUtil.ACCOUNT;

import org.openqa.selenium.WebDriver;

import lombok.extern.slf4j.Slf4j;
import selenium.aanew.logic.domain.SeleniumUser;
import selenium.aanew.logic.flow.Login;
import selenium.aanew.logic.flow.Logout;
import selenium.aanew.logic.flow.Navigate;
import selenium.aanew.logic.flow.Registration;
import selenium.aanew.logic.page.AccountPage;
import selenium.aanew.logic.validator.FieldValidator;
import selenium.aanew.logic.validator.NotificationValidator;
import selenium.aaold.cases.account.testcase.DeleteAccountTest;

@Slf4j
public class AccountTest {

    private final WebDriver driver;
    private final Navigate navigate;
    private final Login login;
    private final Logout logout;
    private final AccountPage accountPage;
    private final SeleniumUser originalUser;
    private final SeleniumUser otherUser;
    private final FieldValidator fieldValidator;
    private final NotificationValidator notificationValidator;

    public AccountTest(WebDriver driver, SeleniumUser originalUser, SeleniumUser otherUser) {
        this.driver = driver;
        this.accountPage = new AccountPage(driver);
        this.login = new Login(driver);
        this.logout = new Logout(driver);
        this.navigate = new Navigate(driver);
        this.originalUser = originalUser;
        this.otherUser = otherUser;
        this.fieldValidator = new FieldValidator(driver, ACCOUNT);
        this.notificationValidator = new NotificationValidator(driver);
    }

    public static void run(WebDriver driver) {
        Registration registration = new Registration(driver);
        SeleniumUser otherUser = registration.registerUser();
        new Logout(driver).logOut();
        SeleniumUser user = registration.registerUser();

        AccountTest test = new AccountTest(driver, user, otherUser);
        test.deleteAccount();
    }

    private void deleteAccount() {
        init();
        DeleteAccountTest testCase = DeleteAccountTest.builder()
            .driver(driver)
            .accountPage(accountPage)
            .originalUser(originalUser)
            .fieldValidator(fieldValidator)
            .notificationValidator(notificationValidator)
            .login(login)
            .build();

        testCase.validateEmptyPassword();
        testCase.validateCancel();
        testCase.validateBadPassword();
        testCase.validateSuccess();
    }

    private void init(){
        logout.logOut();
        login.login(originalUser);
        navigate.toAccountPage();
    }
}
