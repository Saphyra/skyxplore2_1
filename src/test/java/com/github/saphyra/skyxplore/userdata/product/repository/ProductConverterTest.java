package com.github.saphyra.skyxplore.userdata.product.repository;

import com.github.saphyra.encryption.impl.IntegerEncryptor;
import com.github.saphyra.encryption.impl.StringEncryptor;
import com.github.saphyra.skyxplore.userdata.product.domain.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import com.github.saphyra.skyxplore.common.DateTimeUtil;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductConverterTest {
    private static final String ENCRYPTED_ELEMENT_ID = "encrypted_element_id";
    private static final String PRODUCT_ID = "product_id";
    private static final String ELEMENT_ID = "element_id";
    private static final String ENCRYPTED_AMOUNT = "encrypted_amount";
    private static final Integer AMOUNT = 7;
    private static final String ENCRYPTED_CONSTRUCTION_TIME = "encrypted_construction_time";
    private static final Integer CONSTRUCTION_TIME = 10000;
    private static final Long START_TIME_EPOCH = 1000000L;
    private static final OffsetDateTime START_TIME = OffsetDateTime.now(ZoneOffset.UTC);
    private static final Long END_TIME_EPOCH = 200000L;
    private static final OffsetDateTime END_TIME = OffsetDateTime.now(ZoneOffset.UTC).minusDays(2);
    private static final String  FACTORY_ID = "factory_id";
    private static final long ADDED_AT = 3151L;

    @Mock
    private DateTimeUtil dateTimeUtil;

    @Mock
    private IntegerEncryptor integerEncryptor;

    @Mock
    private StringEncryptor stringEncryptor;

    @InjectMocks
    private ProductConverter underTest;

    @Test
    public void testConvertEntityShouldReturnNull() {
        //GIVEN
        ProductEntity entity = null;
        //WHEN
        Product result = underTest.convertEntity(entity);
        //THEN
        assertThat(result).isNull();
    }

    @Test
    public void testConvertEntityShouldDecryptAndConvert() {
        //GIVEN
        ProductEntity entity = ProductEntity.builder()
            .productId(PRODUCT_ID)
            .factoryId(FACTORY_ID)
            .elementId(ENCRYPTED_ELEMENT_ID)
            .amount(ENCRYPTED_AMOUNT)
            .constructionTime(ENCRYPTED_CONSTRUCTION_TIME)
            .startTime(START_TIME_EPOCH)
            .endTime(END_TIME_EPOCH)
            .addedAt(ADDED_AT)
            .build();

        when(stringEncryptor.decryptEntity(ENCRYPTED_ELEMENT_ID, PRODUCT_ID)).thenReturn(ELEMENT_ID);
        when(integerEncryptor.decryptEntity(ENCRYPTED_AMOUNT, PRODUCT_ID)).thenReturn(AMOUNT);
        when(integerEncryptor.decryptEntity(ENCRYPTED_CONSTRUCTION_TIME, PRODUCT_ID)).thenReturn(CONSTRUCTION_TIME);
        when(dateTimeUtil.convertEntity(START_TIME_EPOCH)).thenReturn(START_TIME);
        when(dateTimeUtil.convertEntity(END_TIME_EPOCH)).thenReturn(END_TIME);
        //WHEN
        Product result = underTest.convertEntity(entity);
        //THEN
        assertThat(result.getProductId()).isEqualTo(PRODUCT_ID);
        assertThat(result.getFactoryId()).isEqualTo(FACTORY_ID);
        assertThat(result.getElementId()).isEqualTo(ELEMENT_ID);
        assertThat(result.getAmount()).isEqualTo(AMOUNT);
        assertThat(result.getAddedAt()).isEqualTo(ADDED_AT);
        assertThat(result.getConstructionTime()).isEqualTo(CONSTRUCTION_TIME);
        assertThat(result.getStartTime()).isEqualTo(START_TIME);
        assertThat(result.getEndTime()).isEqualTo(END_TIME);
    }

    @Test
    public void testConvertDomainShouldEncryptAndConvert() {
        //GIVEN
        Product product = Product.builder()
            .productId(PRODUCT_ID)
            .factoryId(FACTORY_ID)
            .elementId(ELEMENT_ID)
            .amount(AMOUNT)
            .constructionTime(CONSTRUCTION_TIME)
            .startTime(START_TIME)
            .endTime(END_TIME)
            .addedAt(ADDED_AT)
            .build();

        when(stringEncryptor.encryptEntity(ELEMENT_ID, PRODUCT_ID)).thenReturn(ENCRYPTED_ELEMENT_ID);
        when(integerEncryptor.encryptEntity(AMOUNT, PRODUCT_ID)).thenReturn(ENCRYPTED_AMOUNT);
        when(integerEncryptor.encryptEntity(CONSTRUCTION_TIME, PRODUCT_ID)).thenReturn(ENCRYPTED_CONSTRUCTION_TIME);
        when(dateTimeUtil.convertDomain(START_TIME)).thenReturn(START_TIME_EPOCH);
        when(dateTimeUtil.convertDomain(END_TIME)).thenReturn(END_TIME_EPOCH);

        //WHEN
        ProductEntity result = underTest.convertDomain(product);
        assertThat(result.getProductId()).isEqualTo(PRODUCT_ID);
        assertThat(result.getFactoryId()).isEqualTo(FACTORY_ID);
        assertThat(result.getElementId()).isEqualTo(ENCRYPTED_ELEMENT_ID);
        assertThat(result.getAmount()).isEqualTo(ENCRYPTED_AMOUNT);
        assertThat(result.getAddedAt()).isEqualTo(ADDED_AT);
        assertThat(result.getConstructionTime()).isEqualTo(ENCRYPTED_CONSTRUCTION_TIME);
        assertThat(result.getStartTime()).isEqualTo(START_TIME_EPOCH);
        assertThat(result.getEndTime()).isEqualTo(END_TIME_EPOCH);
    }
}
