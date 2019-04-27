package skyxplore.controller.view.product;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.PRODUCT_ELEMENT_ID_EQUIPMENT;
import static skyxplore.testutil.TestUtils.FACTORY_ID_1;
import static skyxplore.testutil.TestUtils.PRODUCT_ADDED_AT;
import static skyxplore.testutil.TestUtils.PRODUCT_AMOUNT;
import static skyxplore.testutil.TestUtils.PRODUCT_CONSTRUCTION_TIME;
import static skyxplore.testutil.TestUtils.PRODUCT_END_TIME;
import static skyxplore.testutil.TestUtils.PRODUCT_ID_1;
import static skyxplore.testutil.TestUtils.PRODUCT_START_TIME;
import static skyxplore.testutil.TestUtils.PRODUCT_END_TIME_EPOCH;
import static skyxplore.testutil.TestUtils.PRODUCT_START_TIME_EPOCH;
import static skyxplore.testutil.TestUtils.createProduct;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.github.saphyra.skyxplore.common.DateTimeUtil;

@RunWith(MockitoJUnitRunner.class)
public class ProductViewConverterTest {
    @Mock
    private DateTimeUtil dateTimeUtil;

    @InjectMocks
    private ProductViewConverter underTest;

    @Test
    public void testConvertShouldReturnView() {
        //GIVEN
        when(dateTimeUtil.convertDomain(PRODUCT_START_TIME)).thenReturn(PRODUCT_START_TIME_EPOCH);
        when(dateTimeUtil.convertDomain(PRODUCT_END_TIME)).thenReturn(PRODUCT_END_TIME_EPOCH);
        //WHEN
        ProductView view = underTest.convertDomain(createProduct());
        //THEN
        verify(dateTimeUtil).convertDomain(PRODUCT_START_TIME);
        verify(dateTimeUtil).convertDomain(PRODUCT_END_TIME);
        assertEquals(PRODUCT_ID_1, view.getProductId());
        assertEquals(FACTORY_ID_1, view.getFactoryId());
        assertEquals(PRODUCT_ELEMENT_ID_EQUIPMENT, view.getElementId());
        assertEquals(PRODUCT_AMOUNT, view.getAmount());
        assertEquals(PRODUCT_ADDED_AT, view.getAddedAt());
        assertEquals(PRODUCT_CONSTRUCTION_TIME, view.getConstructionTime());
        assertEquals(PRODUCT_START_TIME_EPOCH, view.getStartTime());
        assertEquals(PRODUCT_END_TIME_EPOCH, view.getEndTime());
    }
}
