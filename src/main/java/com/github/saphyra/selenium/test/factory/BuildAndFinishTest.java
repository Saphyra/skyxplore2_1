package com.github.saphyra.selenium.test.factory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static com.github.saphyra.selenium.logic.util.Util.validateIfPresent;
import static com.github.saphyra.selenium.logic.util.WaitUtil.sleep;
import static com.github.saphyra.selenium.logic.util.WaitUtil.waitUntil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;
import org.openqa.selenium.By;
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
import com.github.saphyra.selenium.logic.validator.NotificationValidator;
import com.github.saphyra.selenium.test.factory.util.FactoryTestHelper;

public class BuildAndFinishTest extends SeleniumTestApplication {
    private static final int AMOUNT_TO_PRODUCE = 3;

    private static final String ELEMENT_MATERIAL_NAME = "span:first-child";
    private static final String ELEMENT_MATERIAL_AMOUNT = ".material-element-amount";
    private static final String MESSAGE_CODE_PRODUCT_ADDED_TO_QUEUE = "PRODUCT_ADDED_TO_QUEUE";
    private static final String SELECTOR_QUEUE_ITEM_NAME = "div:first-child";
    private static final String SELECTOR_PROCESS_BAR_TEXT = ".queue-process .process-bar-text";

    private FactoryTestHelper factoryTestHelper;
    private FactoryPage factoryPage;
    private NotificationValidator notificationValidator;

    private final Map<String, Integer> startMaterialAmounts = new HashMap<>();
    private final Map<String, Integer> usedMaterials = new HashMap<>();

    @Override
    protected void init() {
        factoryTestHelper = new FactoryTestHelper(
            new Registration(driver, messageCodes),
            new CreateCharacter(driver, messageCodes),
            new SelectCharacter(driver),
            new Navigate(driver)
        );
        factoryPage = new FactoryPage(driver, new CategoryNameHelper(OBJECT_MAPPER, locale));
        notificationValidator = new NotificationValidator(driver);
    }

    @Test
    public void testBuildAndFinish() {
        factoryTestHelper.registerAndGoToFactoryPage();

        startMaterialAmounts.putAll(getActualMaterialAmounts());
        factoryPage.loadItemsOfCategory(Category.MATERIAL);
        SeleniumProduct product = new SeleniumProduct(factoryPage.getProductContainer());
        String builtProductName = product.getName();

        startBuilding(product, builtProductName);

        sleep(15000);

        verifyProductStarted(builtProductName);

        sleep(40000);
        verifyProductFinished(builtProductName);
    }

    private void startBuilding(SeleniumProduct product, String builtProductName) {
        product.fillAmountField(AMOUNT_TO_PRODUCE);
        usedMaterials.putAll(product.getUserMaterialAmounts());

        WebElement buildButton = product.getBuildButton();
        assertTrue(buildButton.isEnabled());
        buildButton.click();

        verifyBuildStarted(builtProductName);
    }

    private Map<String, Integer> getActualMaterialAmounts() {
        sleep(1000);
        return factoryPage.getCurrentMaterialAmounts().stream()
            .collect(Collectors.toMap(this::parseMaterialName, this::parseMaterialAmount));
    }

    private String parseMaterialName(WebElement element) {
        return element.findElement(By.cssSelector(ELEMENT_MATERIAL_NAME)).getText();
    }

    private Integer parseMaterialAmount(WebElement element) {
        return Integer.valueOf(element.findElement(By.cssSelector(ELEMENT_MATERIAL_AMOUNT)).getText());
    }

    private void verifyBuildStarted(String builtProductName) {
        verifyMaterialsUsed();
        notificationValidator.verifyNotificationVisibility(messageCodes.get(MESSAGE_CODE_PRODUCT_ADDED_TO_QUEUE));
        verifyMaterialInQueue(builtProductName);
    }

    private void verifyMaterialsUsed() {
        Map<String, Integer> actualMaterials = getActualMaterialAmounts();

        usedMaterials.forEach((key, value) ->
            {
                int actual = actualMaterials.get(key);
                int original = startMaterialAmounts.get(key);
                int used = usedMaterials.get(key);
                assertEquals(actual, original - used);
            }
        );
    }

    private void verifyMaterialInQueue(String builtProductName) {
        WebElement element = getElementOfQueue();
        verifyMaterialInQueue(element, builtProductName);
    }

    private void verifyMaterialInQueue(WebElement element, String builtProductName) {
        String elementTitle = element.findElement(By.cssSelector(SELECTOR_QUEUE_ITEM_NAME)).getText();
        assertEquals(builtProductName, elementTitle.split(" x ")[0]);
        assertEquals((Integer) AMOUNT_TO_PRODUCE, Integer.valueOf(elementTitle.split(" x ")[1]));
    }

    private WebElement getElementOfQueue() {
        waitUntil(() -> !factoryPage.getQueue().isEmpty(), "Waiting for queue element to appear");
        List<WebElement> queue = factoryPage.getQueue();
        assertEquals(1, queue.size());


        return queue.get(0);
    }

    private void verifyProductStarted(String builtProductName) {
        WebElement element = getElementOfQueue();
        verifyMaterialInQueue(element, builtProductName);

        assertNotNull(element.findElement(By.cssSelector(SELECTOR_PROCESS_BAR_TEXT)));
    }

    private void verifyProductFinished(String builtProductName) {
        List<WebElement> queue = factoryPage.getQueue();
        assertTrue(queue.isEmpty());

        validateIfPresent(getActualMaterialAmounts().entrySet().stream()
            .filter(entry -> entry.getKey().equals(builtProductName))
            .findFirst())
            .ifPresent(entry -> {
                int startValue = startMaterialAmounts.get(entry.getKey());
                int actualValue = entry.getValue();
                int expected = startValue + AMOUNT_TO_PRODUCE;
                assertEquals(expected, actualValue);
            });
    }
}
