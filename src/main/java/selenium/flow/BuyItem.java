package selenium.flow;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.WebDriver;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import selenium.cases.shop.testcase.domain.ShopItem;
import selenium.cases.shop.testcase.helper.CartVerifier;
import selenium.cases.shop.testcase.helper.CostCounter;
import selenium.cases.shop.testcase.helper.ElementSearcher;
import selenium.validator.NotificationValidator;

@RequiredArgsConstructor
@Builder
public class BuyItem {
    public static final String SHOPPING_SUCCESSFUL_MESSAGE = "Tárgyak megvásárolva.";

    private final ElementSearcher elementSearcher;
    private final CostCounter costCounter;
    private final CartVerifier cartVerifier;
    private final NotificationValidator notificationValidator;

    public BuyItem(WebDriver driver){
        this.elementSearcher = new ElementSearcher(driver);
        this.costCounter = new CostCounter(driver, elementSearcher);
        this.cartVerifier = new CartVerifier(driver, elementSearcher, costCounter);
        this.notificationValidator = new NotificationValidator(driver);
    }

    public void buyItem(String itemId, int amount){
        ShopItem equipmentToBuy = elementSearcher.searchShopItemById(itemId);
        int cost = equipmentToBuy.getCost();
        int currentMoney = costCounter.getCurrentMoney();

        equipmentToBuy.addToCart();
        cartVerifier.verifyCosts(itemId, 1);

        elementSearcher.getBuyButton().click();

        int newBalance = costCounter.getCurrentMoney();

        assertEquals(currentMoney - cost, newBalance);
        notificationValidator.verifyOnlyOneNotification(SHOPPING_SUCCESSFUL_MESSAGE);
        cartVerifier.verifyEmptyCart();
    }
}
