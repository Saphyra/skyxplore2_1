package com.github.saphyra.skyxplore.userdata.product;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.data.gamedata.entity.FactoryData;

@RunWith(MockitoJUnitRunner.class)
public class ProductFacadeTest {
    private static final String FACTORY_ID = "factory_id";
    private static final Integer AMOUNT = 2;

    @Mock
    private ProductFactory productFactory;

    @Mock
    private FactoryData factoryData;

    @InjectMocks
    private ProductFacade underTest;

    @Test
    public void createAndSave(){
        //WHEN
        underTest.createAndSave(FACTORY_ID, factoryData, AMOUNT);
        //THEN
        verify(productFactory).createAndSave(FACTORY_ID, factoryData, AMOUNT);
    }
}