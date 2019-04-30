package skyxplore.controller;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.CHARACTER_ID_1;
import static skyxplore.testutil.TestUtils.createAddToQueueRequest;
import static skyxplore.testutil.TestUtils.createMaterials;
import static skyxplore.testutil.TestUtils.createProductView;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import skyxplore.controller.request.character.AddToQueueRequest;
import skyxplore.controller.view.product.ProductView;
import org.github.saphyra.skyxplore.factory.domain.Materials;
import skyxplore.service.FactoryFacade;
import skyxplore.service.ProductFacade;

@RunWith(MockitoJUnitRunner.class)
public class FactoryControllerTest {
    @Mock
    private FactoryFacade factoryFacade;

    @Mock
    private ProductFacade productFacade;

    @InjectMocks
    private FactoryController underTest;

    @Test
    public void testAddToQueueShouldCallFacade() {
        //GIVEN
        AddToQueueRequest request = createAddToQueueRequest();
        //WHEN
        underTest.addToQueue(request, CHARACTER_ID_1);
        //THEN
        verify(factoryFacade).addToQueue(CHARACTER_ID_1, request);
    }

    @Test
    public void testGetMaterialsShouldCallFacadeAndReturnResponse() {
        //GIVEN
        Materials materials = createMaterials();
        when(factoryFacade.getMaterials(CHARACTER_ID_1)).thenReturn(materials);
        //WHEN
        Map<String, Integer> result = underTest.getMaterials(CHARACTER_ID_1);
        //THEN
        verify(factoryFacade).getMaterials(CHARACTER_ID_1);
        assertEquals(materials, result);
    }

    @Test
    public void testGetQueueShouldCallFacadeAndReturnResult() {
        //GIVEN
        List<ProductView> productViews = Arrays.asList(createProductView());
        when(productFacade.getQueue(CHARACTER_ID_1)).thenReturn(productViews);
        //WHEN
        List<ProductView> result = underTest.getQueue(CHARACTER_ID_1);
        //THEN
        verify(productFacade).getQueue(CHARACTER_ID_1);
        assertEquals(productViews, result);
    }
}
