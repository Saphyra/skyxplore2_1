package selenium.aanew.test.equipment.util;

import lombok.RequiredArgsConstructor;
import selenium.aanew.logic.domain.SeleniumCharacter;
import selenium.aanew.logic.flow.CreateCharacter;
import selenium.aanew.logic.flow.Navigate;
import selenium.aanew.logic.flow.Registration;
import selenium.aanew.logic.flow.SelectCharacter;

import static org.junit.Assert.assertEquals;

@RequiredArgsConstructor
public class EquipmentTestHelper {
    private final Registration registration;
    private final CreateCharacter createCharacter;
    private final SelectCharacter selectCharacter;
    private final Navigate navigate;
    private final ElementSearcher elementSearcher;

    public SeleniumCharacter registerAndGoToEquipmentPage(){
        registration.registerUser();
        SeleniumCharacter character = createCharacter.createCharacter();
        selectCharacter.selectCharacter(character);
        navigate.toEquipmentPage();
        return character;
    }

    public void verifyItemInStorage(String itemId, int amount){
        assertEquals(amount, elementSearcher.getUnequippedEquipmentById(itemId).getAmount());
    }
}
