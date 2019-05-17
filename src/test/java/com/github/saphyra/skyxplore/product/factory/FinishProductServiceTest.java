package com.github.saphyra.skyxplore.product.factory;

import static com.github.saphyra.skyxplore.product.factory.FinishProductService.CATEGORY_MATERIAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.character.CharacterQueryService;
import com.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.character.repository.CharacterDao;
import com.github.saphyra.skyxplore.factory.FactoryQueryService;
import com.github.saphyra.skyxplore.factory.domain.Factory;
import com.github.saphyra.skyxplore.factory.domain.Materials;
import com.github.saphyra.skyxplore.factory.repository.FactoryDao;
import com.github.saphyra.skyxplore.gamedata.GameDataFacade;
import com.github.saphyra.skyxplore.gamedata.entity.GeneralDescription;
import com.github.saphyra.skyxplore.product.domain.Product;
import com.github.saphyra.skyxplore.product.repository.ProductDao;


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

    @Mock
    private Product finishedProduct;

    @Mock
    private Factory factory;

    @Mock
    private SkyXpCharacter character;

    @Before
    public void setUp() {
        given(finishedProduct.getFactoryId()).willReturn(FACTORY_ID);
        given(finishedProduct.getElementId()).willReturn(ITEM_ID);
        given(finishedProduct.getAmount()).willReturn(AMOUNT);
        given(productDao.getFinishedProducts()).willReturn(Arrays.asList(finishedProduct));
        given(gameDataFacade.getData(ITEM_ID)).willReturn(itemData);
    }

    @Test
    public void finishProduct_material() {
        //GIVEN
        given(itemData.getCategory()).willReturn(CATEGORY_MATERIAL);

        Materials materials = new Materials();
        given(factory.getMaterials()).willReturn(materials);
        given(factoryQueryService.findByFactoryId(FACTORY_ID)).willReturn(factory);
        //WHEN
        underTest.finishProducts();
        //THEN
        verify(productDao).delete(finishedProduct);
        verify(factoryDao).save(factory);
        assertThat(materials.get(ITEM_ID)).isEqualTo(AMOUNT);
    }

    @Test
    public void finishProduct_equipment() {
        //GIVEN
        given(itemData.getCategory()).willReturn("asd");

        given(factory.getCharacterId()).willReturn(CHARACTER_ID);
        given(factoryQueryService.findByFactoryId(FACTORY_ID)).willReturn(factory);

        given(characterQueryService.findByCharacterId(CHARACTER_ID)).willReturn(character);
        //WHEN
        underTest.finishProducts();
        //THEN
        verify(productDao).delete(finishedProduct);
        verify(characterDao).save(character);
        verify(character).addEquipments(ITEM_ID, 3);
    }
}