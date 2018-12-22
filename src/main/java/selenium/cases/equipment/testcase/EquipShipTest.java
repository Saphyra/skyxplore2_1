package selenium.cases.equipment.testcase;

import lombok.RequiredArgsConstructor;
import selenium.cases.equipment.testcase.domain.ContainerId;
import selenium.cases.equipment.testcase.helper.ElementSearcher;
import selenium.validator.NotificationValidator;

import static org.junit.Assert.assertEquals;
import static selenium.cases.equipment.EquipmentTest.TEST_SHIP_ID;

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
