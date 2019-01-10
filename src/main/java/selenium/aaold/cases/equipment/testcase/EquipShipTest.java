package selenium.aaold.cases.equipment.testcase;

import lombok.RequiredArgsConstructor;
import selenium.aanew.logic.domain.ContainerId;
import selenium.aanew.test.equipment.util.ElementSearcher;
import selenium.aanew.logic.validator.NotificationValidator;

import static org.junit.Assert.assertEquals;
import static selenium.aaold.cases.equipment.EquipmentTest.TEST_SHIP_ID;

@RequiredArgsConstructor
public class EquipShipTest {
    private final ElementSearcher elementSearcher;
    private final NotificationValidator notificationValidator;

    public void testEquipShip(){
        elementSearcher.getUnequippedEquipmentById(TEST_SHIP_ID).getElement().click();

        verifySuccess();
    }

    private void verifySuccess() {
        notificationValidator.verifyNotificationVisibility("Hajó felszerelve.");

        for(ContainerId containerId : ContainerId.values()){
            assertEquals(0, elementSearcher.countNonEmptySlotsInContainer(containerId));
        }
    }
}
