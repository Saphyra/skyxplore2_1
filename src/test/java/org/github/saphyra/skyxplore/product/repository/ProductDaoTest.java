package org.github.saphyra.skyxplore.product.repository;

import org.github.saphyra.skyxplore.common.DateTimeUtil;
import org.github.saphyra.skyxplore.event.FactoryDeletedEvent;
import org.github.saphyra.skyxplore.product.domain.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

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

    @Test
    public void factoryDeletedEventListener() {
        //WHEN
        underTest.factoryDeletedEventListener(new FactoryDeletedEvent(FACTORY_ID));
        //THEN
        verify(repository).deleteByFactoryId(FACTORY_ID);
    }

    @Test
    public void findByFactoryId(){
        //GIVEN
        ProductEntity entity = ProductEntity.builder().build();
        List<ProductEntity> entityList = Arrays.asList(entity);
        given(repository.findByFactoryId(FACTORY_ID)).willReturn(entityList);

        Product product = Product.builder().build();
        List<Product> productList = Arrays.asList(product);
        given(converter.convertEntity(entityList)).willReturn(productList);
        //WHEN
        List<Product> result = underTest.findByFactoryId(FACTORY_ID);
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

        Product product = Product.builder().build();
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

        Product product = Product.builder().build();
        List<Product> productList = Arrays.asList(product);
        given(converter.convertEntity(entityList)).willReturn(productList);
        //WHEN
        List<Product> result = underTest.getFirstOfQueue();
        //THEN
        assertThat(result).isEqualTo(productList);
    }
}