package test.java;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumTest {

    @Test
    public void test() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", getClass().getClassLoader().getResource("resources/chromedriver.exe").getPath());
        WebDriver driver = new ChromeDriver();
        driver.get("localhost:8080");
        Thread.sleep(10000);
        driver.close();
    }
}
