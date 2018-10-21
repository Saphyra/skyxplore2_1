package selenium.cases.factory.testcase;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import selenium.page.FactoryPage;
import selenium.validator.NotificationValidator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static selenium.util.Util.sleep;
import static selenium.util.ValidationUtil.validateIfPresent;

@Builder
@Slf4j
public class BuildAndFinishTest {
    private static final int AMOUNT_TO_PRODUCE = 3;

    private static final String ELEMENT_MATERIAL_NAME = "span";
    private static final String ELEMENT_MATERIAL_AMOUNT = "div";
    private static final String NOTIFICATION_PRODUCT_BUILDING_STARTED = "Megrendelés elküldve.";
    private static final String LABEL_QUEUE_EMPTY = "A gyártósor üres.";

    private final WebDriver driver;
    private final FactoryPage factoryPage;
    private final NotificationValidator notificationValidator;

    private final Map<String, Integer> startMaterialAmounts = new HashMap<>();
    private final Map<String, Integer> usedMaterials = new HashMap<>();

    public void testBuildAndFinish() {
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
        String elementTitle = element.findElement(By.cssSelector("div:first-child")).getText();
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

        assertNotNull(element.findElement(By.cssSelector(".queueprocess .processbartext")));
    }

    private void verifyProductFinished(String builtProductName) {
        List<WebElement> queue = factoryPage.getQueue();
        assertEquals(1, queue.size());
        assertEquals(LABEL_QUEUE_EMPTY, queue.get(0).getText());
        

        validateIfPresent(getActualMaterialAmounts().entrySet().stream()
            .filter(entry -> entry.getKey().equals(builtProductName))
            .findFirst())
            .ifPresent(entry ->{
                int startValue = startMaterialAmounts.get(entry.getKey());
                int actualValue = entry.getValue();
                int expected = startValue + AMOUNT_TO_PRODUCE;
                assertEquals(expected, actualValue);
            });
    }
}
