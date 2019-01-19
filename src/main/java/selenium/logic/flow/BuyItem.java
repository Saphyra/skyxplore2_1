package selenium.logic.flow;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import selenium.logic.domain.ShopItem;
import selenium.logic.validator.NotificationValidator;
import selenium.logic.helper.CostCounter;
import selenium.logic.helper.ShopElementSearcher;
import selenium.logic.validator.CartVerifier;

import static org.junit.Assert.assertEquals;

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
