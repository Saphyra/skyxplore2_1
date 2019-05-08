package com.github.saphyra.selenium.test.factory.util;

import lombok.RequiredArgsConstructor;
import com.github.saphyra.selenium.logic.domain.SeleniumCharacter;
import com.github.saphyra.selenium.logic.flow.CreateCharacter;
import com.github.saphyra.selenium.logic.flow.Navigate;
import com.github.saphyra.selenium.logic.flow.Registration;
import com.github.saphyra.selenium.logic.flow.SelectCharacter;

@RequiredArgsConstructor
public class FactoryTestHelper {
    private final Registration registration;
    private final CreateCharacter createCharacter;
    private final SelectCharacter selectCharacter;
    private final Navigate navigate;

    @SuppressWarnings("UnusedReturnValue")
    public SeleniumCharacter registerAndGoToFactoryPage() {
        registration.registerUser();
        SeleniumCharacter character = createCharacter.createCharacter();
        selectCharacter.selectCharacter(character);
        navigate.toFactory();
        return character;
    }
}
