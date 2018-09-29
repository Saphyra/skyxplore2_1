package selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumTest {
    public void test() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", getClass().getClassLoader().getResource("chromedriver.exe").getPath());
        WebDriver driver = new ChromeDriver();
        driver.get("localhost:8080");
        Thread.sleep(10000);
        driver.close();
    }
}
