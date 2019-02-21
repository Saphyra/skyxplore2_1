package selenium.logic.util;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertTrue;
import static selenium.logic.util.LocatorUtil.getNotificationElementsLocator;

@Slf4j
public class Util {
    public static final String ATTRIBUTE_VALUE = "value";

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
