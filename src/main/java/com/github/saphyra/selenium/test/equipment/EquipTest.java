package com.github.saphyra.selenium.test.equipment;

import org.junit.Test;
import org.openqa.selenium.WebElement;
import com.github.saphyra.selenium.SeleniumTestApplication;
import com.github.saphyra.selenium.logic.domain.ContainerId;
import com.github.saphyra.selenium.logic.domain.EquippedEquipment;
import com.github.saphyra.selenium.logic.domain.UnequippedEquipment;
import com.github.saphyra.selenium.logic.flow.CreateCharacter;
import com.github.saphyra.selenium.logic.flow.Navigate;
import com.github.saphyra.selenium.logic.flow.Registration;
import com.github.saphyra.selenium.logic.flow.SelectCharacter;
import com.github.saphyra.selenium.logic.validator.NotificationValidator;
import com.github.saphyra.selenium.test.equipment.util.EquipmentElementSearcher;
import com.github.saphyra.selenium.test.equipment.util.EquipmentTestHelper;

import static org.junit.Assert.assertEquals;
import static com.github.saphyra.selenium.logic.util.WaitUtil.getWithWait;

public class EquipTest extends SeleniumTestApplication {
    private EquipmentElementSearcher equipmentElementSearcher;
    private NotificationValidator notificationValidator;
    private EquipmentTestHelper equipmentTestHelper;


    @Override
    protected void init() {
        equipmentElementSearcher = new EquipmentElementSearcher(driver);
        equipmentTestHelper = new EquipmentTestHelper(
            new Registration(driver),
            new CreateCharacter(driver),
            new SelectCharacter(driver),
            new Navigate(driver),
            equipmentElementSearcher
        );
        notificationValidator = new NotificationValidator(driver);
    }

    @Test
    public void testEquip() {
        equipmentTestHelper.registerAndGoToEquipmentPage();

        EquippedEquipment equipment = equipmentElementSearcher.findAnyEquippedFromContainer(ContainerId.FRONT_WEAPON);
        equipment.unequip();

        String unequippedItemId = getWithWait(
            () -> equipmentElementSearcher.getAllUnequippedEquipments().stream().findFirst(),
            "Querying unequipped item..."
        )
            .orElseThrow(() -> new RuntimeException("No unequippedItem found."))
            .getId();

        int emptySlotCountBefore = equipmentElementSearcher.countEmptySlotsInContainer(ContainerId.FRONT_WEAPON);
        UnequippedEquipment unequippedEquipment = equipmentElementSearcher.getUnequippedEquipmentById(unequippedItemId);
        WebElement emptySlot = equipmentElementSearcher.getEmptySlotFromContainer(ContainerId.FRONT_WEAPON);

        //TODO fix
        //verifyEquipmentSuccessful(emptySlotCountBefore);
    }

    private void verifyEquipmentSuccessful(int emptySlotCountBefore) {
        notificationValidator.verifyNotificationVisibility("Felszerelve.");

        assertEquals(emptySlotCountBefore - 1, equipmentElementSearcher.countEmptySlotsInContainer(ContainerId.FRONT_WEAPON));
    }
}
