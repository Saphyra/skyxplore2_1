package org.github.saphyra.skyxplore.product;

import org.github.saphyra.skyxplore.character.CharacterQueryService;
import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import org.github.saphyra.skyxplore.character.repository.CharacterDao;
import org.github.saphyra.skyxplore.common.DateTimeUtil;
import org.github.saphyra.skyxplore.factory.FactoryQueryService;
import org.github.saphyra.skyxplore.factory.domain.Factory;
import org.github.saphyra.skyxplore.factory.domain.Materials;
import org.github.saphyra.skyxplore.factory.repository.FactoryDao;
import org.github.saphyra.skyxplore.gamedata.GameDataFacade;
import org.github.saphyra.skyxplore.gamedata.entity.Material;
import org.github.saphyra.skyxplore.gamedata.entity.GeneralDescription;
import org.github.saphyra.skyxplore.product.domain.Product;
import org.github.saphyra.skyxplore.product.repository.ProductDao;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.github.saphyra.skyxplore.testing.TestGeneralDescription;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductFactoryServiceTest {
    private static final String PRODUCT_EXCEPTIONAL_ELEMENT = "exceptional";
    private static final String PRODUCT_ID_1 = "product_id_1";
    private static final String ELEMENT_ID = "element_id";
    private static final String FACTORY_ID_1 = "factory_id_1";
    private static final String PRODUCT_ID_2 = "product_id_2";
    private static final String FACTORY_ID_2 = "factory_id_2";
    private static final String PRODUCT_ID_3 = "product_id_3";
    private static final String FACTORY_ID_3 = "factory_id_3";
    private static final String CATEGORY_ID = "category_id";
    private static final String SLOT = "slot";
    private static final String CHARACTER_ID = "character_id";
    private static final OffsetDateTime START_TIME = OffsetDateTime.now(ZoneOffset.UTC);
    private static final Integer AMOUNT = 3;
    private static final long CONSTRUCTION_TIME = 3L;

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
        Product finishedMaterialProduct = Product.builder()
            .productId(PRODUCT_ID_1)
            .elementId(ELEMENT_ID)
            .factoryId(FACTORY_ID_1)
            .build();

        Product finishedExceptionalProduct =Product.builder()
            .productId(PRODUCT_ID_2)
            .elementId(PRODUCT_EXCEPTIONAL_ELEMENT)
            .factoryId(FACTORY_ID_2)
            .build();



        Product finishedEquipmentProduct =
            Product.builder()
                .productId(PRODUCT_ID_3)
                .elementId(ELEMENT_ID)
                .factoryId(FACTORY_ID_3)
                .build();


        List<Product> finishedProducts = Arrays.asList(finishedMaterialProduct, finishedExceptionalProduct, finishedEquipmentProduct);
        when(productDao.getFinishedProducts()).thenReturn(finishedProducts);

        //===FINISH MATERIAL PRODUCT===
        Material material = new Material();
        when(gameDataFacade.getData(ELEMENT_ID)).thenReturn(material);

        Factory factoryForMaterialProduct = Factory.builder()
            .factoryId(FACTORY_ID_1)
            .build();
        factoryForMaterialProduct.setMaterials(new Materials());
        when(factoryQueryService.findByFactoryId(FACTORY_ID_1)).thenReturn(factoryForMaterialProduct);

        //===FINISH EXCEPTIONAL PRODUCT===
        when(gameDataFacade.getData(PRODUCT_EXCEPTIONAL_ELEMENT)).thenThrow(new RuntimeException());

        //===FINISH EQUIPMENT PRODUCT===
        GeneralDescription generalDescription = new TestGeneralDescription(ELEMENT_ID, CATEGORY_ID, SLOT);
        when(gameDataFacade.getData(ELEMENT_ID)).thenReturn(generalDescription);

        when(characterQueryService.findByCharacterId(CHARACTER_ID)).thenReturn(character);

        Factory factoryForEquipmentProduct = Factory.builder()
            .factoryId(FACTORY_ID_3)
            .build();
        factoryForEquipmentProduct.setMaterials(new Materials());
        when(factoryQueryService.findByFactoryId(FACTORY_ID_3)).thenReturn(factoryForEquipmentProduct);

        //===PRODUCTS TO START===
        Product exceptionalProduct = Product.builder()
            .productId(PRODUCT_EXCEPTIONAL_ELEMENT)
            .build();

        Product productToStart = Product.builder()
            .productId(PRODUCT_ID_2)
            .build();
        List<Product> productsToStart = Arrays.asList(exceptionalProduct, productToStart);

        when(productDao.getFirstOfQueue()).thenReturn(productsToStart);

        //===STARTING EXCEPTIONAL PRODUCT===
        when(dateTimeUtil.now()).thenReturn(START_TIME);
        doThrow(new RuntimeException()).when(productDao).save(exceptionalProduct);
        //WHEN
        underTest.process();
        //THEN
        verify(productDao).getFinishedProducts();
        verify(gameDataFacade).getData(ELEMENT_ID);
        verify(factoryQueryService).findByFactoryId(FACTORY_ID_1);

        assertThat(factoryForMaterialProduct.getMaterials()).hasSize(1)
            .containsValue(AMOUNT);


        verify(factoryDao).save(factoryForMaterialProduct);
        verify(gameDataFacade).getData(PRODUCT_EXCEPTIONAL_ELEMENT);
        verify(gameDataFacade).getData(ELEMENT_ID);
        verify(factoryQueryService).findByFactoryId(FACTORY_ID_3);
        verify(characterQueryService).findByCharacterId(CHARACTER_ID);
        verify(character).addEquipments(ELEMENT_ID, AMOUNT);
        verify(characterDao).save(character);
        verify(productDao).delete(finishedMaterialProduct);
        verify(productDao).delete(finishedEquipmentProduct);
        verify(productDao).getFirstOfQueue();
        verify(dateTimeUtil, times(2)).now();
        verify(productDao).save(exceptionalProduct);
        verify(productDao).save(productToStart);
        assertThat(productToStart.getStartTime()).isEqualTo(START_TIME);
        assertThat(productToStart.getEndTime()).isEqualTo(START_TIME.plusSeconds(CONSTRUCTION_TIME));
    }
}
