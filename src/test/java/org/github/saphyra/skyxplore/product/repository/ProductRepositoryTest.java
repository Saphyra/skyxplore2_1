package org.github.saphyra.skyxplore.product.repository;

import org.github.saphyra.skyxplore.testing.configuration.DataSourceConfiguration;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ProductRepositoryTest.TestConfig.class)
public class ProductRepositoryTest {
    private static final String PRODUCT_ID_1 = "product_id_1";
    private static final String PRODUCT_ID_2 = "product_id_2";
    private static final String PRODUCT_ID_3 = "product_id_3";
    private static final String PRODUCT_ID_4 = "product_id_4";
    private static final String PRODUCT_ID_5 = "product_id_5";
    private static final String PRODUCT_ID_6 = "product_id_6";
    private static final String FACTORY_ID_1 = "factory_id_1";
    private static final String FACTORY_ID_2 = "factory_id_2";
    private static final String FACTORY_ID_3 = "factory_id_3";
    private static final Long NOW = 100L;

    @Autowired
    private ProductRepository underTest;

    @After
    public void tearDown() {
        underTest.deleteAll();
    }

    @Test
    public void deleteByFactoryId() {
        //GIVEN
        ProductEntity entity1 = createProductEntity(PRODUCT_ID_1, FACTORY_ID_1);
        ProductEntity entity2 = createProductEntity(PRODUCT_ID_2, FACTORY_ID_1);
        ProductEntity entity3 = createProductEntity(PRODUCT_ID_3, FACTORY_ID_2);

        underTest.saveAll(Arrays.asList(entity1, entity2, entity3));
        //WHEN
        underTest.deleteByFactoryId(FACTORY_ID_1);
        //THEN
        assertThat(underTest.findAll()).containsExactly(entity3);
    }

    @Test
    public void getFinishedProducts() {
        //GIVEN
        ProductEntity entity1 = createProductEntity(PRODUCT_ID_1, FACTORY_ID_1, NOW - 1);
        ProductEntity entity2 = createProductEntity(PRODUCT_ID_2, FACTORY_ID_1, NOW - 1);
        ProductEntity entity3 = createProductEntity(PRODUCT_ID_3, FACTORY_ID_2, NOW);

        underTest.saveAll(Arrays.asList(entity1, entity2, entity3));
        //WHEN
        List<ProductEntity> result = underTest.getFinishedProducts(NOW);
        //THEN
        assertThat(result).containsOnly(entity1, entity2);
    }

    @Test
    public void getFirstOfQueue() {
        //GIVEN
        ProductEntity notFinishedProductOfFactory1 = createProductEntity(PRODUCT_ID_1, FACTORY_ID_1, NOW + 1, NOW - 1, NOW - 5);
        ProductEntity notStartedProductOfFactory1 = createProductEntity(PRODUCT_ID_2, FACTORY_ID_1, null, null, NOW - 4);
        ProductEntity firstNotStartedProductOfFactory2 = createProductEntity(PRODUCT_ID_3, FACTORY_ID_2, null, null, NOW - 2);
        ProductEntity secondNotStartedProductOfFactory2 = createProductEntity(PRODUCT_ID_4, FACTORY_ID_2, null, null, NOW - 1);
        ProductEntity firstNotStartedProductOfFactory3 = createProductEntity(PRODUCT_ID_5, FACTORY_ID_3, null, null, NOW - 2);
        ProductEntity secondNotStartedProductOfFactory3 = createProductEntity(PRODUCT_ID_6, FACTORY_ID_3, null, null, NOW - 1);

        underTest.saveAll(Arrays.asList(
            notFinishedProductOfFactory1,
            notStartedProductOfFactory1,
            firstNotStartedProductOfFactory2,
            secondNotStartedProductOfFactory2,
            firstNotStartedProductOfFactory3,
            secondNotStartedProductOfFactory3
        ));
        //WHEN
        List<ProductEntity> result = underTest.getFirstOfQueue();
        //THEN
        assertThat(result).containsOnly(firstNotStartedProductOfFactory2, firstNotStartedProductOfFactory3);
    }

    private ProductEntity createProductEntity(String productId, String factoryId, Long endTime, Long startTime, Long addedAt) {
        return ProductEntity.builder()
            .productId(productId)
            .factoryId(factoryId)
            .elementId("")
            .amount("")
            .constructionTime("")
            .endTime(endTime)
            .startTime(startTime)
            .addedAt(addedAt)
            .build();
    }

    private ProductEntity createProductEntity(String productId, String factoryId, Long endTime) {
        return createProductEntity(productId, factoryId, endTime, null, 0L);
    }

    private ProductEntity createProductEntity(String productId, String factoryId) {
        return createProductEntity(productId, factoryId, null);
    }

    @TestConfiguration
    @EntityScan(basePackageClasses = ProductEntity.class)
    @EnableJpaRepositories(basePackageClasses = ProductRepository.class)
    @Import(DataSourceConfiguration.class)
    static class TestConfig {

    }
}