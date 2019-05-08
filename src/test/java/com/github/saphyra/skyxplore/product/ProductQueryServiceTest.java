package com.github.saphyra.skyxplore.product;

import com.github.saphyra.skyxplore.factory.FactoryQueryService;
import com.github.saphyra.skyxplore.product.domain.Product;
import com.github.saphyra.skyxplore.product.domain.ProductView;
import com.github.saphyra.skyxplore.product.repository.ProductDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductQueryServiceTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String FACTORY_ID = "factory_id";
    private static final String FACTORY_ID_1 = "factory_id_1";
    private static final String FACTORY_ID_2 = "factory_id_2";

    @Mock
    private FactoryQueryService factoryQueryService;

    @Mock
    private ProductDao productDao;

    @Mock
    private ProductViewConverter productViewConverter;

    @InjectMocks
    private ProductQueryService underTest;

    @Test
    public void getFirstFromQueue() {
        //GIVEN
        Product product1 = Product.builder()
            .factoryId(FACTORY_ID_1)
            .build();

        Product product2 = Product.builder()
            .factoryId(FACTORY_ID_1)
            .build();

        Product product3 = Product.builder()
            .factoryId(FACTORY_ID_2)
            .build();
        given(productDao.getFirstOfQueue()).willReturn(Arrays.asList(product1, product2, product3));
        //WHEN
        List<Product> result = underTest.getFirstFromQueue();
        //THEN
        assertThat(result).containsExactlyInAnyOrder(product1, product3);
    }

    @Test
    public void testGetQueue() {
        //GIVEN
        when(factoryQueryService.getFactoryIdOfCharacter(CHARACTER_ID)).thenReturn(FACTORY_ID);

        Product product = Product.builder()
            .build();
        List<Product> productList = Arrays.asList(product);
        when(productDao.getByFactoryId(FACTORY_ID)).thenReturn(productList);

        ProductView productView = ProductView.builder()
            .build();
        List<ProductView> productViews = Arrays.asList(productView);
        when(productViewConverter.convertDomain(productList)).thenReturn(productViews);
        //WHEN
        List<ProductView> result = underTest.getQueue(CHARACTER_ID);
        //THEN
        verify(factoryQueryService).getFactoryIdOfCharacter(CHARACTER_ID);
        verify(productDao).getByFactoryId(FACTORY_ID);
        verify(productViewConverter).convertDomain(productList);
        assertThat(result).isEqualTo(productViews);
    }
}