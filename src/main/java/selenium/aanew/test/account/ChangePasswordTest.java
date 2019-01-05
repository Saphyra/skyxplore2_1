package selenium.aanew.test.account;

import org.openqa.selenium.WebDriver;
import selenium.aanew.flow.Navigate;
import selenium.aanew.flow.Registration;
import selenium.aanew.test.account.changepassword.TooShortPasswordTest;
import selenium.aanew.validator.FieldValidator;
import selenium.page.AccountPage;

import static selenium.aanew.util.LinkUtil.ACCOUNT;
import static skyxplore.controller.request.user.UserRegistrationRequest.PASSWORD_MAX_LENGTH;

public class ChangePasswordTest {
    private static final String TOO_LONG_PASSWORD;

    static {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i <= PASSWORD_MAX_LENGTH + 1; i++) {
            builder.append("a");
        }
        TOO_LONG_PASSWORD = builder.toString();
    }

    private static final String ERROR_MESSAGE_PASSWORD_TOO_LONG = "Új jelszó túl hosszú! (Maximum 30 karakter)";
    private static final String ERROR_MESSAGE_BAD_CONFIRM_PASSWORD = "A jelszavak nem egyeznek.";
    private static final String ERROR_MESSAGE_EMPTY_CURRENT_PASSWORD = "Jelszó megadása kötelező!";

    private static final String NOTIFICATION_BAD_PASSWORD = "Hibás jelszó.";
    private static final String NOTIFICATION_SUCCESSFUL_PASSWORD_CHANGE = "Jelszó megváltoztatása sikeres.";

    private final WebDriver driver;
    private final Registration registration;
    private final AccountPage accountPage;
    private final FieldValidator fieldValidator;
    private final Navigate navigate;

    public ChangePasswordTest(WebDriver driver) {
        this.driver = driver;
        this.registration = new Registration(driver);
        this.accountPage = new AccountPage(driver);
        this.fieldValidator = new FieldValidator(driver, ACCOUNT);
        this.navigate = new Navigate(driver);
    }

    public void runTests() {
        TooShortPasswordTest.builder()
            .driver(driver)
            .registration(registration)
            .accountPage(accountPage)
            .fieldValidator(fieldValidator)
            .navigate(navigate)
            .build()
            .validateTooShortPassword();

        /*validateTooLongPassword();
        validateConfirmPassword();
        validateEmptyCurrentPassword();
        validateBadPassword();
        validateHappyPath();*/
    }


}
