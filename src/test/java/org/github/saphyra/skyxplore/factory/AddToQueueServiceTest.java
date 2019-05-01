package org.github.saphyra.skyxplore.factory;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.util.IdGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.github.saphyra.skyxplore.factory.domain.AddToQueueRequest;
import org.github.saphyra.skyxplore.character.repository.CharacterDao;
import org.github.saphyra.skyxplore.factory.repository.FactoryDao;
import org.github.saphyra.skyxplore.product.repository.ProductDao;
import org.github.saphyra.skyxplore.gamedata.entity.abstractentity.FactoryData;
import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import org.github.saphyra.skyxplore.factory.domain.Factory;
import org.github.saphyra.skyxplore.factory.domain.Materials;
import org.github.saphyra.skyxplore.product.domain.Product;
import org.github.saphyra.skyxplore.common.exception.NotEnoughMaterialsException;
import org.github.saphyra.skyxplore.common.exception.NotEnoughMoneyException;
import org.github.saphyra.skyxplore.gamedata.GameDataFacade;
import org.github.saphyra.skyxplore.character.CharacterQueryService;
import org.github.saphyra.skyxplore.common.DateTimeUtil;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AddToQueueServiceTest {
    private static final String ELEMENT_ID = "element_id";
    private static final Integer AMOUNT = 2;
    private static final String CHARACTER_ID = "character_id";
    private static final Integer BUILD_PRICE = 93;
    private static final String FACTORY_ID = "factory_id";
    private static final String PRODUCT_ID = "product_id";
    private static final Integer CONSTRUCTION_TIME = 13213;
    private static final OffsetDateTime START_TIME = OffsetDateTime.now(ZoneOffset.UTC);
    private static final Long START_TIME_EPOCH = 7687L;

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

    @Test(expected = NotEnoughMaterialsException.class)
    public void testAddToQueueShouldThrowExceptionWhenNotEnoughMaterials() {
        //GIVEN
        when(character.getMoney()).thenReturn(BUILD_PRICE * AMOUNT);
        when(factoryData.getBuildPrice()).thenReturn(BUILD_PRICE);
        when(factory.getMaterials()).thenReturn(new Materials());

        HashMap<String, Integer> materials = new HashMap<>();
        materials.put(ELEMENT_ID, AMOUNT);
        when(factoryData.getMaterials()).thenReturn(materials);
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

        HashMap<String, Integer> materials = new HashMap<>();
        materials.put(ELEMENT_ID, AMOUNT);
        when(factoryData.getMaterials()).thenReturn(materials);

        when(idGenerator.generateRandomId()).thenReturn(PRODUCT_ID);
        when(factoryData.getId()).thenReturn(ELEMENT_ID);
        when(factoryData.getConstructionTime()).thenReturn(CONSTRUCTION_TIME);
        when(dateTimeUtil.now()).thenReturn(START_TIME);
        when(dateTimeUtil.convertDomain(START_TIME)).thenReturn(START_TIME_EPOCH);
        //WHEN
        underTest.addToQueue(CHARACTER_ID, addToQueueRequest);
        //THEN
        verify(characterQueryService).findByCharacterId(CHARACTER_ID);
        verify(factoryQueryService).findFactoryOfCharacterValidated(CHARACTER_ID);
        verify(gameDataFacade).getFactoryData(ELEMENT_ID);

        verify(character).spendMoney(BUILD_PRICE * AMOUNT);
        verify(characterDao).save(character);
        verify(factoryDao).save(factory);

        ArgumentCaptor<Product> argumentCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productDao).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getFactoryId()).isEqualTo(FACTORY_ID);
        assertThat(argumentCaptor.getValue().getProductId()).isEqualTo(PRODUCT_ID);
        assertThat(argumentCaptor.getValue().getElementId()).isEqualTo(ELEMENT_ID);
        assertThat(argumentCaptor.getValue().getAmount()).isEqualTo(AMOUNT);
        assertThat(argumentCaptor.getValue().getAddedAt()).isEqualTo(START_TIME_EPOCH);
        assertThat(argumentCaptor.getValue().getConstructionTime()).isEqualTo(AMOUNT * CONSTRUCTION_TIME);
        assertThat(materialsDomain.get(ELEMENT_ID)).isEqualTo(0);
    }
}