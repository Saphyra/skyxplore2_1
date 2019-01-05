package selenium.aaold.cases.account;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import selenium.aanew.domain.SeleniumUser;
import selenium.aanew.flow.Navigate;
import selenium.aanew.flow.Registration;
import selenium.aanew.validator.FieldValidator;
import selenium.aanew.validator.NotificationValidator;
import selenium.aaold.cases.account.testcase.ChangeEmailTest;
import selenium.aaold.cases.account.testcase.ChangeUserNameTest;
import selenium.aaold.cases.account.testcase.DeleteAccountTest;
import selenium.aanew.flow.Login;
import selenium.aanew.flow.Logout;
import selenium.page.AccountPage;

import static selenium.aanew.util.LinkUtil.ACCOUNT;

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
        test.testChangeUserName();
        test.testChangeEmail();
        test.deleteAccount();
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

    private void testChangeEmail() {
        init();
        ChangeEmailTest testCase = ChangeEmailTest.builder()
            .driver(driver)
            .accountPage(accountPage)
            .user(originalUser.cloneUser())
            .originalUser(originalUser)
            .otherUser(otherUser)
            .fieldValidator(fieldValidator)
            .notificationValidator(notificationValidator)
            .build();

        testCase.validateInvalidEmail();
        testCase.validateExistingEmail();
        testCase.validateEmptyPassword();
        testCase.validateBadPassword();
        testCase.validateHappyPath();
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
