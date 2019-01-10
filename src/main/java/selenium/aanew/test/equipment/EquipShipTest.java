package selenium.aanew.test.equipment;

import org.junit.Test;
import selenium.aanew.SeleniumTestApplication;
import selenium.aanew.logic.domain.ContainerId;
import selenium.aanew.logic.domain.SeleniumCharacter;
import selenium.aanew.logic.flow.BuyItem;
import selenium.aanew.logic.flow.CreateCharacter;
import selenium.aanew.logic.flow.Navigate;
import selenium.aanew.logic.flow.Registration;
import selenium.aanew.logic.flow.SelectCharacter;
import selenium.aanew.logic.validator.NotificationValidator;
import selenium.aanew.test.equipment.util.EquipmentElementSearcher;

import static org.junit.Assert.assertEquals;

public class EquipShipTest extends SeleniumTestApplication {
    private static final String TEST_SHIP_ID = "sta-02";
    private static final String NOTIFICATION_SHIP_EQUIPPED = "Hajó felszerelve.";

    private Registration registration;
    private CreateCharacter createCharacter;
    private SelectCharacter selectCharacter;
    private Navigate navigate;
    private BuyItem buyItem;
    private EquipmentElementSearcher equipmentElementSearcher;
    private NotificationValidator notificationValidator;

    @Override
    protected void init() {
        registration = new Registration(driver);
        createCharacter = new CreateCharacter(driver);
        selectCharacter = new SelectCharacter(driver);
        navigate = new Navigate(driver);
        buyItem = new BuyItem(driver);
        equipmentElementSearcher = new EquipmentElementSearcher(driver);
        notificationValidator = new NotificationValidator(driver);
    }

    @Test
    public void testEquipShip() {
        initForEquipShipTest();

        equipmentElementSearcher.getUnequippedEquipmentById(TEST_SHIP_ID).getElement().click();

        verifySuccess();
    }

    private void initForEquipShipTest() {
        registration.registerUser();
        SeleniumCharacter character = createCharacter.createCharacter();
        selectCharacter.selectCharacter(character);
        navigate.toShop();
        buyItem.buyItem(TEST_SHIP_ID, 1);
        navigate.toEquipmentPage();
    }

    private void verifySuccess() {
        notificationValidator.verifyNotificationVisibility(NOTIFICATION_SHIP_EQUIPPED);

        verifyEmptyShip();
    }

    private void verifyEmptyShip() {
        for (ContainerId containerId : ContainerId.values()) {
            assertEquals(0, equipmentElementSearcher.countNonEmptySlotsInContainer(containerId));
        }
    }
}
