package selenium.aanew.test.equipment;

import org.junit.Test;
import org.openqa.selenium.WebElement;
import selenium.aanew.SeleniumTestApplication;
import selenium.aanew.logic.domain.ContainerId;
import selenium.aanew.logic.domain.EquippedEquipment;
import selenium.aanew.logic.domain.UnequippedEquipment;
import selenium.aanew.logic.flow.CreateCharacter;
import selenium.aanew.logic.flow.Navigate;
import selenium.aanew.logic.flow.Registration;
import selenium.aanew.logic.flow.SelectCharacter;
import selenium.aanew.logic.validator.NotificationValidator;
import selenium.aanew.test.equipment.util.EquipmentElementSearcher;
import selenium.aanew.test.equipment.util.EquipmentTestHelper;

import static org.junit.Assert.assertEquals;

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

        String unequippedItemId = equipmentElementSearcher.getAllUnequippedEquipments().stream()
            .findFirst()
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
