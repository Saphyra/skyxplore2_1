package com.github.saphyra.selenium.test.equipment;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.saphyra.selenium.SeleniumTestApplication;
import com.github.saphyra.selenium.logic.domain.ContainerId;
import com.github.saphyra.selenium.logic.domain.EquippedEquipment;
import com.github.saphyra.selenium.logic.flow.CreateCharacter;
import com.github.saphyra.selenium.logic.flow.Navigate;
import com.github.saphyra.selenium.logic.flow.Registration;
import com.github.saphyra.selenium.logic.flow.SelectCharacter;
import com.github.saphyra.selenium.logic.validator.NotificationValidator;
import com.github.saphyra.selenium.test.equipment.util.EquipmentElementSearcher;
import com.github.saphyra.selenium.test.equipment.util.EquipmentTestHelper;

public class UnequipTest extends SeleniumTestApplication {
    private static final String MESSAGE_CODE_ITEM_UNEQUIPPED = "ITEM_UNEQUIPPED";

    private EquipmentTestHelper equipmentTestHelper;
    private NotificationValidator notificationValidator;
    private EquipmentElementSearcher equipmentElementSearcher;

    @Override
    protected void init() {
        equipmentElementSearcher = new EquipmentElementSearcher(driver);
        equipmentTestHelper = new EquipmentTestHelper(
            new Registration(driver, messageCodes),
            new CreateCharacter(driver, messageCodes),
            new SelectCharacter(driver),
            new Navigate(driver),
            equipmentElementSearcher
        );
        notificationValidator = new NotificationValidator(driver);
    }

    @Test
    public void testUnEquip() {
        equipmentTestHelper.registerAndGoToEquipmentPage();

        int emptySlotCountBeforeUnequip = equipmentElementSearcher.countEmptySlotsInContainer(ContainerId.FRONT_WEAPON);

        EquippedEquipment equipment = equipmentElementSearcher.findAnyEquippedFromContainer(ContainerId.FRONT_WEAPON);
        equipment.unequip();

        verifySuccess(emptySlotCountBeforeUnequip, equipment);
    }


    private void verifySuccess(int emptySlotCountBeforeUnequip, EquippedEquipment equipment) {
        notificationValidator.verifyNotificationVisibility(messageCodes.get(MESSAGE_CODE_ITEM_UNEQUIPPED));
        int emptySlotCountAfterUnequip = equipmentElementSearcher.countEmptySlotsInContainer(ContainerId.FRONT_WEAPON);
        assertEquals(emptySlotCountBeforeUnequip + 1, emptySlotCountAfterUnequip);

        equipmentTestHelper.verifyItemInStorage(equipment.getId(), 1);
    }
}
