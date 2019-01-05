package selenium.aanew.test.account;

import org.openqa.selenium.WebDriver;
import selenium.aanew.logic.flow.Navigate;
import selenium.aanew.logic.flow.Registration;
import selenium.aanew.logic.page.AccountPage;
import selenium.aanew.logic.validator.FieldValidator;
import selenium.aanew.logic.validator.NotificationValidator;
import selenium.aanew.test.account.changepassword.BadConfirmPasswordTest;
import selenium.aanew.test.account.changepassword.BadPasswordTest;
import selenium.aanew.test.account.changepassword.EmptyCurrentPasswordTest;
import selenium.aanew.test.account.changepassword.TooLongPasswordTest;
import selenium.aanew.test.account.changepassword.TooShortPasswordTest;
import selenium.aanew.test.account.changepassword.helper.ChangePasswordTestHelper;

import static selenium.aanew.logic.util.LinkUtil.ACCOUNT;

public class ChangePasswordTest {
    private static final String NOTIFICATION_SUCCESSFUL_PASSWORD_CHANGE = "Jelszó megváltoztatása sikeres.";

    private final WebDriver driver;
    private final Registration registration;
    private final AccountPage accountPage;
    private final FieldValidator fieldValidator;
    private final Navigate navigate;
    private final ChangePasswordTestHelper changePasswordTestHelper;
    private final NotificationValidator notificationValidator;

    public ChangePasswordTest(WebDriver driver) {
        this.driver = driver;
        this.registration = new Registration(driver);
        this.accountPage = new AccountPage(driver);
        this.fieldValidator = new FieldValidator(driver, ACCOUNT);
        this.navigate = new Navigate(driver);
        this.changePasswordTestHelper = new ChangePasswordTestHelper(driver, accountPage, registration, navigate);
        this.notificationValidator = new NotificationValidator(driver);
    }

    public void runTests() {
        tooShortPasswordTest();
        tooLongPasswordTest();
        badConfirmPasswordTest();
        emptyCurrentPasswordTest();
        badPasswordTest();

        /*

        validateHappyPath();*/
    }

    private void tooShortPasswordTest() {
        TooShortPasswordTest.builder()
            .driver(driver)
            .accountPage(accountPage)
            .fieldValidator(fieldValidator)
            .navigate(navigate)
            .changePasswordTestHelper(changePasswordTestHelper)
            .build()
            .testTooShortPassword();
    }

    private void tooLongPasswordTest() {
        TooLongPasswordTest.builder()
            .changePasswordTestHelper(changePasswordTestHelper)
            .registration(registration)
            .navigate(navigate)
            .accountPage(accountPage)
            .fieldValidator(fieldValidator)
            .build()
            .testTooLongPassword();
    }

    private void badConfirmPasswordTest() {
        BadConfirmPasswordTest.builder()
            .changePasswordTestHelper(changePasswordTestHelper)
            .accountPage(accountPage)
            .fieldValidator(fieldValidator)
            .build()
            .testBadConfirmPassword();
    }

    private void emptyCurrentPasswordTest() {
        EmptyCurrentPasswordTest.builder()
            .changePasswordTestHelper(changePasswordTestHelper)
            .accountPage(accountPage)
            .fieldValidator(fieldValidator)
            .build()
            .testEmptyCurrentPassword();
    }

    private void badPasswordTest() {
        BadPasswordTest.builder()
            .changePasswordTestHelper(changePasswordTestHelper)
            .accountPage(accountPage)
            .notificationValidator(notificationValidator)
            .build()
            .testBadPassword();
    }
}
