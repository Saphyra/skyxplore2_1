package skyxplore.controller;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.CHARACTER_ID;
import static skyxplore.testutil.TestUtils.MATERIAL_KEY;
import static skyxplore.testutil.TestUtils.USER_ID;
import static skyxplore.testutil.TestUtils.createAddToQueueRequest;
import static skyxplore.testutil.TestUtils.createMaterialView;
import static skyxplore.testutil.TestUtils.createProductViewListView;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import skyxplore.controller.request.character.AddToQueueRequest;
import skyxplore.controller.view.View;
import skyxplore.controller.view.material.MaterialView;
import skyxplore.controller.view.product.ProductViewList;
import skyxplore.service.FactoryFacade;
import skyxplore.service.ProductFacade;

@RunWith(MockitoJUnitRunner.class)
public class FactoryControllerTest {
    @Mock
    private  FactoryFacade factoryFacade;

    @Mock
    private  ProductFacade productFacade;

    @InjectMocks
    private FactoryController underTest;

    @Test
    public void testAddToQueueShouldCallFacade(){
        //GIVEN
        AddToQueueRequest request = createAddToQueueRequest();
        //WHEN
        underTest.addToQueue(CHARACTER_ID, request, USER_ID);
        //THEN
        verify(factoryFacade).addToQueue(USER_ID, CHARACTER_ID, request);
    }

    @Test
    public void testGetMaterialsShouldCallFacadeAndReturnResponse(){
        //GIVEN
        MaterialView view = createMaterialView();
        Map<String,MaterialView> map = new HashMap<>();
        map.put(MATERIAL_KEY, view);
        when(factoryFacade.getMaterials(CHARACTER_ID, USER_ID)).thenReturn(map);
        //WHEN
        Map<String, MaterialView> result = underTest.getMaterials(CHARACTER_ID, USER_ID);
        //THEN
        verify(factoryFacade).getMaterials(CHARACTER_ID, USER_ID);
        assertEquals(1, result.size());
        assertEquals(view, result.get(MATERIAL_KEY));
    }

    @Test
    public void testGetQueueShouldCallFacadeAndReturnResult(){
        //GIVEN
        View<ProductViewList> view = createProductViewListView();
        when(productFacade.getQueue(USER_ID, CHARACTER_ID)).thenReturn(view);
        //WHEN
        View<ProductViewList> result = underTest.getQueue(CHARACTER_ID, USER_ID);
        //THEN
        verify(productFacade).getQueue(USER_ID, CHARACTER_ID);
        assertEquals(view, result);
    }
}
