package selenium.logic.flow;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.WebDriver;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import selenium.logic.domain.Category;
import selenium.logic.domain.ShopItem;
import selenium.logic.helper.CostCounter;
import selenium.logic.helper.ShopElementSearcher;
import selenium.logic.page.ShopPage;
import selenium.logic.validator.CartVerifier;
import selenium.logic.validator.NotificationValidator;
import selenium.test.shop.util.CategoryNameHelper;

@RequiredArgsConstructor
@Builder
public class BuyItem {
    public static final String SHOPPING_SUCCESSFUL_MESSAGE = "Tárgyak megvásárolva.";

    private final ShopElementSearcher shopElementSearcher;
    private final CostCounter costCounter;
    private final CartVerifier cartVerifier;
    private final NotificationValidator notificationValidator;

    public BuyItem(WebDriver driver, String locale) {
        ObjectMapper objectMapper = new ObjectMapper();
        CategoryNameHelper categoryNameHelper = new CategoryNameHelper(objectMapper, locale);
        ShopPage shopPage = new ShopPage(driver);
        shopElementSearcher = new ShopElementSearcher(driver, categoryNameHelper, shopPage);
        this.costCounter = new CostCounter(driver, shopElementSearcher);
        this.cartVerifier = new CartVerifier(driver, shopElementSearcher, costCounter);
        this.notificationValidator = new NotificationValidator(driver);
    }

    public void buyItem(String itemId, int amount) {
        ShopItem equipmentToBuy = shopElementSearcher.searchShopItemById(Category.ENERGY, itemId);
        int cost = equipmentToBuy.getCost();
        int currentMoney = costCounter.getCurrentMoney();

        equipmentToBuy.addToCart();
        cartVerifier.verifyCosts(Category.ENERGY, itemId, 1);

        shopElementSearcher.getBuyButton().click();

        int newBalance = costCounter.getCurrentMoney();

        assertEquals(currentMoney - cost, newBalance);
        notificationValidator.verifyOnlyOneNotification(SHOPPING_SUCCESSFUL_MESSAGE);
        cartVerifier.verifyEmptyCart();
    }
}
