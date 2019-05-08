package com.github.saphyra.selenium.test.characterselect.common;

import com.github.saphyra.selenium.logic.domain.SeleniumCharacter;
import com.github.saphyra.selenium.logic.domain.SeleniumUser;
import lombok.RequiredArgsConstructor;
import com.github.saphyra.selenium.logic.flow.CreateCharacter;
import com.github.saphyra.selenium.logic.flow.Registration;

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
