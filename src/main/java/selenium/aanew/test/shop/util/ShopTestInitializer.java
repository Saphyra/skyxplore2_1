package selenium.aanew.test.shop.util;

import lombok.RequiredArgsConstructor;
import selenium.aanew.logic.domain.SeleniumCharacter;
import selenium.aanew.logic.flow.CreateCharacter;
import selenium.aanew.logic.flow.Navigate;
import selenium.aanew.logic.flow.Registration;
import selenium.aanew.logic.flow.SelectCharacter;

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
