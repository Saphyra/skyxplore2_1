package com.github.saphyra.selenium.test.equipment.util;

import com.github.saphyra.selenium.logic.domain.SeleniumCharacter;
import lombok.RequiredArgsConstructor;
import com.github.saphyra.selenium.logic.flow.CreateCharacter;
import com.github.saphyra.selenium.logic.flow.Navigate;
import com.github.saphyra.selenium.logic.flow.Registration;
import com.github.saphyra.selenium.logic.flow.SelectCharacter;

import org.junit.Assert;

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
        Assert.assertEquals(amount, equipmentElementSearcher.getUnequippedEquipmentById(itemId).getAmount());
    }
}
