package org.github.saphyra.selenium.test.equipment;

import org.junit.Test;
import org.github.saphyra.selenium.SeleniumTestApplication;
import org.github.saphyra.selenium.logic.domain.Category;
import org.github.saphyra.selenium.logic.domain.ContainerId;
import org.github.saphyra.selenium.logic.domain.SeleniumCharacter;
import org.github.saphyra.selenium.logic.flow.BuyItem;
import org.github.saphyra.selenium.logic.flow.CreateCharacter;
import org.github.saphyra.selenium.logic.flow.Navigate;
import org.github.saphyra.selenium.logic.flow.Registration;
import org.github.saphyra.selenium.logic.flow.SelectCharacter;
import org.github.saphyra.selenium.logic.validator.NotificationValidator;
import org.github.saphyra.selenium.test.equipment.util.EquipmentElementSearcher;

import static org.junit.Assert.assertEquals;

public class EquipShipTest extends SeleniumTestApplication {
    private static final String TEST_SHIP_ID = "sta-02";
    private static final String MESSAGE_CODE_SHIP_EQUIPPED = "SHIP_EQUIPPED";

    private Registration registration;
    private CreateCharacter createCharacter;
    private SelectCharacter selectCharacter;
    private Navigate navigate;
    private BuyItem buyItem;
    private EquipmentElementSearcher equipmentElementSearcher;
    private NotificationValidator notificationValidator;

    @Override
    protected void init() {
        registration = new Registration(driver, messageCodes);
        createCharacter = new CreateCharacter(driver, messageCodes);
        selectCharacter = new SelectCharacter(driver);
        navigate = new Navigate(driver);
        buyItem = new BuyItem(driver, locale, messageCodes);
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
        buyItem.buyItem(TEST_SHIP_ID, Category.STARTER_SHIP);
        navigate.toEquipmentPage();
    }

    private void verifySuccess() {
        notificationValidator.verifyNotificationVisibility(messageCodes.get(MESSAGE_CODE_SHIP_EQUIPPED));

        verifyEmptyShip();
    }

    private void verifyEmptyShip() {
        for (ContainerId containerId : ContainerId.values()) {
            assertEquals(0, equipmentElementSearcher.countNonEmptySlotsInContainer(containerId));
        }
    }
}
