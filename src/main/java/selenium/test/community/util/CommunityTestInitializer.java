package selenium.test.community.util;

import java.util.LinkedList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import selenium.logic.domain.SeleniumAccount;
import selenium.logic.domain.SeleniumCharacter;
import selenium.logic.domain.SeleniumUser;
import selenium.logic.flow.CreateCharacter;
import selenium.logic.flow.Logout;
import selenium.logic.flow.Registration;

@RequiredArgsConstructor
public class CommunityTestInitializer {
    private final Registration registration;
    private final CreateCharacter createCharacter;
    private final Logout logout;

    public List<SeleniumAccount> registerAccounts(int[] characterNums) {
        List<SeleniumAccount> result = new LinkedList<>();
        for (int characterNum : characterNums) {
            result.add(createAccount(characterNum));
            logout.logOut();
        }
        return result;
    }

    public SeleniumAccount createAccount(int characterNum) {
        SeleniumUser user = registration.registerUser();
        List<SeleniumCharacter> characters = createCharactersForUser(user, characterNum);
        return SeleniumAccount.builder()
            .user(user)
            .characters(characters)
            .build();
    }

    private List<SeleniumCharacter> createCharactersForUser(SeleniumUser user, int characterNum) {
        List<SeleniumCharacter> result = new LinkedList<>();
        for (int i = 0; i < characterNum; i++) {
            result.add(createCharacter.createCharacter());
        }
        return result;
    }
}
