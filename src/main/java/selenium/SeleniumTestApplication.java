package selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.SpringApplication;
import selenium.cases.RegistrationAndAccountTest;
import selenium.cases.RegistrationEdgeCasesTest;
import skyxplore.Application;

import static selenium.util.LinkUtil.HOST;
import static skyxplore.Application.APP_CTX;

public class SeleniumTestApplication {

    private WebDriver driver;

    @Before
    public void init() {
        Application.main(new String[0]);
        System.setProperty("webdriver.chrome.driver", getClass().getClassLoader().getResource("chromedriver.exe").getPath());
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(HOST);
    }

    @Test
    public void testRegistrationAndAccount() {
        RegistrationAndAccountTest.run(driver);
    }

    @Test
    public void testRegistrationEdgeCases(){
        RegistrationEdgeCasesTest.run(driver);
    }

    @After
    public void tearDown() throws InterruptedException {
        Thread.sleep(5000);
        SpringApplication.exit(APP_CTX);
        driver.close();
        driver.quit();
    }
}
