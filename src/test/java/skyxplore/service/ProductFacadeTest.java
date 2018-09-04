package skyxplore.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.CHARACTER_ID;
import static skyxplore.testutil.TestUtils.USER_ID;
import static skyxplore.testutil.TestUtils.createProductViewListView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import skyxplore.controller.view.View;
import skyxplore.controller.view.product.ProductViewList;
import skyxplore.service.product.ProductQueryService;

@RunWith(MockitoJUnitRunner.class)
public class ProductFacadeTest {
    @Mock
    private ProductQueryService productQueryService;

    @InjectMocks
    private ProductFacade underTest;

    @Test
    public void testGetQueueShouldCallService() {
        //GIVEN
        View<ProductViewList> productView = createProductViewListView();
        when(productQueryService.getQueue(USER_ID, CHARACTER_ID)).thenReturn(productView);
        //WHEN
        View<ProductViewList> result = underTest.getQueue(USER_ID, CHARACTER_ID);
        //THEN
        verify(productQueryService).getQueue(USER_ID, CHARACTER_ID);
        assertEquals(productView, result);
    }

}
