package org.github.saphyra.selenium.test.equipment.util;

import lombok.RequiredArgsConstructor;
import org.github.saphyra.selenium.logic.domain.SeleniumCharacter;
import org.github.saphyra.selenium.logic.flow.CreateCharacter;
import org.github.saphyra.selenium.logic.flow.Navigate;
import org.github.saphyra.selenium.logic.flow.Registration;
import org.github.saphyra.selenium.logic.flow.SelectCharacter;

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
