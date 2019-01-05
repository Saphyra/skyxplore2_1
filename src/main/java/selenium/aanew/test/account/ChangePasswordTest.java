package selenium.aanew.test.account;

import org.openqa.selenium.WebDriver;
import selenium.aanew.logic.flow.Navigate;
import selenium.aanew.logic.flow.Registration;
import selenium.aanew.logic.page.AccountPage;
import selenium.aanew.logic.validator.FieldValidator;
import selenium.aanew.test.account.changepassword.ConfirmPasswordTest;
import selenium.aanew.test.account.changepassword.EmptyCurrentPasswordTest;
import selenium.aanew.test.account.changepassword.TooLongPasswordTest;
import selenium.aanew.test.account.changepassword.TooShortPasswordTest;
import selenium.aanew.test.account.changepassword.helper.ChangePasswordTestSetup;

import static selenium.aanew.logic.util.LinkUtil.ACCOUNT;

public class ChangePasswordTest {

    private static final String NOTIFICATION_BAD_PASSWORD = "Hibás jelszó.";
    private static final String NOTIFICATION_SUCCESSFUL_PASSWORD_CHANGE = "Jelszó megváltoztatása sikeres.";

    private final WebDriver driver;
    private final Registration registration;
    private final AccountPage accountPage;
    private final FieldValidator fieldValidator;
    private final Navigate navigate;
    private final ChangePasswordTestSetup changePasswordTestSetup;

    public ChangePasswordTest(WebDriver driver) {
        this.driver = driver;
        this.registration = new Registration(driver);
        this.accountPage = new AccountPage(driver);
        this.fieldValidator = new FieldValidator(driver, ACCOUNT);
        this.navigate = new Navigate(driver);
        this.changePasswordTestSetup = new ChangePasswordTestSetup(accountPage, registration, navigate);
    }

    public void runTests() {
        tooShortPasswordTest();
        tooLongPasswordTest();
        validateConfirmPassword();
        validateEmptyCurrentPassword();

        /*
        validateBadPassword();
        validateHappyPath();*/
    }

    private void tooShortPasswordTest() {
        TooShortPasswordTest.builder()
            .driver(driver)
            .accountPage(accountPage)
            .fieldValidator(fieldValidator)
            .navigate(navigate)
            .changePasswordTestSetup(changePasswordTestSetup)
            .build()
            .validateTooShortPassword();
    }

    private void tooLongPasswordTest() {
        TooLongPasswordTest.builder()
            .changePasswordTestSetup(changePasswordTestSetup)
            .registration(registration)
            .navigate(navigate)
            .accountPage(accountPage)
            .fieldValidator(fieldValidator)
            .build()
            .validateTooLongPassword();
    }

    private void validateConfirmPassword() {
        ConfirmPasswordTest.builder()
            .changePasswordTestSetup(changePasswordTestSetup)
            .accountPage(accountPage)
            .fieldValidator(fieldValidator)
            .build()
            .validateConfirmPassword();
    }

    private void validateEmptyCurrentPassword() {
        EmptyCurrentPasswordTest.builder()
            .changePasswordTestSetup(changePasswordTestSetup)
            .accountPage(accountPage)
            .fieldValidator(fieldValidator)
            .build()
            .validateEmptyCurrentPassword();
    }
}
