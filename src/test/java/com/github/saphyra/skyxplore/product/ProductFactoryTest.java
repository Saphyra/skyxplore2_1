package com.github.saphyra.skyxplore.product;

import com.github.saphyra.skyxplore.common.DateTimeUtil;
import com.github.saphyra.util.IdGenerator;
import com.github.saphyra.skyxplore.gamedata.entity.FactoryData;
import com.github.saphyra.skyxplore.product.domain.Product;
import com.github.saphyra.skyxplore.product.repository.ProductDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ProductFactoryTest {
    private static final String FACTORY_ID = "factory_id";
    private static final Integer AMOUNT = 2;
    private static final String PRODUCT_ID = "product_id";
    private static final String ELEMENT_ID = "element_id";
    private static final Integer CONSTRUCTION_TIME = 10;
    private static final OffsetDateTime NOW = OffsetDateTime.now(ZoneOffset.UTC);
    private static final Long NOW_EPOCH = 99L;

    @Mock
    private DateTimeUtil dateTimeUtil;

    @Mock
    private IdGenerator idGenerator;

    @Mock
    private ProductDao productDao;

    @Mock
    private FactoryData factoryData;

    @InjectMocks
    private ProductFactory underTest;

    @Test
    public void createAndSave(){
        //GIVEN
        given(idGenerator.generateRandomId()).willReturn(PRODUCT_ID);

        given(factoryData.getId()).willReturn(ELEMENT_ID);
        given(factoryData.getConstructionTime()).willReturn(CONSTRUCTION_TIME);
        given(dateTimeUtil.now()).willReturn(NOW);
        given(dateTimeUtil.convertDomain(NOW)).willReturn(NOW_EPOCH);
        //WHEN
        underTest.createAndSave(FACTORY_ID, factoryData, AMOUNT);
        //THEN
        ArgumentCaptor<Product> argumentCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productDao).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getProductId()).isEqualTo(PRODUCT_ID);
        assertThat(argumentCaptor.getValue().getFactoryId()).isEqualTo(FACTORY_ID);
        assertThat(argumentCaptor.getValue().getElementId()).isEqualTo(ELEMENT_ID);
        assertThat(argumentCaptor.getValue().getAmount()).isEqualTo(AMOUNT);
        assertThat(argumentCaptor.getValue().getConstructionTime()).isEqualTo(CONSTRUCTION_TIME * AMOUNT);
        assertThat(argumentCaptor.getValue().getAddedAt()).isEqualTo(NOW_EPOCH);
    }
}