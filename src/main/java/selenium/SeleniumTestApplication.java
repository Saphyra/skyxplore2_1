package selenium;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.SpringApplication;
import selenium.logic.util.Util;
import skyxplore.Application;

import static selenium.logic.util.LinkUtil.HOST;
import static selenium.logic.util.LinkUtil.HOST_LOCAL;
import static skyxplore.Application.APP_CTX;

public abstract class SeleniumTestApplication {
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
    private static final String CHROME_DRIVER_PROPERTY_NAME = "webdriver.chrome.driver";
    private static final String CHROME_DRIVER_EXE_LOCATION = "chromedriver.exe";
    private static final boolean HEADLESS_MODE = false;

    protected WebDriver driver;

    @Before
    public void startServices() {
        if (HOST.equals(HOST_LOCAL)) {
            Application.main(ARGS);
        }

        System.setProperty(CHROME_DRIVER_PROPERTY_NAME, getClass().getClassLoader().getResource(CHROME_DRIVER_EXE_LOCATION).getPath());
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(HEADLESS_MODE);

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get(HOST);

        init();
    }

    protected abstract void init();

    @After
    public void tearDown() {
        Util.sleep(0);
        if (APP_CTX != null) {
            SpringApplication.exit(APP_CTX);
        }
        driver.close();
        driver.quit();
    }
}
