package com.github.saphyra.skyxplore.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.factory.FactoryQueryService;
import com.github.saphyra.skyxplore.product.domain.Product;
import com.github.saphyra.skyxplore.product.domain.ProductView;
import com.github.saphyra.skyxplore.product.repository.ProductDao;

@RunWith(MockitoJUnitRunner.class)
public class ProductQueryServiceTest {
    private static final String CHARACTER_ID = "character_id";
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

    @Mock
    private Product product1;

    @Mock
    private Product product2;

    @Mock
    private Product product3;

    @Mock
    private ProductView productView;

    @Test
    public void getFirstFromQueue() {
        //GIVEN
        given(product1.getFactoryId()).willReturn(FACTORY_ID_1);
        given(product2.getFactoryId()).willReturn(FACTORY_ID_1);
        given(product3.getFactoryId()).willReturn(FACTORY_ID_2);
        given(productDao.getFirstOfQueue()).willReturn(Arrays.asList(product1, product2, product3));
        //WHEN
        List<Product> result = underTest.getFirstFromQueue();
        //THEN
        assertThat(result).containsExactlyInAnyOrder(product1, product3);
    }

    @Test
    public void testGetQueue() {
        //GIVEN
        when(factoryQueryService.getFactoryIdOfCharacter(CHARACTER_ID)).thenReturn(FACTORY_ID_1);

        List<Product> productList = Arrays.asList(product1);
        when(productDao.getByFactoryId(FACTORY_ID_1)).thenReturn(productList);

        List<ProductView> productViews = Arrays.asList(productView);
        when(productViewConverter.convertDomain(productList)).thenReturn(productViews);
        //WHEN
        List<ProductView> result = underTest.getQueue(CHARACTER_ID);
        //THEN
        verify(factoryQueryService).getFactoryIdOfCharacter(CHARACTER_ID);
        verify(productDao).getByFactoryId(FACTORY_ID_1);
        verify(productViewConverter).convertDomain(productList);
        assertThat(result).isEqualTo(productViews);
    }
}