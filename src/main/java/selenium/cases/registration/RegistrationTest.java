package selenium.cases.registration;

import org.openqa.selenium.WebDriver;
import selenium.cases.registration.testcase.RegistrationEmailTest;
import selenium.cases.registration.testcase.RegistrationPasswordTest;
import selenium.cases.registration.testcase.RegistrationUserNameTest;
import selenium.domain.SeleniumUser;
import selenium.flow.Logout;
import selenium.flow.Registration;
import selenium.page.IndexPage;

public class RegistrationTest {
    private final WebDriver driver;
    private final IndexPage indexPage;
    private final SeleniumUser currentUser;
    private final SeleniumUser newUser = SeleniumUser.create();
    private final RegistrationVerificator registrationVerificator;

    private RegistrationTest(WebDriver driver, SeleniumUser user) {
        this.driver = driver;
        this.currentUser = user;
        this.indexPage = new IndexPage(driver);
        this.registrationVerificator = new RegistrationVerificator(driver, indexPage);
    }

    public static void run(WebDriver driver) {
        SeleniumUser currentUser = new Registration(driver).registerUser();
        new Logout(driver).logOut();

        RegistrationTest testCase = new RegistrationTest(driver, currentUser);
        testCase.validateUserName();
        testCase.validatePasswords();
        testCase.validateEmail();
        testCase.validateSuccessfulRegistration();
    }

    private void validateUserName() {
        RegistrationUserNameTest test = RegistrationUserNameTest.builder()
            .currentUser(currentUser)
            .indexPage(indexPage)
            .newUser(newUser)
            .registrationTest(this)
            .registrationVerificator(registrationVerificator)
            .build();
        test.validateUserName();
    }

    private void validatePasswords() {
        RegistrationPasswordTest test = RegistrationPasswordTest.builder()
            .currentUser(currentUser)
            .indexPage(indexPage)
            .newUser(newUser)
            .registrationTest(this)
            .registrationVerificator(registrationVerificator)
            .build();
        test.validatePasswords();
    }

    private void validateEmail() {
        RegistrationEmailTest test = RegistrationEmailTest.builder()
            .currentUser(currentUser)
            .indexPage(indexPage)
            .newUser(newUser)
            .registrationTest(this)
            .registrationVerificator(registrationVerificator)
            .build();
        test.validateEmail();
    }

    public void cleanUp() {
        indexPage.getRegistrationUserNameField().clear();
        indexPage.getRegistrationPasswordField().clear();
        indexPage.getRegistrationConfirmPasswordField().clear();
        indexPage.getRegistrationEmailField().clear();
    }

    private void validateSuccessfulRegistration(){
        cleanUp();
        new Registration(driver).registerUser(newUser);
    }
}
