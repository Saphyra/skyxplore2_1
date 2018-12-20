package selenium.cases.shop.testcase;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import selenium.cases.shop.testcase.domain.ShopItem;
import selenium.cases.shop.testcase.helper.CartVerifier;
import selenium.cases.shop.testcase.helper.CostCounter;
import selenium.cases.shop.testcase.helper.ElementSearcher;
import selenium.flow.Navigate;
import selenium.validator.NotificationValidator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BuyTest {
    public static final String SHOPPING_SUCCESSFUL_MESSAGE = "Tárgyak megvásárolva.";
    private static final String BOUGHT_ITEM_ID = "bat-01";

    private final WebDriver driver;
    private final ElementSearcher elementSearcher;
    private final CostCounter costCounter;
    private final NotificationValidator notificationValidator;
    private final CartVerifier cartVerifier;
    private final Navigate navigate;

    public BuyTest(WebDriver driver, Navigate navigate) {
        this.driver = driver;
        this.elementSearcher = new ElementSearcher(driver);
        this.costCounter = new CostCounter(driver, elementSearcher);
        this.notificationValidator = new NotificationValidator(driver);
        this.cartVerifier = new CartVerifier(driver, elementSearcher, costCounter);
        this.navigate = navigate;
    }

    public void init() {
        driver.navigate().refresh();
    }

    public void testBuyEquipment() {
        ShopItem equipmentToBuy = elementSearcher.searchShopItemById(BOUGHT_ITEM_ID);
        int cost = equipmentToBuy.getCost();
        int currentMoney = costCounter.getCurrentMoney();

        equipmentToBuy.addToCart();
        cartVerifier.verifyCosts(BOUGHT_ITEM_ID, 1);

        elementSearcher.getBuyButton().click();

        int newBalance = costCounter.getCurrentMoney();

        assertEquals(currentMoney - cost, newBalance);
        notificationValidator.verifyOnlyOneNotification(SHOPPING_SUCCESSFUL_MESSAGE);
        cartVerifier.verifyEmptyCart();

        verifyItemInStorage();
    }

    private void verifyItemInStorage() {
        navigate.toEquipmentPage();

        assertTrue(BOUGHT_ITEM_ID.equalsIgnoreCase(driver.findElement(By.cssSelector("#equipmentlist div.equipmentslotcontainer:first-child div:nth-child(2) div:first-child")).getText().split(" ")[0]));
    }
}
