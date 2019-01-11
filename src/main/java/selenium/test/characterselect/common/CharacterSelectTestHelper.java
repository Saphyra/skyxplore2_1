package selenium.test.characterselect.common;

import lombok.RequiredArgsConstructor;
import selenium.logic.domain.SeleniumCharacter;
import selenium.logic.domain.SeleniumUser;
import selenium.logic.flow.CreateCharacter;
import selenium.logic.flow.Registration;

@RequiredArgsConstructor
public class CharacterSelectTestHelper {
    private final Registration registration;
    private final CreateCharacter createCharacter;

    public SeleniumUser registerUser() {
        return registration.registerUser();
    }

    public SeleniumCharacter createCharacter() {
        return createCharacter.createCharacter();
    }

    public SeleniumCharacter registerAndCreateCharacter(){
        registerUser();
        return createCharacter();
    }
}
