package skyxplore.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.controller.view.product.ProductView;
import skyxplore.service.product.ProductQueryService;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.CHARACTER_ID_1;
import static skyxplore.testutil.TestUtils.createProductView;

@RunWith(MockitoJUnitRunner.class)
public class ProductFacadeTest {
    @Mock
    private ProductQueryService productQueryService;

    @InjectMocks
    private ProductFacade underTest;

    @Test
    public void testGetQueueShouldCallService() {
        //GIVEN
        List<ProductView> productViews = Arrays.asList(createProductView());
        when(productQueryService.getQueue(CHARACTER_ID_1)).thenReturn(productViews);
        //WHEN
        List<ProductView> result = underTest.getQueue(CHARACTER_ID_1);
        //THEN
        verify(productQueryService).getQueue(CHARACTER_ID_1);
        assertEquals(productViews, result);
    }

}
