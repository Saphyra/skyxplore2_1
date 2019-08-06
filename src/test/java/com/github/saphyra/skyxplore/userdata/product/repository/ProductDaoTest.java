package com.github.saphyra.skyxplore.userdata.product.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.common.DateTimeUtil;
import com.github.saphyra.skyxplore.common.event.FactoryDeletedEvent;
import com.github.saphyra.skyxplore.userdata.product.domain.Product;

@RunWith(MockitoJUnitRunner.class)
public class ProductDaoTest {
    private static final String FACTORY_ID = "factory_id";
    private static final Long NOW_EPOCH = 6156L;
    private static final OffsetDateTime NOW = OffsetDateTime.now(ZoneOffset.UTC);

    @Mock
    private ProductConverter converter;

    @Mock
    private ProductRepository repository;

    @Mock
    private DateTimeUtil dateTimeUtil;

    @InjectMocks
    private ProductDao underTest;

    @Mock
    private Product product;

    @Test
    public void factoryDeletedEventListener() {
        //WHEN
        underTest.factoryDeletedEventListener(new FactoryDeletedEvent(FACTORY_ID));
        //THEN
        verify(repository).deleteByFactoryId(FACTORY_ID);
    }

    @Test
    public void getByFactoryId(){
        //GIVEN
        ProductEntity entity = ProductEntity.builder().build();
        List<ProductEntity> entityList = Arrays.asList(entity);
        given(repository.getByFactoryId(FACTORY_ID)).willReturn(entityList);

        List<Product> productList = Arrays.asList(product);
        given(converter.convertEntity(entityList)).willReturn(productList);
        //WHEN
        List<Product> result = underTest.getByFactoryId(FACTORY_ID);
        //THEN
        assertThat(result).isEqualTo(productList);
    }

    @Test
    public void getFinishedProducts(){
        //GIVEN
        given(dateTimeUtil.now()).willReturn(NOW);
        given(dateTimeUtil.convertDomain(NOW)).willReturn(NOW_EPOCH);

        ProductEntity entity = ProductEntity.builder().build();
        List<ProductEntity> entityList = Arrays.asList(entity);
        given(repository.getFinishedProducts(NOW_EPOCH)).willReturn(entityList);

        List<Product> productList = Arrays.asList(product);
        given(converter.convertEntity(entityList)).willReturn(productList);
        //WHEN
        List<Product> result = underTest.getFinishedProducts();
        //THEN
        assertThat(result).isEqualTo(productList);
    }

    @Test
    public void getFirstOfQueue(){
        //GIVEN
        ProductEntity entity = ProductEntity.builder().build();
        List<ProductEntity> entityList = Arrays.asList(entity);
        given(repository.getFirstOfQueue()).willReturn(entityList);

        List<Product> productList = Arrays.asList(product);
        given(converter.convertEntity(entityList)).willReturn(productList);
        //WHEN
        List<Product> result = underTest.getFirstOfQueue();
        //THEN
        assertThat(result).isEqualTo(productList);
    }
}