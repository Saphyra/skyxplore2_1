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
public class FactoryFacadeTest {
    private static final String CHARACTER_ID = "character_id";
    @Mock
    private AddToQueueService addToQueueService;

    @Mock
    private FactoryQueryService factoryQueryService;

    @InjectMocks
    private FactoryFacade underTest;

    @Test
    public void testAddToQueueShouldCallService() {
        //GIVEN
        AddToQueueRequest request = new AddToQueueRequest();
        //WHEN
        underTest.addToQueue(CHARACTER_ID, request);
        //THEN
        verify(addToQueueService).addToQueue(CHARACTER_ID, request);
    }

    @Test
    public void testGetMaterialsShouldCallServiceAndReturn() {
        //GIVEN
        Materials materials = new Materials();
        when(factoryQueryService.getMaterials(CHARACTER_ID)).thenReturn(materials);
        //WHEN
        Map<String, Integer> result = underTest.getMaterials(CHARACTER_ID);
        //THEN
        verify(factoryQueryService).getMaterials(CHARACTER_ID);
        assertThat(result).isEqualTo(materials);
    }
}