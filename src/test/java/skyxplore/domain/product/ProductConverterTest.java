package skyxplore.domain.product;

import com.github.saphyra.encryption.impl.IntegerEncryptor;
import com.github.saphyra.encryption.impl.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.util.DateTimeUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.FACTORY_ID_1;
import static skyxplore.testutil.TestUtils.PRODUCT_ADDED_AT;
import static skyxplore.testutil.TestUtils.PRODUCT_AMOUNT;
import static skyxplore.testutil.TestUtils.PRODUCT_CONSTRUCTION_TIME;
import static skyxplore.testutil.TestUtils.PRODUCT_ELEMENT_ID_EQUIPMENT;
import static skyxplore.testutil.TestUtils.PRODUCT_ENCRYPTED_AMOUNT;
import static skyxplore.testutil.TestUtils.PRODUCT_ENCRYPTED_CONSTRUCTION_TIME;
import static skyxplore.testutil.TestUtils.PRODUCT_ENCRYPTED_ELEMENT_ID;
import static skyxplore.testutil.TestUtils.PRODUCT_END_TIME;
import static skyxplore.testutil.TestUtils.PRODUCT_END_TIME_EPOCH;
import static skyxplore.testutil.TestUtils.PRODUCT_ID_1;
import static skyxplore.testutil.TestUtils.PRODUCT_START_TIME;
import static skyxplore.testutil.TestUtils.PRODUCT_START_TIME_EPOCH;
import static skyxplore.testutil.TestUtils.createProduct;
import static skyxplore.testutil.TestUtils.createProductEntity;

@RunWith(MockitoJUnitRunner.class)
public class ProductConverterTest {
    @Mock
    private DateTimeUtil dateTimeUtil;

    @Mock
    private IntegerEncryptor integerEncryptor;

    @Mock
    private StringEncryptor stringEncryptor;

    @InjectMocks
    private ProductConverter underTest;

    @Test
    public void testConvertEntityShouldReturnNull(){
        //GIVEN
        ProductEntity entity = null;
        //WHEN
        Product result = underTest.convertEntity(entity);
        //THEN
        assertNull(result);
    }

    @Test
    public void testConvertEntityShouldDecryptAndConvert(){
        //GIVEN
        ProductEntity entity = createProductEntity();

        when(stringEncryptor.decryptEntity(PRODUCT_ENCRYPTED_ELEMENT_ID, PRODUCT_ID_1)).thenReturn(PRODUCT_ELEMENT_ID_EQUIPMENT);
        when(integerEncryptor.decryptEntity(PRODUCT_ENCRYPTED_AMOUNT, PRODUCT_ID_1)).thenReturn(PRODUCT_AMOUNT);
        when(integerEncryptor.decryptEntity(PRODUCT_ENCRYPTED_CONSTRUCTION_TIME, PRODUCT_ID_1)).thenReturn(PRODUCT_CONSTRUCTION_TIME);
        when(dateTimeUtil.convertEntity(PRODUCT_START_TIME_EPOCH)).thenReturn(PRODUCT_START_TIME);
        when(dateTimeUtil.convertEntity(PRODUCT_END_TIME_EPOCH)).thenReturn(PRODUCT_END_TIME);
        //WHEN
        Product result = underTest.convertEntity(entity);
        //THEN
        assertEquals(PRODUCT_ID_1, result.getProductId());
        assertEquals(FACTORY_ID_1, result.getFactoryId());
        assertEquals(PRODUCT_ELEMENT_ID_EQUIPMENT, result.getElementId());
        assertEquals(PRODUCT_AMOUNT, result.getAmount());
        assertEquals(PRODUCT_ADDED_AT, result.getAddedAt());
        assertEquals(PRODUCT_CONSTRUCTION_TIME, result.getConstructionTime());
        assertEquals(PRODUCT_START_TIME, result.getStartTime());
        assertEquals(PRODUCT_END_TIME, result.getEndTime());
    }

    @Test
    public void testConvertDomainShouldEncryptAndConvert(){
        //GIVEN
        Product product = createProduct();

        when(stringEncryptor.encryptEntity(PRODUCT_ELEMENT_ID_EQUIPMENT, PRODUCT_ID_1)).thenReturn(PRODUCT_ENCRYPTED_ELEMENT_ID);
        when(integerEncryptor.encryptEntity(PRODUCT_AMOUNT, PRODUCT_ID_1)).thenReturn(PRODUCT_ENCRYPTED_AMOUNT);
        when(integerEncryptor.encryptEntity(PRODUCT_CONSTRUCTION_TIME, PRODUCT_ID_1)).thenReturn(PRODUCT_ENCRYPTED_CONSTRUCTION_TIME);
        when(dateTimeUtil.convertDomain(PRODUCT_START_TIME)).thenReturn(PRODUCT_START_TIME_EPOCH);
        when(dateTimeUtil.convertDomain(PRODUCT_END_TIME)).thenReturn(PRODUCT_END_TIME_EPOCH);

        //WHEN
        ProductEntity result = underTest.convertDomain(product);
        assertEquals(PRODUCT_ID_1, result.getProductId());
        assertEquals(FACTORY_ID_1, result.getFactoryId());
        assertEquals(PRODUCT_ENCRYPTED_ELEMENT_ID, result.getElementId());
        assertEquals(PRODUCT_ENCRYPTED_AMOUNT, result.getAmount());
        assertEquals(PRODUCT_ADDED_AT, result.getAddedAt());
        assertEquals(PRODUCT_ENCRYPTED_CONSTRUCTION_TIME, result.getConstructionTime());
        assertEquals(PRODUCT_START_TIME_EPOCH, result.getStartTime());
        assertEquals(PRODUCT_END_TIME_EPOCH, result.getEndTime());
    }
}
