package selenium.cases.equipment;

import org.openqa.selenium.WebDriver;
import selenium.cases.equipment.testcase.EquipShipTest;
import selenium.cases.equipment.testcase.EquipTest;
import selenium.cases.equipment.testcase.UnEquipTest;
import selenium.cases.equipment.testcase.helper.ElementSearcher;
import selenium.cases.equipment.testcase.helper.EquipmentVerifier;
import selenium.domain.SeleniumCharacter;
import selenium.flow.BuyItem;
import selenium.flow.CreateCharacter;
import selenium.flow.Navigate;
import selenium.flow.Registration;
import selenium.flow.SelectCharacter;
import selenium.validator.NotificationValidator;

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
