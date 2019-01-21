package selenium.test.factory;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import selenium.SeleniumTestApplication;
import selenium.logic.domain.SeleniumProduct;
import selenium.logic.flow.CreateCharacter;
import selenium.logic.flow.Navigate;
import selenium.logic.flow.Registration;
import selenium.logic.flow.SelectCharacter;
import selenium.logic.page.FactoryPage;
import selenium.logic.validator.NotificationValidator;
import selenium.test.factory.util.FactoryTestHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static selenium.logic.util.Util.sleep;
import static selenium.logic.util.Util.validateIfPresent;

public class BuildAndFinishTest extends SeleniumTestApplication {
    private static final int AMOUNT_TO_PRODUCE = 3;

    private static final String ELEMENT_MATERIAL_NAME = "span";
    private static final String ELEMENT_MATERIAL_AMOUNT = "div";
    private static final String NOTIFICATION_PRODUCT_BUILDING_STARTED = "Megrendelés elküldve.";
    private static final String LABEL_QUEUE_EMPTY = "A gyártósor üres.";
    private static final String SELECTOR_QUEUE_ITEM_NAME = "div:first-child";
    private static final String SELECTOR_PROCESS_BAR_TEXT = ".queueprocess .processbartext";

    private FactoryTestHelper factoryTestHelper;
    private FactoryPage factoryPage;
    private NotificationValidator notificationValidator;

    private final Map<String, Integer> startMaterialAmounts = new HashMap<>();
    private final Map<String, Integer> usedMaterials = new HashMap<>();

    @Override
    protected void init() {
        factoryTestHelper = new FactoryTestHelper(
            new Registration(driver, messageCodes),
            new CreateCharacter(driver),
            new SelectCharacter(driver),
            new Navigate(driver)
        );
        factoryPage = new FactoryPage(driver);
        notificationValidator = new NotificationValidator(driver);
    }

    @Test
    public void testBuildAndFinish() {
        factoryTestHelper.registerAndGoToFactoryPage();

        startMaterialAmounts.putAll(getActualMaterialAmounts());

        SeleniumProduct product = new SeleniumProduct(factoryPage.getProductContainer());
        String builtProductName = product.getName();

        startBuilding(product, builtProductName);

        sleep(10000);
        driver.navigate().refresh();

        verifyProductStarted(builtProductName);

        sleep(40000);
        driver.navigate().refresh();
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
        return factoryPage.getCurrentMaterialAmounts().stream()
            .collect(Collectors.toMap(this::parseMaterialName, this::parseMaterialAmount));
    }

    private String parseMaterialName(WebElement element) {
        String name = element.findElement(By.cssSelector(ELEMENT_MATERIAL_NAME)).getText();
        return name.substring(0, name.length() - 1);
    }

    private Integer parseMaterialAmount(WebElement element) {
        return Integer.valueOf(element.findElement(By.cssSelector(ELEMENT_MATERIAL_AMOUNT)).getText());
    }

    private void verifyBuildStarted(String builtProductName) {
        verifyMaterialsUsed();
        notificationValidator.verifyNotificationVisibility(NOTIFICATION_PRODUCT_BUILDING_STARTED);
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
        assertEquals(1, queue.size());
        assertEquals(LABEL_QUEUE_EMPTY, queue.get(0).getText());


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
