package com.github.saphyra.skyxplore.userdata.factory.impl.addtoqueue;

import static com.github.saphyra.testing.ExceptionValidator.verifyException;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.exceptionhandling.exception.PaymentRequiredException;
import com.github.saphyra.skyxplore.common.ErrorCode;
import com.github.saphyra.skyxplore.data.GameDataFacade;
import com.github.saphyra.skyxplore.data.entity.FactoryData;
import com.github.saphyra.skyxplore.userdata.character.CharacterQueryService;
import com.github.saphyra.skyxplore.userdata.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.userdata.character.repository.CharacterDao;
import com.github.saphyra.skyxplore.userdata.factory.FactoryQueryService;
import com.github.saphyra.skyxplore.userdata.factory.domain.AddToQueueRequest;
import com.github.saphyra.skyxplore.userdata.factory.domain.Factory;
import com.github.saphyra.skyxplore.userdata.factory.domain.Materials;
import com.github.saphyra.skyxplore.userdata.product.ProductFacade;

@RunWith(MockitoJUnitRunner.class)
public class AddToQueueServiceTest {
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
    private AddToQueueService underTest;

    private AddToQueueRequest addToQueueRequest;

    @Before
    public void setUp() {
        addToQueueRequest = new AddToQueueRequest(ELEMENT_ID, AMOUNT);

        when(characterQueryService.findByCharacterIdValidated(CHARACTER_ID)).thenReturn(character);
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

    @Test
    public void testAddToQueueShouldThrowExceptionWhenNotEnoughMoney() {
        //GIVEN
        when(character.getMoney()).thenReturn(0);
        when(factoryData.getBuildPrice()).thenReturn(BUILD_PRICE);
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.addToQueue(CHARACTER_ID, addToQueueRequest));
        //THEN
        verifyException(ex, PaymentRequiredException.class, ErrorCode.NOT_ENOUGH_MONEY);
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