package selenium.cases.registration;

import org.openqa.selenium.WebDriver;
import selenium.cases.registration.testcase.RegistrationEmailTest;
import selenium.cases.registration.testcase.RegistrationPasswordTest;
import selenium.cases.registration.testcase.RegistrationUserNameTest;
import selenium.domain.SeleniumUser;
import selenium.flow.Logout;
import selenium.flow.Registration;
import selenium.page.IndexPage;
import selenium.validator.FieldValidator;

import static selenium.util.DOMUtil.cleanNotifications;
import static selenium.util.LinkUtil.HOST;

public class RegistrationTest {
    private final WebDriver driver;
    private final IndexPage indexPage;
    private final SeleniumUser currentUser;
    private final SeleniumUser newUser = SeleniumUser.create();
    private final FieldValidator fieldValidator;

    private RegistrationTest(WebDriver driver, SeleniumUser user) {
        this.driver = driver;
        this.currentUser = user;
        this.indexPage = new IndexPage(driver);
        this.fieldValidator = new FieldValidator(driver, HOST);
    }

    public static void run(WebDriver driver) {
        SeleniumUser currentUser = new Registration(driver).registerUser();
        new Logout(driver).logOut();

        RegistrationTest testCase = new RegistrationTest(driver, currentUser);
        testCase.validateUserName();
        testCase.validatePasswords();
        testCase.validateEmail();
    }

    private void validateUserName() {
        RegistrationUserNameTest test = RegistrationUserNameTest.builder()
            .driver(driver)
            .currentUser(currentUser)
            .indexPage(indexPage)
            .newUser(newUser)
            .registrationTest(this)
            .fieldValidator(fieldValidator)
            .build();
        test.validateUserName();
    }

    private void validatePasswords() {
        RegistrationPasswordTest test = RegistrationPasswordTest.builder()
            .driver(driver)
            .currentUser(currentUser)
            .indexPage(indexPage)
            .newUser(newUser)
            .registrationTest(this)
            .fieldValidator(fieldValidator)
            .build();
        test.validatePasswords();
    }

    private void validateEmail() {
        RegistrationEmailTest test = RegistrationEmailTest.builder()
            .driver(driver)
            .currentUser(currentUser)
            .indexPage(indexPage)
            .newUser(newUser)
            .registrationTest(this)
            .fieldValidator(fieldValidator)
            .build();
        test.validateEmail();
    }

    public void cleanUp() {
        cleanNotifications(driver);

        indexPage.getRegistrationUserNameField().clear();
        indexPage.getRegistrationPasswordField().clear();
        indexPage.getRegistrationConfirmPasswordField().clear();
        indexPage.getRegistrationEmailField().clear();
    }
}
