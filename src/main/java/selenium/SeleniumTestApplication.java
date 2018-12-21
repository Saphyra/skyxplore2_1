package selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.SpringApplication;
import selenium.cases.account.AccountTest;
import selenium.cases.characterselect.CharacterSelectTest;
import selenium.cases.equipment.EquipmentTest;
import selenium.cases.factory.FactoryTest;
import selenium.cases.registration.RegistrationTest;
import selenium.cases.shop.ShopTest;
import selenium.util.Util;
import skyxplore.Application;

import static selenium.util.LinkUtil.HOST;
import static selenium.util.LinkUtil.HOST_LOCAL;
import static skyxplore.Application.APP_CTX;

public class SeleniumTestApplication {
    private static final String ARG_PROFILE = "--spring.profiles.active=test";
    private static final String SKYXPLORE_LOG_LEVEL = "--logging.level.skyxplore=WARN";
    private static final String LOG_LEVEL_DEBUG = "--logging.level.skyxplore.filter=DEBUG";
    private static final String[] ARGS = new String[]{
        //LOG_LEVEL_DEBUG,
        ARG_PROFILE,
        SKYXPLORE_LOG_LEVEL
    };

    private WebDriver driver;

    @Before
    public void init() {
        if (HOST.equals(HOST_LOCAL)) {
            Application.main(ARGS);
        }

        System.setProperty("webdriver.chrome.driver", getClass().getClassLoader().getResource("chromedriver.exe").getPath());
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(HOST);
    }

    @Test
    public void testRegistration() {
        RegistrationTest.run(driver);
    }

    @Test
    public void testAccount() {
        AccountTest.run(driver);
    }

    @Test
    public void testCharacterSelect() {
        CharacterSelectTest.run(driver);
    }

    @Test
    public void testFactory() {
        FactoryTest.run(driver);
    }

    @Test
    public void testShop() {
        ShopTest.run(driver);
    }

    @Test
    public void testEquipment(){
        EquipmentTest.run(driver);
    }

    @After
    public void tearDown() throws InterruptedException {
        Util.sleep(0);
        if (APP_CTX != null) {
            SpringApplication.exit(APP_CTX);
        }
        driver.close();
        driver.quit();
    }
}
