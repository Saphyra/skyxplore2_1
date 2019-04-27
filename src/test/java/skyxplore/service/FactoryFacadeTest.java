package skyxplore.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.CHARACTER_ID_1;
import static skyxplore.testutil.TestUtils.createAddToQueueRequest;
import static skyxplore.testutil.TestUtils.createMaterials;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import skyxplore.controller.request.character.AddToQueueRequest;
import skyxplore.domain.materials.Materials;
import skyxplore.service.factory.AddToQueueService;
import skyxplore.service.factory.FactoryQueryService;

@RunWith(MockitoJUnitRunner.class)
public class FactoryFacadeTest {
    @Mock
    private AddToQueueService addToQueueService;

    @Mock
    private FactoryQueryService factoryQueryService;

    @InjectMocks
    private FactoryFacade underTest;

    @Test
    public void testAddToQueueShouldCallService() {
        //GIVEN
        AddToQueueRequest request = createAddToQueueRequest();
        //WHEN
        underTest.addToQueue(CHARACTER_ID_1, request);
        //THEN
        verify(addToQueueService).addToQueue(CHARACTER_ID_1, request);
    }

    @Test
    public void testGetMaterialsShouldCallServiceAndReturn() {
        //GIVEN
        Materials materials = createMaterials();
        when(factoryQueryService.getMaterials(CHARACTER_ID_1)).thenReturn(materials);
        //WHEN
        Map<String, Integer> result = underTest.getMaterials(CHARACTER_ID_1);
        //THEN
        verify(factoryQueryService).getMaterials(CHARACTER_ID_1);
        assertEquals(materials, result);
    }
}