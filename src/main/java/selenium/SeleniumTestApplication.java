package selenium;

import static java.util.Objects.isNull;
import static selenium.logic.util.LinkUtil.HOST;
import static selenium.logic.util.LinkUtil.HOST_LOCAL;
import static selenium.logic.util.Util.executeScript;
import static selenium.logic.util.Util.sleep;
import static skyxplore.Application.APP_CTX;

import java.io.IOException;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.SpringApplication;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import selenium.logic.domain.MessageCodes;
import skyxplore.Application;

@Slf4j
public abstract class SeleniumTestApplication {
    private static final String ARG_PROFILE = "--spring.profiles.active=test";
    private static final String SKYXPLORE_LOG_LEVEL = "--logging.level=WARN";
    private static final String SELENIUM_LOG_LEVEL = "--logging.level.selenium=INFO";
    private static final String[] ARGS = new String[]{
        ARG_PROFILE,
        SKYXPLORE_LOG_LEVEL,
        SELENIUM_LOG_LEVEL
    };
    private static final String CHROME_DRIVER_PROPERTY_NAME = "webdriver.chrome.driver";
    private static final String CHROME_DRIVER_EXE_LOCATION = "chromedriver.exe";
    private static final boolean HEADLESS_MODE = false;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    protected WebDriver driver;
    protected String locale;
    protected MessageCodes messageCodes;

    @Before
    public void startServices() throws IOException {
        if (HOST.equals(HOST_LOCAL)) {
            Application.main(ARGS);
        }

        System.setProperty(CHROME_DRIVER_PROPERTY_NAME, getClass().getClassLoader().getResource(CHROME_DRIVER_EXE_LOCATION).getPath());
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(HEADLESS_MODE);

        driver = new ChromeDriver(options);

        getLocale();
        readMessageCodes();

        driver.manage().window().maximize();
        driver.get(HOST);

        init();
    }

    private void getLocale() {
        locale = executeScript(driver, "return navigator.language.toLowerCase().split(\"-\")[0]");
        log.info("Locale: {}", locale);
    }

    private void readMessageCodes() throws IOException {
        URL messageCodes = getClass().getClassLoader().getResource("public/i18n/" + locale + "/message_codes.json");

        if (isNull(messageCodes)) {
            log.info("Localization not found for locale {}. Using default locale...", locale);
            messageCodes = getClass().getClassLoader().getResource("public/i18n/hu/message_codes.json");
        }

        this.messageCodes = OBJECT_MAPPER.readValue(messageCodes, MessageCodes.class);
    }

    protected abstract void init();

    @After
    public void tearDown() {
        sleep(0);
        if (APP_CTX != null) {
            SpringApplication.exit(APP_CTX);
        }
        driver.close();
        driver.quit();
    }
}
