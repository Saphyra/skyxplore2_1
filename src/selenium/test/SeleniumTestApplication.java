package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import skyxplore.Application;

public class SeleniumTestApplication {
    private WebDriver driver;

    @Before
    public void init() {
        Application.main(new String[0]);
        System.setProperty("webdriver.chrome.driver", getClass().getClassLoader().getResource("resources/chromedriver.exe").getPath());
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("localhost:8080");
    }

    @Test
    public void test() {

    }

    @After
    public void tearDown() {
        driver.close();
    }
}
