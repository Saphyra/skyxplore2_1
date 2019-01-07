package selenium.aanew.test.account.changeemail.helper;

import lombok.RequiredArgsConstructor;
import selenium.aanew.logic.domain.SeleniumUser;
import selenium.aanew.logic.flow.Navigate;
import selenium.aanew.logic.flow.Registration;
import selenium.aanew.logic.page.AccountPage;

@RequiredArgsConstructor
public class ChangeEmailTestHelper {
    private final AccountPage accountPage;
    private final Registration registration;
    private final Navigate navigate;

    public SeleniumUser registerAndNavigateToAccount() {
        SeleniumUser user = registration.registerUser();
        navigate.toAccountPage();
        return user;
    }

    public SeleniumUser setUpWithCurrentPassword() {
        SeleniumUser user = registerAndNavigateToAccount();
        accountPage.getChangeEmailPasswordField().sendKeys(user.getPassword());
        return user;
    }

    public SeleniumUser setUpWithRandomEmail(){
        SeleniumUser user = registerAndNavigateToAccount();
        accountPage.getChangeEmailField().sendKeys(SeleniumUser.createRandomEmail());
        return user;
    }
}
