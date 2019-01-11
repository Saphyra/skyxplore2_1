package selenium.test.shop.util;

import lombok.RequiredArgsConstructor;
import selenium.logic.domain.SeleniumCharacter;
import selenium.logic.flow.CreateCharacter;
import selenium.logic.flow.Navigate;
import selenium.logic.flow.Registration;
import selenium.logic.flow.SelectCharacter;

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
