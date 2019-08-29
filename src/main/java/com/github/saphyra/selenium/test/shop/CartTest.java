package com.github.saphyra.selenium.test.shop;

import com.github.saphyra.selenium.SeleniumTestApplication;
import com.github.saphyra.selenium.logic.flow.CreateCharacter;
import com.github.saphyra.selenium.logic.flow.Navigate;
import com.github.saphyra.selenium.logic.flow.Registration;
import com.github.saphyra.selenium.logic.flow.SelectCharacter;
import com.github.saphyra.selenium.logic.helper.CostCounter;
import com.github.saphyra.selenium.logic.helper.ShopElementSearcher;
import com.github.saphyra.selenium.logic.page.ShopPage;
import com.github.saphyra.selenium.logic.util.CategoryNameHelper;
import com.github.saphyra.selenium.logic.validator.CartVerifier;
import com.github.saphyra.selenium.test.shop.cart.AddToCartTest;
import com.github.saphyra.selenium.test.shop.cart.RemoveFromCartTest;
import com.github.saphyra.selenium.test.shop.cart.TooExpensiveTest;
import com.github.saphyra.selenium.test.shop.util.ShopTestHelper;
import com.github.saphyra.selenium.test.shop.util.ShopTestInitializer;
import org.junit.Test;

public class CartTest extends SeleniumTestApplication {
    private CartVerifier cartVerifier;
    private ShopTestHelper shopTestHelper;
    private ShopTestInitializer shopTestInitializer;
    private ShopElementSearcher shopElementSearcher;
    private CostCounter costCounter;

    @Override
    protected void init() {
        CategoryNameHelper categoryNameHelper = new CategoryNameHelper(driver);
        ShopPage shopPage = new ShopPage(driver);
        shopElementSearcher = new ShopElementSearcher(driver, categoryNameHelper, shopPage);
        costCounter = new CostCounter(driver, shopElementSearcher);
        cartVerifier = new CartVerifier(driver, shopElementSearcher, costCounter, shopPage);
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
