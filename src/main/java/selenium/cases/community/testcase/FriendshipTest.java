package selenium.cases.community.testcase;

import lombok.Builder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import selenium.cases.community.domain.SeleniumAccount;
import selenium.domain.SeleniumCharacter;
import selenium.flow.Login;
import selenium.flow.Navigate;
import selenium.flow.SelectCharacter;
import selenium.page.CommunityPage;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;
import static selenium.domain.SeleniumCharacter.CHARACTER_NAME_PREFIX;

@Builder
public class FriendshipTest {
    private final Supplier<SeleniumAccount> seleniumAccountSupplier;
    private final Navigate navigate;
    private final SelectCharacter selectCharacter;
    private final Login login;
    private final CommunityPage communityPage;

    public FriendshipTest testFilter() {
        SeleniumAccount account1 = seleniumAccountSupplier.get();
        SeleniumAccount account2 = seleniumAccountSupplier.get();

        login.login(account1.getUser());
        selectCharacter.selectCharacter(account1.getCharacter1());
        navigate.toCommunityPage();

        openAddFriendPage();

        WebElement friendNameInputField = communityPage.getFriendNameInputField();
        friendNameInputField.sendKeys(CHARACTER_NAME_PREFIX);
        verifyDoesNotContainOwnCharacters(account1.getCharacters(), account2.getCharacters());

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

    private void openAddFriendPage() {
        communityPage.getAddFriendButton().click();
        assertTrue(communityPage.getAddFriendContainer().isDisplayed());
    }

    private void verifyDoesNotContainOwnCharacters(List<SeleniumCharacter> shouldNotContain, List<SeleniumCharacter> shouldContain) {
        List<WebElement> searchResult = communityPage.getCharactersCanBeFriendList();
        List<String> characterNames = searchResult.stream()
            .map(element -> element.findElement(By.cssSelector("div:first-child")).getText())
            .collect(Collectors.toList());

        shouldNotContain.forEach(character -> assertTrue(characterNames.stream().noneMatch(s -> s.equals(character.getCharacterName()))));
        shouldContain.forEach(character -> assertTrue(characterNames.stream().anyMatch(s -> s.equals(character.getCharacterName()))));
    }
}
