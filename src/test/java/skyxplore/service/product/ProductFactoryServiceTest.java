package skyxplore.service.product;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.CATEGORY_ID;
import static skyxplore.testutil.TestUtils.CHARACTER_ID_1;
import static skyxplore.testutil.TestUtils.DATA_ID_1;
import static skyxplore.testutil.TestUtils.DATA_SLOT;
import static skyxplore.testutil.TestUtils.FACTORY_ID_1;
import static skyxplore.testutil.TestUtils.FACTORY_ID_2;
import static skyxplore.testutil.TestUtils.FACTORY_ID_3;
import static skyxplore.testutil.TestUtils.PRODUCT_AMOUNT;
import static skyxplore.testutil.TestUtils.PRODUCT_CONSTRUCTION_TIME;
import static skyxplore.testutil.TestUtils.PRODUCT_ELEMENT_ID_EQUIPMENT;
import static skyxplore.testutil.TestUtils.PRODUCT_ELEMENT_ID_MATERIAL;
import static skyxplore.testutil.TestUtils.PRODUCT_ID_1;
import static skyxplore.testutil.TestUtils.PRODUCT_ID_2;
import static skyxplore.testutil.TestUtils.PRODUCT_ID_3;
import static skyxplore.testutil.TestUtils.PRODUCT_START_TIME;
import static skyxplore.testutil.TestUtils.createFactory;
import static skyxplore.testutil.TestUtils.createMaterial;
import static skyxplore.testutil.TestUtils.createProduct;

import java.util.Arrays;
import java.util.List;

import org.github.saphyra.skyxplore.character.CharacterQueryService;
import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import org.github.saphyra.skyxplore.character.repository.CharacterDao;
import org.github.saphyra.skyxplore.common.DateTimeUtil;
import org.github.saphyra.skyxplore.gamedata.GameDataFacade;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.github.saphyra.skyxplore.factory.repository.FactoryDao;
import org.github.saphyra.skyxplore.product.repository.ProductDao;
import org.github.saphyra.skyxplore.gamedata.entity.Material;
import org.github.saphyra.skyxplore.gamedata.entity.abstractentity.GeneralDescription;
import org.github.saphyra.skyxplore.factory.domain.Factory;
import org.github.saphyra.skyxplore.factory.domain.Materials;
import org.github.saphyra.skyxplore.product.domain.Product;
import skyxplore.service.factory.FactoryQueryService;
import skyxplore.testutil.TestGeneralDescription;

@RunWith(MockitoJUnitRunner.class)
public class ProductFactoryServiceTest {
    private static final String PRODUCT_EXCEPTIONAL_ELEMENT = "exceptional";

    @Mock
    private CharacterQueryService characterQueryService;

    @Mock
    private CharacterDao characterDao;

    @Mock
    private DateTimeUtil dateTimeUtil;

    @Mock
    private GameDataFacade gameDataFacade;

    @Mock
    private FactoryQueryService factoryQueryService;

    @Mock
    private FactoryDao factoryDao;

    @Mock
    private ProductDao productDao;

    @Mock
    private SkyXpCharacter character;

    @InjectMocks
    private ProductFactoryService underTest;

