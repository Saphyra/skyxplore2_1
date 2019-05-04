package org.github.saphyra.skyxplore.factory.impl.addtoqueue;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import org.github.saphyra.skyxplore.character.CharacterQueryService;
import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import org.github.saphyra.skyxplore.character.repository.CharacterDao;
import org.github.saphyra.skyxplore.common.exception.NotEnoughMoneyException;
import org.github.saphyra.skyxplore.factory.FactoryQueryService;
import org.github.saphyra.skyxplore.factory.domain.AddToQueueRequest;
import org.github.saphyra.skyxplore.factory.domain.Factory;
import org.github.saphyra.skyxplore.factory.domain.Materials;
import org.github.saphyra.skyxplore.gamedata.GameDataFacade;
import org.github.saphyra.skyxplore.gamedata.entity.FactoryData;
import org.github.saphyra.skyxplore.product.ProductFacade;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AddToQueueServiceImplTest {
    private static final String ELEMENT_ID = "element_id";
    private static final Integer AMOUNT = 2;
    private static final String CHARACTER_ID = "character_id";
    private static final Integer BUILD_PRICE = 93;
    private static final String FACTORY_ID = "factory_id";

    @Mock
    private CharacterDao characterDao;

    @Mock
    private CharacterQueryService characterQueryService;

    @Mock
    private FactoryQueryService factoryQueryService;

    @Mock
    private GameDataFacade gameDataFacade;

    @Mock
    private MaterialsValidator materialsValidator;

    @Mock
    private ProductFacade productFacade;

    @Mock
    private SpendMaterialsService spendMaterialsService;

    @Mock
    private SkyXpCharacter character;

    @Mock
    private Factory factory;

    @Mock
    private FactoryData factoryData;

    @InjectMocks
    private AddToQueueServiceImpl underTest;

    private AddToQueueRequest addToQueueRequest;

    @Before
    public void setUp() {
        addToQueueRequest = new AddToQueueRequest(ELEMENT_ID, AMOUNT);

        when(characterQueryService.findByCharacterId(CHARACTER_ID)).thenReturn(character);
        when(factoryQueryService.findFactoryOfCharacterValidated(CHARACTER_ID)).thenReturn(factory);
        when(gameDataFacade.getFactoryData(ELEMENT_ID)).thenReturn(factoryData);
        when(factoryData.isBuildable()).thenReturn(true);
    }

    @Test(expected = BadRequestException.class)
    public void testAddToQueueShouldThrowExceptionWhenNotBuildable() {
        //GIVEN
        when(factoryData.isBuildable()).thenReturn(false);
        //WHEN
        underTest.addToQueue(CHARACTER_ID, addToQueueRequest);
    }

    @Test(expected = NotEnoughMoneyException.class)
    public void testAddToQueueShouldThrowExceptionWhenNotEnoughMoney() {
        //GIVEN
        when(character.getMoney()).thenReturn(0);
        when(factoryData.getBuildPrice()).thenReturn(BUILD_PRICE);
        //WHEN
        underTest.addToQueue(CHARACTER_ID, addToQueueRequest);
    }

    @Test
    public void testAddToQueueShouldProcess() {
        //GIVEN
        when(character.getMoney()).thenReturn(BUILD_PRICE * AMOUNT);
        when(factoryData.getBuildPrice()).thenReturn(BUILD_PRICE);

        Map<String, Integer> currentMaterials = new HashMap<>();
        currentMaterials.put(ELEMENT_ID, AMOUNT * AMOUNT);
        Materials materialsDomain = new Materials(currentMaterials);
        when(factory.getMaterials()).thenReturn(materialsDomain);
        when(factory.getFactoryId()).thenReturn(FACTORY_ID);
        //WHEN
        underTest.addToQueue(CHARACTER_ID, addToQueueRequest);
        //THEN
        verify(materialsValidator).validateMaterials(materialsDomain, factoryData, AMOUNT);

        verify(productFacade).createAndSave(FACTORY_ID, factoryData, AMOUNT);
        verify(character).spendMoney(BUILD_PRICE * AMOUNT);
        verify(characterDao).save(character);
        verify(spendMaterialsService).spendMaterials(factory, factoryData, AMOUNT);
    }
}