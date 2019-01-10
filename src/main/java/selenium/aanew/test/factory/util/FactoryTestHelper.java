package selenium.aanew.test.factory.util;

import lombok.RequiredArgsConstructor;
import selenium.aanew.logic.domain.SeleniumCharacter;
import selenium.aanew.logic.flow.CreateCharacter;
import selenium.aanew.logic.flow.Navigate;
import selenium.aanew.logic.flow.Registration;
import selenium.aanew.logic.flow.SelectCharacter;

@RequiredArgsConstructor
public class FactoryTestHelper {
    private final Registration registration;
    private final CreateCharacter createCharacter;
    private final SelectCharacter selectCharacter;
    private final Navigate navigate;

    public SeleniumCharacter registerAndGoToFactoryPage(){
        registration.registerUser();
        SeleniumCharacter character = createCharacter.createCharacter();
        selectCharacter.selectCharacter(character);
        navigate.toFactory();
        return character;
    }
}
