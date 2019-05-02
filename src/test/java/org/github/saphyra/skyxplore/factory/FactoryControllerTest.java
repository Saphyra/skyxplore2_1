package org.github.saphyra.skyxplore.factory;

import org.github.saphyra.skyxplore.factory.domain.AddToQueueRequest;
import org.github.saphyra.skyxplore.factory.domain.Materials;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FactoryControllerTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String ELEMENT_ID = "element_id";
    private static final Integer AMOUNT = 2;

    @Mock
    private FactoryFacade factoryFacade;

    @InjectMocks
    private FactoryController underTest;

    @Test
    public void testAddToQueueShouldCallFacade() {
        //GIVEN
        AddToQueueRequest request = new AddToQueueRequest(ELEMENT_ID, AMOUNT);
        //WHEN
        underTest.addToQueue(request, CHARACTER_ID);
        //THEN
        verify(factoryFacade).addToQueue(CHARACTER_ID, request);
    }

    @Test
    public void testGetMaterialsShouldCallFacadeAndReturnResponse() {
        //GIVEN
        Materials materials = new Materials();
        when(factoryFacade.getMaterials(CHARACTER_ID)).thenReturn(materials);
        //WHEN
        Map<String, Integer> result = underTest.getMaterials(CHARACTER_ID);
        //THEN
        verify(factoryFacade).getMaterials(CHARACTER_ID);
        assertThat(result).isEqualTo(materials);
    }
}