package skyxplore.service.factory;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.util.IdGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.controller.request.character.AddToQueueRequest;
import org.github.saphyra.skyxplore.character.repository.CharacterDao;
import skyxplore.dataaccess.db.FactoryDao;
import skyxplore.dataaccess.db.ProductDao;
import skyxplore.dataaccess.gamedata.entity.abstractentity.FactoryData;
import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import skyxplore.domain.factory.Factory;
import skyxplore.domain.materials.Materials;
import skyxplore.domain.product.Product;
import skyxplore.exception.NotEnoughMaterialsException;
import skyxplore.exception.NotEnoughMoneyException;
import org.github.saphyra.skyxplore.gamedata.GameDataFacade;
import org.github.saphyra.skyxplore.character.CharacterQueryService;
import org.github.saphyra.skyxplore.common.DateTimeUtil;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.CHARACTER_ID_1;
import static skyxplore.testutil.TestUtils.DATA_ELEMENT;
import static skyxplore.testutil.TestUtils.DATA_ELEMENT_AMOUNT;
import static skyxplore.testutil.TestUtils.FACTORY_ID_1;
import static skyxplore.testutil.TestUtils.MATERIAL_AMOUNT;
import static skyxplore.testutil.TestUtils.MATERIAL_ID;
import static skyxplore.testutil.TestUtils.PRODUCT_AMOUNT;
import static skyxplore.testutil.TestUtils.PRODUCT_BUILD_PRICE;
import static skyxplore.testutil.TestUtils.PRODUCT_CONSTRUCTION_TIME;
import static skyxplore.testutil.TestUtils.PRODUCT_ID_1;
import static skyxplore.testutil.TestUtils.PRODUCT_START_TIME;
import static skyxplore.testutil.TestUtils.PRODUCT_START_TIME_EPOCH;
import static skyxplore.testutil.TestUtils.createAddToQueueRequest;

@RunWith(MockitoJUnitRunner.class)
public class AddToQueueServiceTest {
    @Mock
    private CharacterDao characterDao;

    @Mock
    private CharacterQueryService characterQueryService;

    @Mock
    private DateTimeUtil dateTimeUtil;

    @Mock
    private FactoryDao factoryDao;

    @Mock
    private FactoryQueryService factoryQueryService;

    @Mock
    private GameDataFacade gameDataFacade;

    @Mock
    private IdGenerator idGenerator;

    @Mock
    private ProductDao productDao;

    @Mock
    private SkyXpCharacter character;

    private AddToQueueRequest addToQueueRequest;

    @Mock
    private Factory factory;

    @Mock
    private FactoryData factoryData;

    @InjectMocks
    private AddToQueueService underTest;

    @Before
    public void setUp() {
        addToQueueRequest = createAddToQueueRequest();

        when(characterQueryService.findByCharacterId(CHARACTER_ID_1)).thenReturn(character);
        when(factoryQueryService.findFactoryOfCharacterValidated(CHARACTER_ID_1)).thenReturn(factory);
        when(gameDataFacade.getFactoryData(DATA_ELEMENT)).thenReturn(factoryData);
        when(factoryData.isBuildable()).thenReturn(true);
    }

    @Test(expected = BadRequestException.class)
    public void testAddToQueueShouldThrowExceptionWhenNotBuildable(){
        //GIVEN
        when(factoryData.isBuildable()).thenReturn(false);
        //WHEN
        underTest.addToQueue(CHARACTER_ID_1, addToQueueRequest);
    }

    @Test(expected = NotEnoughMoneyException.class)
    public void testAddToQueueShouldThrowExceptionWhenNotEnoughMoney() {
        //GIVEN
        when(character.getMoney()).thenReturn(0);
        when(factoryData.getBuildPrice()).thenReturn(PRODUCT_BUILD_PRICE);
        //WHEN
        underTest.addToQueue(CHARACTER_ID_1, addToQueueRequest);
    }

    @Test(expected = NotEnoughMaterialsException.class)
    public void testAddToQueueShouldThrowExceptionWhenNotEnoughMaterials() {
        //GIVEN
        when(character.getMoney()).thenReturn(PRODUCT_BUILD_PRICE * DATA_ELEMENT_AMOUNT);
        when(factoryData.getBuildPrice()).thenReturn(PRODUCT_BUILD_PRICE);
        when(factory.getMaterials()).thenReturn(new Materials());

        HashMap<String, Integer> materials = new HashMap<>();
        materials.put(MATERIAL_ID, PRODUCT_AMOUNT);
        when(factoryData.getMaterials()).thenReturn(materials);
        //WHEN
        underTest.addToQueue(CHARACTER_ID_1, addToQueueRequest);
    }

    @Test
    public void testAddToQueueShouldProcess() {
        //GIVEN
        when(character.getMoney()).thenReturn(PRODUCT_BUILD_PRICE * DATA_ELEMENT_AMOUNT);
        when(factoryData.getBuildPrice()).thenReturn(PRODUCT_BUILD_PRICE);

        Map<String, Integer> currentMaterials = new HashMap<>();
        currentMaterials.put(MATERIAL_ID, MATERIAL_AMOUNT * DATA_ELEMENT_AMOUNT);
        Materials materialsDomain = new Materials(currentMaterials);
        when(factory.getMaterials()).thenReturn(materialsDomain);
        when(factory.getFactoryId()).thenReturn(FACTORY_ID_1);

        HashMap<String, Integer> materials = new HashMap<>();
        materials.put(MATERIAL_ID, MATERIAL_AMOUNT);
        when(factoryData.getMaterials()).thenReturn(materials);

        when(idGenerator.generateRandomId()).thenReturn(PRODUCT_ID_1);
        when(factoryData.getId()).thenReturn(DATA_ELEMENT);
        when(factoryData.getConstructionTime()).thenReturn(PRODUCT_CONSTRUCTION_TIME);
        when(dateTimeUtil.now()).thenReturn(PRODUCT_START_TIME);
        when(dateTimeUtil.convertDomain(PRODUCT_START_TIME)).thenReturn(PRODUCT_START_TIME_EPOCH);
        //WHEN
        underTest.addToQueue(CHARACTER_ID_1, addToQueueRequest);
        //THEN
        verify(characterQueryService).findByCharacterId(CHARACTER_ID_1);
        verify(factoryQueryService).findFactoryOfCharacterValidated(CHARACTER_ID_1);
        verify(gameDataFacade).getFactoryData(DATA_ELEMENT);

        verify(character).spendMoney(PRODUCT_BUILD_PRICE * DATA_ELEMENT_AMOUNT);
        verify(characterDao).save(character);
        verify(factoryDao).save(factory);

        ArgumentCaptor<Product> argumentCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productDao).save(argumentCaptor.capture());
        assertEquals(PRODUCT_ID_1, argumentCaptor.getValue().getProductId());
        assertEquals(FACTORY_ID_1, argumentCaptor.getValue().getFactoryId());
        assertEquals(DATA_ELEMENT, argumentCaptor.getValue().getElementId());
        assertEquals(DATA_ELEMENT_AMOUNT, argumentCaptor.getValue().getAmount());
        assertEquals(PRODUCT_START_TIME_EPOCH, argumentCaptor.getValue().getAddedAt());
        assertEquals((int) PRODUCT_CONSTRUCTION_TIME * DATA_ELEMENT_AMOUNT, (int) argumentCaptor.getValue().getConstructionTime());
        assertEquals((Integer) 0, materialsDomain.get(MATERIAL_ID));
    }
}