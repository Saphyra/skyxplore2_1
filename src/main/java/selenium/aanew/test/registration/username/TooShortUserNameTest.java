package selenium.aanew.test.registration.username;

import lombok.Builder;
import org.openqa.selenium.WebElement;
import selenium.aanew.logic.domain.SeleniumUser;
import selenium.aanew.logic.page.IndexPage;
import selenium.aanew.logic.validator.FieldValidator;
import selenium.aanew.test.registration.username.helper.UserNameTestHelper;

import static selenium.aanew.logic.util.Util.crop;
import static skyxplore.controller.request.user.UserRegistrationRequest.USER_NAME_MIN_LENGTH;

@Builder
public class TooShortUserNameTest {
    private static final String ERROR_MESSAGE_TOO_SHORT_USER_NAME = "Felhasználónév túl rövid (Minimum 3 karakter).";

    private final UserNameTestHelper userNameTestHelper;
    private final IndexPage indexPage;
    private final FieldValidator fieldValidator;


    public void testTooShortUserName() {
        SeleniumUser user = SeleniumUser.create();
        userNameTestHelper.setUpForUserNameTest(user);

        WebElement userNameField = indexPage.getRegistrationUserNameField();
        userNameField.sendKeys(crop(user.getUserName(), USER_NAME_MIN_LENGTH - 1));

        fieldValidator.verifyError(
            indexPage.getInvalidUserNameField(),
            ERROR_MESSAGE_TOO_SHORT_USER_NAME,
            userNameField,
            indexPage.getRegisterButton()
        );

    }
}
