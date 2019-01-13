package selenium.logic.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertTrue;
import static selenium.logic.util.LocatorUtil.getNotificationElementsLocator;

public class Util {
    public static final String ATTRIBUTE_VALUE = "value";

    public static void cleanNotifications(WebDriver driver){
        driver.findElements(getNotificationElementsLocator()).forEach(WebElement::click);
    }

    public static void sleep(long timeout){
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String randomUID(){
        return UUID.randomUUID().toString();
    }

    public static String crop(String source, Integer length){
        return source.substring(0, length);
    }

    public static <T> Optional<T> validateIfPresent(Optional<T> optional) {
        assertTrue(optional.isPresent());
        return optional;
    }

    public static boolean hasClass(WebElement element, String clazz){
        String[] classes = element.getAttribute("class").split(" ");
        return Arrays.asList(classes).contains(clazz);
    }
}
