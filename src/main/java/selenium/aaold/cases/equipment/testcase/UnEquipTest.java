package selenium.aaold.cases.equipment.testcase;

import static org.junit.Assert.assertEquals;

import lombok.RequiredArgsConstructor;
import selenium.aanew.logic.domain.ContainerId;
import selenium.aanew.logic.domain.EquippedEquipment;
import selenium.aaold.cases.equipment.testcase.helper.ElementSearcher;
import selenium.aaold.cases.equipment.testcase.helper.EquipmentVerifier;
import selenium.aanew.logic.validator.NotificationValidator;

@RequiredArgsConstructor
public class UnEquipTest {
    private static final String UNEQUIP_SUCCESFUL_MESSAGE = "Leszerelés sikeres.";
    private final ElementSearcher elementSearcher;
    private final NotificationValidator notificationValidator;
    private final EquipmentVerifier equipmentVerifier;

    public String testUnEquip() {
        int emptySlotCountBeforeUnequip = elementSearcher.countEmptySlotsInContainer(ContainerId.FRONT_WEAPON);
        EquippedEquipment equipment = elementSearcher.findAnyEquippedFromContainer(ContainerId.FRONT_WEAPON);
        equipment.unequip();

        verifySuccess(emptySlotCountBeforeUnequip, equipment);
        return equipment.getId();
    }


    private void verifySuccess(int emptySlotCountBeforeUnequip, EquippedEquipment equipment) {
        notificationValidator.verifyNotificationVisibility(UNEQUIP_SUCCESFUL_MESSAGE);
        int emptySlotCountAfterUnequip = elementSearcher.countEmptySlotsInContainer(ContainerId.FRONT_WEAPON);
        assertEquals(emptySlotCountBeforeUnequip + 1, emptySlotCountAfterUnequip);

        equipmentVerifier.verifyItemInStorage(equipment.getId(), 1);
    }
}
