package selenium.cases.community.testcase;

import lombok.Builder;
import selenium.cases.community.domain.SeleniumAccount;
import selenium.flow.Login;
import selenium.flow.Navigate;
import selenium.flow.SelectCharacter;

import java.util.function.Supplier;

@Builder
public class FriendshipTest {
    private final Supplier<SeleniumAccount> seleniumAccountSupplier;
    private final Navigate navigate;
    private final SelectCharacter selectCharacter;
    private final Login login;

    public FriendshipTest testFilter() {
        SeleniumAccount account1 = seleniumAccountSupplier.get();
        SeleniumAccount account2 = seleniumAccountSupplier.get();

        login.login(account1.getUser());
        selectCharacter.selectCharacter(account1.getCharacter1());
        navigate.toCommunityPage();

        return this;
    }

    public FriendshipTest testSendFriendRequest() {
        return this;
    }

    public FriendshipTest testCancelFriendRequest() {
        return this;
    }

    public FriendshipTest testAcceptFriendRequest() {
        return this;
    }
}
