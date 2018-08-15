package skyxplore.controller.view.product;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.TestUtils.ELEMENT_ID;
import static skyxplore.TestUtils.FACTORY_ID;
import static skyxplore.TestUtils.PRODUCT_ADDED_AT;
import static skyxplore.TestUtils.PRODUCT_AMOUNT;
import static skyxplore.TestUtils.PRODUCT_CONSTRUCTION_TIME;
import static skyxplore.TestUtils.PRODUCT_END_TIME;
import static skyxplore.TestUtils.PRODUCT_ID;
import static skyxplore.TestUtils.PRODUCT_START_TIME;
import static skyxplore.TestUtils.PRODUC_END_TIME_EPOCH;
import static skyxplore.TestUtils.PRODUCT_START_TIME_EPOCH;
import static skyxplore.TestUtils.createProduct;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import skyxplore.util.DateTimeConverter;

@RunWith(MockitoJUnitRunner.class)
public class ProductViewConverterTest {
    @Mock
    private DateTimeConverter dateTimeConverter;

    @InjectMocks
    private ProductViewConverter underTest;

    @Test
    public void testConvertShouldReturnView(){
        //GIVEN
        when(dateTimeConverter.convertDomain(PRODUCT_START_TIME)).thenReturn(PRODUCT_START_TIME_EPOCH);
        when(dateTimeConverter.convertDomain(PRODUCT_END_TIME)).thenReturn(PRODUC_END_TIME_EPOCH);
        //WHEN
        ProductView view = underTest.convertDomain(createProduct());
        //THEN
        verify(dateTimeConverter).convertDomain(PRODUCT_START_TIME);
        verify(dateTimeConverter).convertDomain(PRODUCT_END_TIME);
        assertEquals(PRODUCT_ID, view.getProductId());
        assertEquals(FACTORY_ID, view.getFactoryId());
        assertEquals(ELEMENT_ID, view.getElementId());
        assertEquals(PRODUCT_AMOUNT, view.getAmount());
        assertEquals(PRODUCT_ADDED_AT, view.getAddedAt());
        assertEquals(PRODUCT_CONSTRUCTION_TIME, view.getConstructionTime());
        assertEquals(PRODUCT_START_TIME_EPOCH, view.getStartTime());
        assertEquals(PRODUC_END_TIME_EPOCH, view.getEndTime());
    }
}
