package skyxplore.service.product;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.controller.view.product.ProductView;
import skyxplore.controller.view.product.ProductViewConverter;
import org.github.saphyra.skyxplore.product.repository.ProductDao;
import org.github.saphyra.skyxplore.product.domain.Product;
import skyxplore.service.factory.FactoryQueryService;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.CHARACTER_ID_1;
import static skyxplore.testutil.TestUtils.PRODUCT_ELEMENT_ID_EQUIPMENT;
import static skyxplore.testutil.TestUtils.createProduct;
import static skyxplore.testutil.TestUtils.createProductView;

@RunWith(MockitoJUnitRunner.class)
public class ProductQueryServiceTest {
    @Mock
    private FactoryQueryService factoryQueryService;

    @Mock
    private ProductDao productDao;

    @Mock
    private ProductViewConverter productViewConverter;

    @InjectMocks
    private ProductQueryService underTest;

    @Test
    public void testGetQueue() {
        //GIVEN
        when(factoryQueryService.getFactoryIdOfCharacter(CHARACTER_ID_1)).thenReturn(PRODUCT_ELEMENT_ID_EQUIPMENT);

        Product product = createProduct();
        List<Product> productList = Arrays.asList(product);
        when(productDao.findByFactoryId(PRODUCT_ELEMENT_ID_EQUIPMENT)).thenReturn(productList);

        ProductView productView = createProductView();
        List<ProductView> productViews = Arrays.asList(productView);
        when(productViewConverter.convertDomain(productList)).thenReturn(productViews);
        //WHEN
        List<ProductView> result = underTest.getQueue(CHARACTER_ID_1);
        //THEN
        verify(factoryQueryService).getFactoryIdOfCharacter(CHARACTER_ID_1);
        verify(productDao).findByFactoryId(PRODUCT_ELEMENT_ID_EQUIPMENT);
        verify(productViewConverter).convertDomain(productList);
        assertEquals(productViews, result);
    }
}