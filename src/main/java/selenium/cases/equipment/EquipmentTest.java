package selenium.cases.equipment;

import org.openqa.selenium.WebDriver;

import selenium.domain.SeleniumCharacter;
import selenium.flow.BuyItem;
import selenium.flow.CreateCharacter;
import selenium.flow.Navigate;
import selenium.flow.Registration;
import selenium.flow.SelectCharacter;

public class EquipmentTest {
    private static final String SHIP_ID = "sta-01";

    private final WebDriver driver;
    private final Navigate navigate;
    private final BuyItem buyItem;

    private EquipmentTest(WebDriver driver) {
        this.driver = driver;
        this.navigate = new Navigate(driver);
        this.buyItem = new BuyItem(driver);
    }

    public static void run(WebDriver driver){
        EquipmentTest testCase = new EquipmentTest(driver);
        testCase.init();
    }

    private void init() {
        new Registration(driver).registerUser();
        SeleniumCharacter testCharacter = SeleniumCharacter.create();
        new CreateCharacter(driver).createCharacter(testCharacter);
        new SelectCharacter(driver).selectCharacter(testCharacter);
        navigate.toShop();
        buyItem.buyItem(SHIP_ID, 1);
        navigate.toEquipmentPage();
    }
}