    @Test
    //TODO enable when service splitted
    @Ignore
    public void testProcessShouldProcessFinishedProductsAndStartNewProducts() {
        //GIVEN
        //===PRODUCTS TO FINISH===
        Product finishedMaterialProduct = createProduct(PRODUCT_ID_1);
        finishedMaterialProduct.setElementId(PRODUCT_ELEMENT_ID_MATERIAL);
        finishedMaterialProduct.setFactoryId(FACTORY_ID_1);

        Product finishedExceptionalProduct = createProduct(PRODUCT_ID_2);
        finishedExceptionalProduct.setElementId(PRODUCT_EXCEPTIONAL_ELEMENT);
        finishedExceptionalProduct.setFactoryId(FACTORY_ID_2);

        Product finishedEquipmentProduct = createProduct(PRODUCT_ID_3);
        finishedEquipmentProduct.setElementId(PRODUCT_ELEMENT_ID_EQUIPMENT);
        finishedEquipmentProduct.setFactoryId(FACTORY_ID_3);

        List<Product> finishedProducts = Arrays.asList(finishedMaterialProduct, finishedExceptionalProduct, finishedEquipmentProduct);
        when(productDao.getFinishedProducts()).thenReturn(finishedProducts);

        //===FINISH MATERIAL PRODUCT===
        Material material = createMaterial();
        when(gameDataFacade.getData(PRODUCT_ELEMENT_ID_MATERIAL)).thenReturn(material);

        Factory factoryForMaterialProduct = createFactory(FACTORY_ID_1);
        factoryForMaterialProduct.setMaterials(new Materials());
        when(factoryQueryService.findByFactoryId(FACTORY_ID_1)).thenReturn(factoryForMaterialProduct);

        //===FINISH EXCEPTIONAL PRODUCT===
        when(gameDataFacade.getData(PRODUCT_EXCEPTIONAL_ELEMENT)).thenThrow(new RuntimeException());

        //===FINISH EQUIPMENT PRODUCT===
        GeneralDescription generalDescription = new TestGeneralDescription(DATA_ID_1, CATEGORY_ID, DATA_SLOT);
        when(gameDataFacade.getData(PRODUCT_ELEMENT_ID_EQUIPMENT)).thenReturn(generalDescription);

        when(characterQueryService.findByCharacterId(CHARACTER_ID_1)).thenReturn(character);

        Factory factoryForEquipmentProduct = createFactory(FACTORY_ID_3);
        factoryForEquipmentProduct.setMaterials(new Materials());
        when(factoryQueryService.findByFactoryId(FACTORY_ID_3)).thenReturn(factoryForEquipmentProduct);

        //===PRODUCTS TO START===
        Product exceptionalProduct = createProduct(PRODUCT_ID_1);

        Product productToStart = createProduct(PRODUCT_ID_2);
        productToStart.setStartTime(null);
        productToStart.setEndTime(null);

        List<Product> productsToStart = Arrays.asList(exceptionalProduct, productToStart);

        when(productDao.getFirstOfQueue()).thenReturn(productsToStart);

        //===STARTING EXCEPTIONAL PRODUCT===
        when(dateTimeUtil.now()).thenReturn(PRODUCT_START_TIME);
        doThrow(new RuntimeException()).when(productDao).save(exceptionalProduct);
        //WHEN
        underTest.process();
        //THEN
        verify(productDao).getFinishedProducts();
        verify(gameDataFacade).getData(PRODUCT_ELEMENT_ID_MATERIAL);
        verify(factoryQueryService).findByFactoryId(FACTORY_ID_1);
        assertEquals(1, factoryForMaterialProduct.getMaterials().size());
        assertEquals(PRODUCT_AMOUNT, factoryForMaterialProduct.getMaterials().get(PRODUCT_ELEMENT_ID_MATERIAL));
        verify(factoryDao).save(factoryForMaterialProduct);
        verify(gameDataFacade).getData(PRODUCT_EXCEPTIONAL_ELEMENT);
        verify(gameDataFacade).getData(PRODUCT_ELEMENT_ID_EQUIPMENT);
        verify(factoryQueryService).findByFactoryId(FACTORY_ID_3);
        verify(characterQueryService).findByCharacterId(CHARACTER_ID_1);
        verify(character).addEquipments(PRODUCT_ELEMENT_ID_EQUIPMENT, PRODUCT_AMOUNT);
        verify(characterDao).save(character);
        verify(productDao).delete(finishedMaterialProduct);
        verify(productDao).delete(finishedEquipmentProduct);
        verify(productDao).getFirstOfQueue();
        verify(dateTimeUtil, times(2)).now();
        verify(productDao).save(exceptionalProduct);
        verify(productDao).save(productToStart);
        assertEquals(PRODUCT_START_TIME, productToStart.getStartTime());
        assertEquals(PRODUCT_START_TIME.plusSeconds(PRODUCT_CONSTRUCTION_TIME), productToStart.getEndTime());
    }
}
