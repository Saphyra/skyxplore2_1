package selenium.test.equipment.util;

import lombok.RequiredArgsConstructor;
import selenium.logic.domain.SeleniumCharacter;
import selenium.logic.flow.CreateCharacter;
import selenium.logic.flow.Navigate;
import selenium.logic.flow.Registration;
import selenium.logic.flow.SelectCharacter;

import static org.junit.Assert.assertEquals;

@RequiredArgsConstructor
public class EquipmentTestHelper {
    private final Registration registration;
    private final CreateCharacter createCharacter;
    private final SelectCharacter selectCharacter;
    private final Navigate navigate;
    private final EquipmentElementSearcher equipmentElementSearcher;

    @SuppressWarnings("UnusedReturnValue")
    public SeleniumCharacter registerAndGoToEquipmentPage() {
        registration.registerUser();
        SeleniumCharacter character = createCharacter.createCharacter();
        selectCharacter.selectCharacter(character);
        navigate.toEquipmentPage();
        return character;
    }

    public void verifyItemInStorage(String itemId, int amount) {
        assertEquals(amount, equipmentElementSearcher.getUnequippedEquipmentById(itemId).getAmount());
    }
}
