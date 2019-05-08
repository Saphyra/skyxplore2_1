package com.github.saphyra.selenium.test.factory;

import org.junit.Test;
import org.openqa.selenium.WebElement;
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

import static org.junit.Assert.assertFalse;

public class NotEnoughMaterialTest extends SeleniumTestApplication {
    private FactoryTestHelper factoryTestHelper;
    private FactoryPage factoryPage;

    @Override
    protected void init() {
        factoryTestHelper = new FactoryTestHelper(
            new Registration(driver, messageCodes),
            new CreateCharacter(driver, messageCodes),
            new SelectCharacter(driver),
            new Navigate(driver)
        );
        factoryPage = new FactoryPage(driver, new CategoryNameHelper(OBJECT_MAPPER, locale));
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
