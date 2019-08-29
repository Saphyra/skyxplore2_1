package com.github.saphyra.selenium.test.equipment;

import com.github.saphyra.selenium.SeleniumTestApplication;
import com.github.saphyra.selenium.logic.domain.Category;
import com.github.saphyra.selenium.logic.domain.ContainerId;
import com.github.saphyra.selenium.logic.domain.SeleniumCharacter;
import com.github.saphyra.selenium.logic.domain.localization.Page;
import com.github.saphyra.selenium.logic.flow.BuyItem;
import com.github.saphyra.selenium.logic.flow.CreateCharacter;
import com.github.saphyra.selenium.logic.flow.Navigate;
import com.github.saphyra.selenium.logic.flow.Registration;
import com.github.saphyra.selenium.logic.flow.SelectCharacter;
import com.github.saphyra.selenium.logic.validator.NotificationValidator;
import com.github.saphyra.selenium.test.equipment.util.EquipmentElementSearcher;
import org.junit.Test;

import static com.github.saphyra.selenium.logic.util.LocalizationUtil.getAdditionalContent;
import static org.junit.Assert.assertEquals;

public class EquipShipTest extends SeleniumTestApplication {
    private static final String TEST_SHIP_ID = "sta-02";
    private static final String MESSAGE_CODE_SHIP_EQUIPPED = "ship-equipped";

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
        buyItem.buyItem(TEST_SHIP_ID, Category.STARTER_SHIP);
        navigate.toEquipmentPage();
    }

    private void verifySuccess() {
        notificationValidator.verifyNotificationVisibility(getAdditionalContent(driver, Page.EQUIPMENT, MESSAGE_CODE_SHIP_EQUIPPED));

        verifyEmptyShip();
    }

    private void verifyEmptyShip() {
        for (ContainerId containerId : ContainerId.values()) {
            assertEquals(0, equipmentElementSearcher.countNonEmptySlotsInContainer(containerId));
        }
    }
}
