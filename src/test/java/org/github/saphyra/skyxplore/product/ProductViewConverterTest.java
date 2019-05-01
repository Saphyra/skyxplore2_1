package org.github.saphyra.skyxplore.product;

import org.github.saphyra.skyxplore.common.DateTimeUtil;
import org.github.saphyra.skyxplore.product.domain.ProductView;
import org.github.saphyra.skyxplore.product.domain.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductViewConverterTest {
    private static final OffsetDateTime START_TIME = OffsetDateTime.now(ZoneOffset.UTC);
    private static final Long START_TIME_EPOCH = 7622L;
    private static final OffsetDateTime END_TIME = OffsetDateTime.now(ZoneOffset.UTC).plusSeconds(2);
    private static final Long END_TIME_EPOCH = 65191L;
    private static final String PRODUCT_ID = "product_id";
    private static final String FACTORY_ID = "factory_id";
    private static final String ELEMENT_ID = "element_id";
    private static final int AMOUNT = 3;
    private static final long ADDED_AT = 87368L;
    private static final int CONSTRUCTION_TIME = 2342;

    @Mock
    private DateTimeUtil dateTimeUtil;

    @InjectMocks
    private ProductViewConverter underTest;

    @Test
    public void testConvertShouldReturnView() {
        //GIVEN
        when(dateTimeUtil.convertDomain(START_TIME)).thenReturn(START_TIME_EPOCH);
        when(dateTimeUtil.convertDomain(END_TIME)).thenReturn(END_TIME_EPOCH);
        Product product = Product.builder()
            .productId(PRODUCT_ID)
            .factoryId(FACTORY_ID)
            .elementId(ELEMENT_ID)
            .amount(AMOUNT)
            .addedAt(ADDED_AT)
            .constructionTime(CONSTRUCTION_TIME)
            .startTime(START_TIME)
            .endTime(END_TIME)
            .build();
        //WHEN
        ProductView result = underTest.convertDomain(product);
        //THEN
        assertThat(result.getProductId()).isEqualTo(PRODUCT_ID);
        assertThat(result.getFactoryId()).isEqualTo(FACTORY_ID);
        assertThat(result.getElementId()).isEqualTo(ELEMENT_ID);
        assertThat(result.getAmount()).isEqualTo(AMOUNT);
        assertThat(result.getAddedAt()).isEqualTo(ADDED_AT);
        assertThat(result.getConstructionTime()).isEqualTo(CONSTRUCTION_TIME);
        assertThat(result.getStartTime()).isEqualTo(START_TIME_EPOCH);
        assertThat(result.getEndTime()).isEqualTo(END_TIME_EPOCH);
    }
}
