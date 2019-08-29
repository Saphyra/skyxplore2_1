package com.github.saphyra.selenium.test.factory;

import com.github.saphyra.selenium.SeleniumTestApplication;
import com.github.saphyra.selenium.logic.domain.Category;
import com.github.saphyra.selenium.logic.domain.SeleniumProduct;
import com.github.saphyra.selenium.logic.flow.CreateCharacter;
import com.github.saphyra.selenium.logic.flow.Navigate;
import com.github.saphyra.selenium.logic.flow.Registration;
import com.github.saphyra.selenium.logic.flow.SelectCharacter;
import com.github.saphyra.selenium.logic.page.FactoryPage;
import com.github.saphyra.selenium.logic.util.CategoryNameHelper;
import com.github.saphyra.selenium.test.factory.util.FactoryTestHelper;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertFalse;

public class NotEnoughMaterialTest extends SeleniumTestApplication {
    private FactoryTestHelper factoryTestHelper;
    private FactoryPage factoryPage;

    @Override
    protected void init() {
        factoryTestHelper = new FactoryTestHelper(
            new Registration(driver),
            new CreateCharacter(driver),
            new SelectCharacter(driver),
            new Navigate(driver)
        );
        factoryPage = new FactoryPage(driver, new CategoryNameHelper(driver));
    }

    @Test
    public void testNotEnoughMaterial() {
        factoryTestHelper.registerAndGoToFactoryPage();

        factoryPage.loadItemsOfCategory(Category.MATERIAL);
        WebElement productElement = factoryPage.getProductContainer();
        SeleniumProduct product = new SeleniumProduct(productElement);

        product.fillAmountField(product.getTooExpensiveAmount());

        WebElement buildButton = product.getBuildButton();

        assertFalse(buildButton.isEnabled());
    }


}
