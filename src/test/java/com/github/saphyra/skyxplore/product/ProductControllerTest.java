package com.github.saphyra.skyxplore.product;

import com.github.saphyra.skyxplore.product.domain.ProductView;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {
    private static final String CHARACTER_ID = "character_id";

    @Mock
    private ProductQueryService productQueryService;

    @InjectMocks
    private ProductController underTest;

    @Test
    public void testGetQueueShouldCallFacadeAndReturnResult() {
        //GIVEN
        List<ProductView> productViews = Arrays.asList(ProductView.builder().build());
        when(productQueryService.getQueue(CHARACTER_ID)).thenReturn(productViews);
        //WHEN
        List<ProductView> result = underTest.getQueue(CHARACTER_ID);
        //THEN
        verify(productQueryService).getQueue(CHARACTER_ID);
        assertThat(result).isEqualTo(productViews);
    }
}