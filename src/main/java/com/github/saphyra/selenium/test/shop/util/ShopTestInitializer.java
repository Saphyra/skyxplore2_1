package com.github.saphyra.selenium.test.shop.util;

import lombok.RequiredArgsConstructor;
import com.github.saphyra.selenium.logic.domain.SeleniumCharacter;
import com.github.saphyra.selenium.logic.flow.CreateCharacter;
import com.github.saphyra.selenium.logic.flow.Navigate;
import com.github.saphyra.selenium.logic.flow.Registration;
import com.github.saphyra.selenium.logic.flow.SelectCharacter;

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
