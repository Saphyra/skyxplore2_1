package selenium.aanew.test.equipment;

import org.junit.Test;
import selenium.aanew.SeleniumTestApplication;
import selenium.aanew.logic.domain.ContainerId;
import selenium.aanew.logic.domain.EquippedEquipment;
import selenium.aanew.logic.flow.CreateCharacter;
import selenium.aanew.logic.flow.Navigate;
import selenium.aanew.logic.flow.Registration;
import selenium.aanew.logic.flow.SelectCharacter;
import selenium.aanew.logic.validator.NotificationValidator;
import selenium.aanew.test.equipment.util.ElementSearcher;
import selenium.aanew.test.equipment.util.EquipmentTestHelper;

import static org.junit.Assert.assertEquals;

public class UnequipTest extends SeleniumTestApplication {
    private static final String UNEQUIP_SUCCESFUL_MESSAGE = "Leszerelés sikeres.";

    private EquipmentTestHelper equipmentTestHelper;
    private NotificationValidator notificationValidator;
    private ElementSearcher elementSearcher;

    @Override
    protected void init() {
        elementSearcher = new ElementSearcher(driver);
        equipmentTestHelper = new EquipmentTestHelper(
            new Registration(driver),
            new CreateCharacter(driver),
            new SelectCharacter(driver),
            new Navigate(driver),
            elementSearcher
        );
        notificationValidator = new NotificationValidator(driver);
    }

    @Test
    public void testUnEquip() {
        equipmentTestHelper.registerAndGoToEquipmentPage();

        int emptySlotCountBeforeUnequip = elementSearcher.countEmptySlotsInContainer(ContainerId.FRONT_WEAPON);

        EquippedEquipment equipment = elementSearcher.findAnyEquippedFromContainer(ContainerId.FRONT_WEAPON);
        equipment.unequip();

        verifySuccess(emptySlotCountBeforeUnequip, equipment);
    }


    private void verifySuccess(int emptySlotCountBeforeUnequip, EquippedEquipment equipment) {
        notificationValidator.verifyNotificationVisibility(UNEQUIP_SUCCESFUL_MESSAGE);
        int emptySlotCountAfterUnequip = elementSearcher.countEmptySlotsInContainer(ContainerId.FRONT_WEAPON);
        assertEquals(emptySlotCountBeforeUnequip + 1, emptySlotCountAfterUnequip);

        equipmentTestHelper.verifyItemInStorage(equipment.getId(), 1);
    }
}
