package selenium.aaold;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.SpringApplication;
import selenium.aaold.cases.account.AccountTest;
import selenium.aaold.cases.characterselect.CharacterSelectTest;
import selenium.aaold.cases.community.CommunityTest;
import selenium.aaold.cases.equipment.EquipmentTest;
import selenium.aaold.cases.factory.FactoryTest;
import selenium.aaold.cases.registration.RegistrationTest;
import selenium.aaold.cases.shop.ShopTest;
import selenium.aanew.util.Util;
import skyxplore.Application;

import static selenium.aanew.util.LinkUtil.HOST;
import static selenium.aanew.util.LinkUtil.HOST_LOCAL;
import static skyxplore.Application.APP_CTX;

public class SeleniumTestApplication {
    private static final String ARG_PROFILE = "--spring.profiles.active=test";
    private static final String SKYXPLORE_LOG_LEVEL = "--logging.level=WARN";
    private static final String SELENIUM_LOG_LEVEL = "--logging.level.selenium=INFO";
    private static final String LOG_LEVEL_DEBUG = "--logging.level.skyxplore.filter=DEBUG";
    private static final String[] ARGS = new String[]{
        //LOG_LEVEL_DEBUG,
        ARG_PROFILE,
        SKYXPLORE_LOG_LEVEL,
        SELENIUM_LOG_LEVEL
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
    public void test01_Registration() {
        RegistrationTest.run(driver);
    }

    @Test
    public void test02_Account() {
        AccountTest.run(driver);
    }

    @Test
    public void test03_CharacterSelect() {
        CharacterSelectTest.run(driver);
    }

    @Test
    public void test04_Factory() {
        FactoryTest.run(driver);
    }

    @Test
    public void test05_Shop() {
        ShopTest.run(driver);
    }

    @Test
    public void test06_Equipment() {
        EquipmentTest.run(driver);
    }

    @Test
    public void test07_Community() {
        CommunityTest.run(driver);
    }

    @After
    public void tearDown() {
        Util.sleep(6000);
        if (APP_CTX != null) {
            SpringApplication.exit(APP_CTX);
        }
        driver.close();
        driver.quit();
    }
}
