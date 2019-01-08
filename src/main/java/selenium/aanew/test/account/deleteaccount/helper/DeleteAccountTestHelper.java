package selenium.aanew.test.account.deleteaccount.helper;

import lombok.RequiredArgsConstructor;
import selenium.aanew.logic.domain.SeleniumUser;
import selenium.aanew.logic.flow.Navigate;
import selenium.aanew.logic.flow.Registration;
import selenium.aanew.logic.page.AccountPage;

@RequiredArgsConstructor
public class DeleteAccountTestHelper {
    private final Registration registration;
    private final Navigate navigate;
    private final AccountPage accountPage;

    public SeleniumUser registerAndNavigateToAccount() {
        SeleniumUser user = registration.registerUser();
        navigate.toAccountPage();
        return user;
    }
}
