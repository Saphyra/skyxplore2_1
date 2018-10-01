package selenium.cases.account;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import selenium.cases.account.testcase.ChangePasswordTest;
import selenium.cases.account.testcase.ChangeUserNameTest;
import selenium.domain.SeleniumUser;
import selenium.flow.Login;
import selenium.flow.Logout;
import selenium.flow.Navigate;
import selenium.flow.Registration;
import selenium.page.AccountPage;
import selenium.validator.FieldValidator;
import selenium.validator.NotificationValidator;

import static selenium.util.LinkUtil.ACCOUNT;

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
        test.testChangePassword();
        test.testChangeUserName();
    }

    private void testChangePassword() {
        init();
        ChangePasswordTest testCase = ChangePasswordTest.builder()
            .driver(driver)
            .navigate(navigate)
            .login(login)
            .logout(logout)
            .accountPage(accountPage)
            .user(originalUser.cloneUser())
            .originalUser(originalUser)
            .otherUser(otherUser)
            .fieldValidator(fieldValidator)
            .notificationValidator(notificationValidator)
            .build();

        testCase.validateTooShortPassword();
        testCase.validateTooLongPassword();
        testCase.validateConfirmPassword();
        testCase.validateEmptyCurrentPassword();
        testCase.validateBadPassword();
        testCase.validateHappyPath();
    }

    private void testChangeUserName() {
        init();
        ChangeUserNameTest testCase = ChangeUserNameTest.builder()
            .driver(driver)
            .navigate(navigate)
            .login(login)
            .logout(logout)
            .accountPage(accountPage)
            .user(originalUser.cloneUser())
            .originalUser(originalUser)
            .otherUser(otherUser)
            .fieldValidator(fieldValidator)
            .notificationValidator(notificationValidator)
            .build();

        testCase.validateTooShortUserName();
        testCase.validateTooLongUserName();
        testCase.validateExistingUserName();
        testCase.validateEmptyPassword();
        testCase.validateBadPassword();
        testCase.validateHappyPath();
    }

    private void init(){
        logout.logOut();
        login.login(originalUser);
        navigate.toAccountPage();
    }
}
