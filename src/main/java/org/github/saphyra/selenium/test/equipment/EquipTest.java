package org.github.saphyra.selenium.test.equipment;

import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.github.saphyra.selenium.SeleniumTestApplication;
import org.github.saphyra.selenium.logic.domain.ContainerId;
import org.github.saphyra.selenium.logic.domain.EquippedEquipment;
import org.github.saphyra.selenium.logic.domain.UnequippedEquipment;
import org.github.saphyra.selenium.logic.flow.CreateCharacter;
import org.github.saphyra.selenium.logic.flow.Navigate;
import org.github.saphyra.selenium.logic.flow.Registration;
import org.github.saphyra.selenium.logic.flow.SelectCharacter;
import org.github.saphyra.selenium.logic.validator.NotificationValidator;
import org.github.saphyra.selenium.test.equipment.util.EquipmentElementSearcher;
import org.github.saphyra.selenium.test.equipment.util.EquipmentTestHelper;

import static org.junit.Assert.assertEquals;
import static org.github.saphyra.selenium.logic.util.WaitUtil.getWithWait;

public class EquipTest extends SeleniumTestApplication {
    private EquipmentElementSearcher equipmentElementSearcher;
    private NotificationValidator notificationValidator;
    private EquipmentTestHelper equipmentTestHelper;


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
