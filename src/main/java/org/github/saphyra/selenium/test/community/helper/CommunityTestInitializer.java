package org.github.saphyra.selenium.test.community.helper;

import lombok.RequiredArgsConstructor;
import org.github.saphyra.selenium.logic.domain.SeleniumAccount;
import org.github.saphyra.selenium.logic.domain.SeleniumCharacter;
import org.github.saphyra.selenium.logic.domain.SeleniumUser;
import org.github.saphyra.selenium.logic.flow.CreateCharacter;
import org.github.saphyra.selenium.logic.flow.Logout;
import org.github.saphyra.selenium.logic.flow.Registration;

import java.util.LinkedList;
import java.util.List;

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

    private SeleniumAccount createAccount(int characterNum) {
        SeleniumUser user = registration.registerUser();
        List<SeleniumCharacter> characters = createCharactersForUser(characterNum);
        return SeleniumAccount.builder()
            .user(user)
            .characters(characters)
            .build();
    }

    private List<SeleniumCharacter> createCharactersForUser(int characterNum) {
        List<SeleniumCharacter> result = new LinkedList<>();
        for (int i = 0; i < characterNum; i++) {
            result.add(createCharacter.createCharacter());
        }
        return result;
    }
}
