package selenium.aanew.test.characterselect.createcharacter.helper;

import lombok.RequiredArgsConstructor;
import selenium.aanew.logic.domain.SeleniumUser;
import selenium.aanew.logic.flow.Registration;

@RequiredArgsConstructor
public class CreateCharacterTestHelper {
    private final Registration registration;

    public SeleniumUser registerUser() {
        return registration.registerUser();
    }
}
