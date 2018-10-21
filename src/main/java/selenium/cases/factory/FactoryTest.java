package selenium.cases.factory;

import org.openqa.selenium.WebDriver;
import selenium.cases.factory.testcase.NotEnoughMaterialTest;
import selenium.domain.SeleniumCharacter;
import selenium.flow.CreateCharacter;
import selenium.flow.Navigate;
import selenium.flow.Registration;
import selenium.flow.SelectCharacter;
import selenium.page.FactoryPage;

public class FactoryTest {

    private final WebDriver driver;
    private final FactoryPage factoryPage;
    private final Navigate navigate;
    private final SeleniumCharacter testCharacter = SeleniumCharacter.create();

    private FactoryTest(WebDriver driver){
        this.driver = driver;
        this.factoryPage = new FactoryPage(driver);
        this.navigate = new Navigate(driver);
    }

    public static void run(WebDriver driver){
        FactoryTest testCase = new FactoryTest(driver);
        testCase.init();
        testCase.testNotEnoughMaterials();
        testCase.testBuildAndFinish();
    }

    private void testNotEnoughMaterials() {
        NotEnoughMaterialTest test = NotEnoughMaterialTest.builder()
            .driver(driver)
            .factoryPage(factoryPage)
            .build();
        test.testNotEnoughMaterial();
    }

    private void testBuildAndFinish() {

    }

    private void init() {
        new Registration(driver).registerUser();
        new CreateCharacter(driver).createCharacter(testCharacter);
        new SelectCharacter(driver).selectCharacter(testCharacter);
        navigate.toFactory();
    }
}
