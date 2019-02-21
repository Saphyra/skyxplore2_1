package selenium.logic.util;

import static org.junit.Assert.assertTrue;
import static selenium.logic.util.LocatorUtil.getNotificationElementsLocator;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Util {
    public static final String ATTRIBUTE_VALUE = "value";

    public static <T> Optional<T> getWithWait(Supplier<Optional<T>> supplier) {
        return getWithWait(supplier, "Querying item...");
    }

    public static <T> Optional<T> getWithWait(Supplier<Optional<T>> supplier, String logMessage) {
        log.info(logMessage);
        int counter = 0;
        Optional<T> result;
        do {
            log.info("Query attempts: {}", counter);
            result = supplier.get();
            sleep(100);
            counter++;
        } while (!result.isPresent() && counter < 100);
        return result;
    }

    public static String executeScript(WebDriver driver, String script) {
        if (driver instanceof JavascriptExecutor) {
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            return (String) executor.executeScript(script);
        } else {
            throw new IllegalArgumentException("driver is not JavascriptExecutor");
        }
    }

    public static void cleanNotifications(WebDriver driver) {
        driver.findElements(getNotificationElementsLocator()).forEach(WebElement::click);
    }

    public static void sleep(long timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String randomUID() {
        return UUID.randomUUID().toString();
    }

    public static String crop(String source, Integer length) {
        return source.substring(0, length);
    }

    public static <T> Optional<T> validateIfPresent(Optional<T> optional) {
        assertTrue(optional.isPresent());
        return optional;
    }

    public static boolean hasClass(WebElement element, String clazz) {
        String[] classes = element.getAttribute("class").split(" ");
        return Arrays.asList(classes).contains(clazz);
    }
}
