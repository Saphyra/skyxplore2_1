package selenium.test.equipment;

import org.junit.Test;
import org.openqa.selenium.WebElement;
import selenium.SeleniumTestApplication;
import selenium.logic.domain.ContainerId;
import selenium.logic.domain.EquippedEquipment;
import selenium.logic.domain.UnequippedEquipment;
import selenium.logic.flow.CreateCharacter;
import selenium.logic.flow.Navigate;
import selenium.logic.flow.Registration;
import selenium.logic.flow.SelectCharacter;
import selenium.logic.validator.NotificationValidator;
import selenium.test.equipment.util.EquipmentElementSearcher;
import selenium.test.equipment.util.EquipmentTestHelper;

import static org.junit.Assert.assertEquals;

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
