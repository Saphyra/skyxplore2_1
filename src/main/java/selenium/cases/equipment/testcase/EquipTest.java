package selenium.cases.equipment.testcase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import selenium.cases.equipment.testcase.domain.ContainerId;
import selenium.cases.equipment.testcase.domain.UnequippedEquipment;
import selenium.cases.equipment.testcase.helper.ElementSearcher;
import selenium.validator.NotificationValidator;

import static org.junit.Assert.assertEquals;

@RequiredArgsConstructor
@Slf4j
public class EquipTest {
    private final ElementSearcher elementSearcher;
    private final WebDriver driver;
    private final NotificationValidator notificationValidator;

    public void testEquip(String unequippedItemId) {
        int emptySlotCountBefore = elementSearcher.countEmptySlotsInContainer(ContainerId.FRONT_WEAPON);
        UnequippedEquipment unequippedEquipment = elementSearcher.getUnequippedEquipmentById(unequippedItemId);
        WebElement emptySlot = elementSearcher.getEmptySlotFromContainer(ContainerId.FRONT_WEAPON);

        //TODO fix
        //verifyEquipmentSuccessful(emptySlotCountBefore);
    }

    private void verifyEquipmentSuccessful(int emptySlotCountBefore) {
        notificationValidator.verifyNotificationVisibility("Felszerelve.");

        assertEquals(emptySlotCountBefore - 1, elementSearcher.countEmptySlotsInContainer(ContainerId.FRONT_WEAPON));
    }
}
