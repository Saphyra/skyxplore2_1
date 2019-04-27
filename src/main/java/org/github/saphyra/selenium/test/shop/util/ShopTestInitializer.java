package org.github.saphyra.selenium.test.shop.util;

import lombok.RequiredArgsConstructor;
import org.github.saphyra.selenium.logic.domain.SeleniumCharacter;
import org.github.saphyra.selenium.logic.flow.CreateCharacter;
import org.github.saphyra.selenium.logic.flow.Navigate;
import org.github.saphyra.selenium.logic.flow.Registration;
import org.github.saphyra.selenium.logic.flow.SelectCharacter;

@RequiredArgsConstructor
public class ShopTestInitializer {
    private final Registration registration;
    private final CreateCharacter createCharacter;
    private final SelectCharacter selectCharacter;
    private final Navigate navigate;

    public void registerAndGoToShop() {
        registration.registerUser();
        SeleniumCharacter testCharacter = createCharacter.createCharacter();
        selectCharacter.selectCharacter(testCharacter);
        navigate.toShop();
    }
}
