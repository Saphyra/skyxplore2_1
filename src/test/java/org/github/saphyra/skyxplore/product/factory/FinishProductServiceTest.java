package org.github.saphyra.skyxplore.product.factory;

import org.github.saphyra.skyxplore.character.CharacterQueryService;
import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import org.github.saphyra.skyxplore.character.repository.CharacterDao;
import org.github.saphyra.skyxplore.factory.FactoryQueryService;
import org.github.saphyra.skyxplore.factory.domain.Factory;
import org.github.saphyra.skyxplore.factory.domain.Materials;
import org.github.saphyra.skyxplore.factory.repository.FactoryDao;
import org.github.saphyra.skyxplore.gamedata.GameDataFacade;
import org.github.saphyra.skyxplore.gamedata.entity.GeneralDescription;
import org.github.saphyra.skyxplore.product.domain.Product;
import org.github.saphyra.skyxplore.product.repository.ProductDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.github.saphyra.skyxplore.product.factory.FinishProductService.CATEGORY_MATERIAL;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class FinishProductServiceTest {
    private static final String ITEM_ID = "item_id";
    private static final String FACTORY_ID = "factory_id";
    private static final int AMOUNT = 3;
    private static final String CHARACTER_ID = "character_id";

    @Mock
    private CharacterDao characterDao;

    @Mock
    private CharacterQueryService characterQueryService;

    @Mock
    private FactoryDao factoryDao;

    @Mock
    private FactoryQueryService factoryQueryService;

    @Mock
    private GameDataFacade gameDataFacade;

    @Mock
    private ProductDao productDao;

    @Mock
    private GeneralDescription itemData;

    @InjectMocks
    private FinishProductService underTest;

    private Product finishedProduct;

    @Before
    public void setUp() {
        finishedProduct = Product.builder()
            .factoryId(FACTORY_ID)
            .elementId(ITEM_ID)
            .amount(AMOUNT)
            .build();
        given(productDao.getFinishedProducts()).willReturn(Arrays.asList(finishedProduct));
        given(gameDataFacade.getData(ITEM_ID)).willReturn(itemData);
    }

    @Test
    public void finishProduct_material() {
        //GIVEN
        given(itemData.getCategory()).willReturn(CATEGORY_MATERIAL);

        Factory factory = Factory.builder()
            .materials(new Materials())
            .build();
        given(factoryQueryService.findByFactoryId(FACTORY_ID)).willReturn(factory);
        //WHEN
        underTest.finishProducts();
        //THEN
        verify(productDao).delete(finishedProduct);
        verify(factoryDao).save(factory);
        assertThat(factory.getMaterials().get(ITEM_ID)).isEqualTo(AMOUNT);
    }

    @Test
    public void finishProduct_equipment() {
        //GIVEN
        given(itemData.getCategory()).willReturn("asd");

        Factory factory = Factory.builder()
            .characterId(CHARACTER_ID)
            .build();
        given(factoryQueryService.findByFactoryId(FACTORY_ID)).willReturn(factory);

        SkyXpCharacter character = SkyXpCharacter.builder().build();
        given(characterQueryService.findByCharacterId(CHARACTER_ID)).willReturn(character);
        //WHEN
        underTest.finishProducts();
        //THEN
        verify(productDao).delete(finishedProduct);
        verify(characterDao).save(character);
        assertThat(character.getEquipments()).containsExactlyInAnyOrder(ITEM_ID, ITEM_ID, ITEM_ID);
    }
}