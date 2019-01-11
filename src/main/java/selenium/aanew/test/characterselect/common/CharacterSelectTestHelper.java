package selenium.aanew.test.characterselect.common;

import lombok.RequiredArgsConstructor;
import selenium.aanew.logic.domain.SeleniumCharacter;
import selenium.aanew.logic.domain.SeleniumUser;
import selenium.aanew.logic.flow.CreateCharacter;
import selenium.aanew.logic.flow.Registration;

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
