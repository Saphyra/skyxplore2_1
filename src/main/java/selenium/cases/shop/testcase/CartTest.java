package selenium.cases.shop.testcase;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.WebDriver;

import lombok.extern.slf4j.Slf4j;
import selenium.cases.shop.testcase.domain.CartItem;
import selenium.cases.shop.testcase.domain.ShopItem;
import selenium.cases.shop.testcase.helper.ItemSearcher;

@Slf4j
public class CartTest {
    private static final String CHEAP_ITEM_ID = "bat-01";

    private final WebDriver driver;
    private final ItemSearcher itemSearcher;

    public CartTest(WebDriver driver){
        this.driver = driver;
        this.itemSearcher = new ItemSearcher(driver);
    }

    public void testAddToCart(){
        //Add one element
        //Add more elements
        addToCart(CHEAP_ITEM_ID);
        verifyAmountInCart(CHEAP_ITEM_ID, 1);
        verifyItemCost(CHEAP_ITEM_ID, 1);

        addToCart(CHEAP_ITEM_ID);
        verifyAmountInCart(CHEAP_ITEM_ID, 2);
        verifyItemCost(CHEAP_ITEM_ID, 2);

        //Verify: item is in cart with the correct amount
        //Verify: total cost displayed properly

    }

    private void addToCart(String itemId){
        ShopItem shopItem = itemSearcher.searchShopItemById(itemId);
        shopItem.addToCart();
    }

    private void verifyAmountInCart(String itemId, int amount) {
        CartItem cartItem = itemSearcher.searchCartItemById(itemId);
        assertEquals(amount, cartItem.getAmount());
    }

    private void verifyItemCost(String itemId, int amount) {
        int costPerItem = itemSearcher.searchShopItemById(itemId).getCost();
        int total = costPerItem * amount;

        CartItem cartItem = itemSearcher.searchCartItemById(itemId);
        int costPerItemType = cartItem.getCostPerItem();
        assertEquals(costPerItem, costPerItemType);

        int totalCost = cartItem.getTotalCost();
        assertEquals(total, totalCost);
    }

    public void testRemoveFromCart() {
        //Remove one
        //Remove more

        //Verify: item is in cart with the correct amount
        //Verify: total cost displayed properly
        //Verify: empty cart message appears when empty
    }

    public void cannotAddMoreWhenTooExpensive() {
        //Add one

        //Verify: item is in cart
        //Verify: cannot add more
    }
}
