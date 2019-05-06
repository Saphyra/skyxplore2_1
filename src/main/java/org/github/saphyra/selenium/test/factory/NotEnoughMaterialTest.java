package org.github.saphyra.selenium.test.factory;

import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.github.saphyra.selenium.SeleniumTestApplication;
import org.github.saphyra.selenium.logic.domain.Category;
import org.github.saphyra.selenium.logic.domain.SeleniumProduct;
import org.github.saphyra.selenium.logic.flow.CreateCharacter;
import org.github.saphyra.selenium.logic.flow.Navigate;
import org.github.saphyra.selenium.logic.flow.Registration;
import org.github.saphyra.selenium.logic.flow.SelectCharacter;
import org.github.saphyra.selenium.logic.page.FactoryPage;
import org.github.saphyra.selenium.logic.util.CategoryNameHelper;
import org.github.saphyra.selenium.test.factory.util.FactoryTestHelper;

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
