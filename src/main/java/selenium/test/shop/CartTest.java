package selenium.test.shop;

import org.junit.Test;
import selenium.SeleniumTestApplication;
import selenium.logic.flow.CreateCharacter;
import selenium.logic.flow.Navigate;
import selenium.logic.flow.Registration;
import selenium.logic.flow.SelectCharacter;
import selenium.logic.helper.CostCounter;
import selenium.logic.helper.ShopElementSearcher;
import selenium.logic.validator.CartVerifier;
import selenium.test.shop.cart.AddToCartTest;
import selenium.test.shop.cart.RemoveFromCartTest;
import selenium.test.shop.cart.TooExpensiveTest;
import selenium.test.shop.util.ShopTestHelper;
import selenium.test.shop.util.ShopTestInitializer;

public class CartTest extends SeleniumTestApplication {
    private CartVerifier cartVerifier;
    private ShopTestHelper shopTestHelper;
    private ShopTestInitializer shopTestInitializer;
    private ShopElementSearcher shopElementSearcher;
    private CostCounter costCounter;

    @Override
    protected void init() {
        shopElementSearcher = new ShopElementSearcher(driver);
        costCounter = new CostCounter(driver, shopElementSearcher);
        cartVerifier = new CartVerifier(driver, shopElementSearcher, costCounter);
        shopTestHelper = new ShopTestHelper(shopElementSearcher);
        shopTestInitializer = new ShopTestInitializer(
            new Registration(driver),
            new CreateCharacter(driver),
            new SelectCharacter(driver),
            new Navigate(driver)
        );
    }

    @Test
    public void testAddToCart() {
        AddToCartTest.builder()
            .cartVerifier(cartVerifier)
            .shopTestHelper(shopTestHelper)
            .shopTestInitializer(shopTestInitializer)
            .build()
            .testAddToCart();
    }

    @Test
    public void testRemoveFromCart() {
        RemoveFromCartTest.builder()
            .cartVerifier(cartVerifier)
            .shopTestHelper(shopTestHelper)
            .shopTestInitializer(shopTestInitializer)
            .build()
            .testRemoveFromCart();
    }

    @Test
    public void testTooExpensiveItem() {
        TooExpensiveTest.builder()
            .shopTestInitializer(shopTestInitializer)
            .shopElementSearcher(shopElementSearcher)
            .shopTestHelper(shopTestHelper)
            .costCounter(costCounter)
            .build()
            .testTooExpensiveItem();
    }
}
