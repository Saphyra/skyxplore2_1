package org.github.saphyra.selenium.test.characterselect.common;

import lombok.RequiredArgsConstructor;
import org.github.saphyra.selenium.logic.domain.SeleniumCharacter;
import org.github.saphyra.selenium.logic.domain.SeleniumUser;
import org.github.saphyra.selenium.logic.flow.CreateCharacter;
import org.github.saphyra.selenium.logic.flow.Registration;

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
}
