package selenium.aaold.cases.registration;

import org.openqa.selenium.WebDriver;
import selenium.aanew.logic.domain.SeleniumUser;
import selenium.aanew.logic.flow.Logout;
import selenium.aanew.logic.flow.Registration;
import selenium.aanew.logic.page.IndexPage;
import selenium.aanew.logic.validator.FieldValidator;
import selenium.aaold.cases.registration.testcase.RegistrationPasswordTest;
import selenium.aaold.cases.registration.testcase.RegistrationUserNameTest;

import static selenium.aanew.logic.util.LinkUtil.HOST;
import static selenium.aanew.logic.util.Util.cleanNotifications;

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

    public void cleanUp() {
        cleanNotifications(driver);

        indexPage.getRegistrationUserNameField().clear();
        indexPage.getRegistrationPasswordField().clear();
        indexPage.getRegistrationConfirmPasswordField().clear();
        indexPage.getRegistrationEmailField().clear();
    }
}
