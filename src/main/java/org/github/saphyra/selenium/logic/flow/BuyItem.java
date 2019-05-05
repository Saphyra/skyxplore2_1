package org.github.saphyra.selenium.logic.flow;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.github.saphyra.selenium.logic.domain.Category;
import org.github.saphyra.selenium.logic.domain.localization.MessageCodes;
import org.github.saphyra.selenium.logic.domain.ShopItem;
import org.github.saphyra.selenium.logic.helper.CostCounter;
import org.github.saphyra.selenium.logic.helper.ShopElementSearcher;
import org.github.saphyra.selenium.logic.page.ShopPage;
import org.github.saphyra.selenium.logic.util.CategoryNameHelper;
import org.github.saphyra.selenium.logic.validator.CartVerifier;
import org.github.saphyra.selenium.logic.validator.NotificationValidator;

import static org.junit.Assert.assertEquals;

@RequiredArgsConstructor
@Builder
public class BuyItem {
    public static final String MESSAGE_CODE_ITEMS_BOUGHT = "ITEMS_BOUGHT";

    private final ShopElementSearcher shopElementSearcher;
    private final CostCounter costCounter;
    private final CartVerifier cartVerifier;
    private final NotificationValidator notificationValidator;
    private final MessageCodes messageCodes;

    public BuyItem(WebDriver driver, String locale, MessageCodes messageCodes) {
        ObjectMapper objectMapper = new ObjectMapper();
        CategoryNameHelper categoryNameHelper = new CategoryNameHelper(objectMapper, locale);
        ShopPage shopPage = new ShopPage(driver);
        shopElementSearcher = new ShopElementSearcher(driver, categoryNameHelper, shopPage);
        this.costCounter = new CostCounter(driver, shopElementSearcher);
        this.cartVerifier = new CartVerifier(driver, shopElementSearcher, costCounter, shopPage);
        this.notificationValidator = new NotificationValidator(driver);
        this.messageCodes = messageCodes;
    }

    public void buyItem(String itemId, Category category) {
        ShopItem equipmentToBuy = shopElementSearcher.searchShopItemById(category, itemId);
        int cost = equipmentToBuy.getCost();
        int currentMoney = costCounter.getCurrentMoney();

        equipmentToBuy.addToCart();
        cartVerifier.verifyCosts(category, itemId, 1);

        shopElementSearcher.getBuyButton().click();

        notificationValidator.verifyOnlyOneNotification(messageCodes.get(MESSAGE_CODE_ITEMS_BOUGHT));

        int newBalance = costCounter.getCurrentMoney();
        assertEquals(currentMoney - cost, newBalance);
        cartVerifier.verifyEmptyCart();
    }
}
