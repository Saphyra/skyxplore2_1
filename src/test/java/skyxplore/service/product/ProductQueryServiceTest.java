package skyxplore.service.product;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.CHARACTER_ID_1;
import static skyxplore.testutil.TestUtils.PRODUCT_ELEMENT_ID_EQUIPMENT;
import static skyxplore.testutil.TestUtils.createGeneralDescriptionMap;
import static skyxplore.testutil.TestUtils.createProduct;
import static skyxplore.testutil.TestUtils.createProductView;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import skyxplore.controller.view.View;
import skyxplore.controller.view.product.ProductView;
import skyxplore.controller.view.product.ProductViewConverter;
import skyxplore.controller.view.product.ProductViewList;
import skyxplore.dataaccess.db.ProductDao;
import skyxplore.dataaccess.gamedata.entity.abstractentity.GeneralDescription;
import skyxplore.domain.product.Product;
import skyxplore.service.GameDataFacade;
import skyxplore.service.factory.FactoryQueryService;

@SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
@RunWith(MockitoJUnitRunner.class)
public class ProductQueryServiceTest {
    @Mock
    private FactoryQueryService factoryQueryService;

    @Mock
    private GameDataFacade gameDataFacade;

    @Mock
    private ProductDao productDao;

    @Mock
    private ProductViewConverter productViewConverter;

    @InjectMocks
    private ProductQueryService underTest;

    @Test
    public void testGetQueue(){
        //GIVEN
        when(factoryQueryService.getFactoryIdOfCharacter(CHARACTER_ID_1)).thenReturn(PRODUCT_ELEMENT_ID_EQUIPMENT);

        Product product = createProduct();
        List<Product> productList = Arrays.asList(product);
        when(productDao.findByFactoryId(PRODUCT_ELEMENT_ID_EQUIPMENT)).thenReturn(productList);

        Map<String, GeneralDescription> data = createGeneralDescriptionMap();
        List<String> productIdList = Arrays.asList(PRODUCT_ELEMENT_ID_EQUIPMENT);
        when(gameDataFacade.collectEquipmentData(productIdList)).thenReturn(data);

        ProductView productView = createProductView();
        when(productViewConverter.convertDomain(productList)).thenReturn(Arrays.asList(productView));
        //WHEN
        View<ProductViewList> result = underTest.getQueue(CHARACTER_ID_1);
        //THEN
        verify(factoryQueryService).getFactoryIdOfCharacter(CHARACTER_ID_1);
        verify(productDao).findByFactoryId(PRODUCT_ELEMENT_ID_EQUIPMENT);
        verify(productViewConverter).convertDomain(productList);
        verify(gameDataFacade).collectEquipmentData(productIdList);
        assertTrue(result.getInfo().contains(productView));
        assertEquals(data, result.getData());
    }
}