package selenium.test.equipment;

import org.junit.Test;
import selenium.SeleniumTestApplication;
import selenium.logic.domain.ContainerId;
import selenium.logic.domain.EquippedEquipment;
import selenium.logic.flow.CreateCharacter;
import selenium.logic.flow.Navigate;
import selenium.logic.flow.Registration;
import selenium.logic.flow.SelectCharacter;
import selenium.logic.validator.NotificationValidator;
import selenium.test.equipment.util.EquipmentElementSearcher;
import selenium.test.equipment.util.EquipmentTestHelper;

import static org.junit.Assert.assertEquals;

public class UnequipTest extends SeleniumTestApplication {
    private static final String UNEQUIP_SUCCESFUL_MESSAGE = "Leszerelés sikeres.";

    private EquipmentTestHelper equipmentTestHelper;
    private NotificationValidator notificationValidator;
    private EquipmentElementSearcher equipmentElementSearcher;

    @Override
    protected void init() {
        equipmentElementSearcher = new EquipmentElementSearcher(driver);
        equipmentTestHelper = new EquipmentTestHelper(
            new Registration(driver, messageCodes),
            new CreateCharacter(driver),
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
        notificationValidator.verifyNotificationVisibility(UNEQUIP_SUCCESFUL_MESSAGE);
        int emptySlotCountAfterUnequip = equipmentElementSearcher.countEmptySlotsInContainer(ContainerId.FRONT_WEAPON);
        assertEquals(emptySlotCountBeforeUnequip + 1, emptySlotCountAfterUnequip);

        equipmentTestHelper.verifyItemInStorage(equipment.getId(), 1);
    }
}
