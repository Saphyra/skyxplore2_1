package selenium.aaold.cases.equipment;

import org.openqa.selenium.WebDriver;
import selenium.aanew.flow.Navigate;
import selenium.aanew.flow.Registration;
import selenium.aanew.validator.NotificationValidator;
import selenium.aaold.cases.equipment.testcase.EquipShipTest;
import selenium.aaold.cases.equipment.testcase.EquipTest;
import selenium.aaold.cases.equipment.testcase.UnEquipTest;
import selenium.aaold.cases.equipment.testcase.helper.ElementSearcher;
import selenium.aaold.cases.equipment.testcase.helper.EquipmentVerifier;
import selenium.aanew.domain.SeleniumCharacter;
import selenium.aanew.flow.BuyItem;
import selenium.aanew.flow.CreateCharacter;
import selenium.aanew.flow.SelectCharacter;

public class EquipmentTest {
    public static final String TEST_SHIP_ID = "sta-02";

    private final WebDriver driver;
    private final Navigate navigate;
    private final BuyItem buyItem;
    private final NotificationValidator notificationValidator;
    private final ElementSearcher elementSearcher;
    private final EquipmentVerifier equipmentVerifier;
    private EquipmentTest(WebDriver driver) {
        this.driver = driver;
        this.navigate = new Navigate(driver);
        this.buyItem = new BuyItem(driver);
        this.notificationValidator = new NotificationValidator(driver);
        this.elementSearcher = new ElementSearcher(driver);
        this.equipmentVerifier = new EquipmentVerifier(elementSearcher);
    }

    public static void run(WebDriver driver){
        EquipmentTest testCase = new EquipmentTest(driver);
        testCase.init();
        String unequippedItemId = testCase.testUnEquip();
        testCase.testEquip(unequippedItemId);
        testCase.testEquipShip();
        testCase.testEquipConnector();
        testCase.testUnequipConnector();
    }

    private String testUnEquip() {
        UnEquipTest unEquipTest = new UnEquipTest(elementSearcher, notificationValidator, equipmentVerifier);
        return unEquipTest.testUnEquip();
    }

    private void testEquip(String unequippedItemId) {
        EquipTest equipTest = new EquipTest(elementSearcher, driver, notificationValidator);
        equipTest.testEquip(unequippedItemId);
    }

    private void testEquipShip() {
        EquipShipTest equipShipTest = new EquipShipTest(elementSearcher, notificationValidator);
        equipShipTest.testEquipShip();
    }

    private void testEquipConnector() {
        //TODO fix
    }

    private void testUnequipConnector() {
        //TODO fix
    }

    private void init() {
        new Registration(driver).registerUser();
        SeleniumCharacter testCharacter = SeleniumCharacter.create();
        new CreateCharacter(driver).createCharacter(testCharacter);
        new SelectCharacter(driver).selectCharacter(testCharacter);
        navigate.toShop();
        buyItem.buyItem(TEST_SHIP_ID, 1);
        navigate.toEquipmentPage();
    }
}
