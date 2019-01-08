package selenium.aanew.test.account.changeusername.helper;

import lombok.RequiredArgsConstructor;
import selenium.aanew.logic.domain.SeleniumUser;
import selenium.aanew.logic.flow.Navigate;
import selenium.aanew.logic.flow.Registration;
import selenium.aanew.logic.page.AccountPage;

@RequiredArgsConstructor
public class ChangeUserNameTestHelper {
    private final Registration registration;
    private final AccountPage accountPage;
    private final Navigate navigate;

    public SeleniumUser setUpWithCurrentPassword() {
        SeleniumUser user = registerAndNavigateToAccount();
        accountPage.getChangeUserNamePasswordField().sendKeys(user.getPassword());
        return user;
    }

    public SeleniumUser registerAndNavigateToAccount() {
        SeleniumUser user = registration.registerUser();
        navigate.toAccountPage();
        return user;
    }

    public void setUpWithRandomUserName() {
        registerAndNavigateToAccount();
        accountPage.getChangeUserNameField().sendKeys(SeleniumUser.createRandomUserName());
    }
}
