package selenium.flow;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.WebDriver;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import selenium.domain.ShopItem;
import selenium.validator.CartVerifier;
import selenium.helper.CostCounter;
import selenium.helper.ShopElementSearcher;
import selenium.validator.NotificationValidator;

@RequiredArgsConstructor
@Builder
public class BuyItem {
    public static final String SHOPPING_SUCCESSFUL_MESSAGE = "Tárgyak megvásárolva.";

    private final ShopElementSearcher shopElementSearcher;
    private final CostCounter costCounter;
    private final CartVerifier cartVerifier;
    private final NotificationValidator notificationValidator;

    public BuyItem(WebDriver driver){
        this.shopElementSearcher = new ShopElementSearcher(driver);
        this.costCounter = new CostCounter(driver, shopElementSearcher);
        this.cartVerifier = new CartVerifier(driver, shopElementSearcher, costCounter);
        this.notificationValidator = new NotificationValidator(driver);
    }

    public void buyItem(String itemId, int amount){
        ShopItem equipmentToBuy = shopElementSearcher.searchShopItemById(itemId);
        int cost = equipmentToBuy.getCost();
        int currentMoney = costCounter.getCurrentMoney();

        equipmentToBuy.addToCart();
        cartVerifier.verifyCosts(itemId, 1);

        shopElementSearcher.getBuyButton().click();

        int newBalance = costCounter.getCurrentMoney();

        assertEquals(currentMoney - cost, newBalance);
        notificationValidator.verifyOnlyOneNotification(SHOPPING_SUCCESSFUL_MESSAGE);
        cartVerifier.verifyEmptyCart();
    }
}
