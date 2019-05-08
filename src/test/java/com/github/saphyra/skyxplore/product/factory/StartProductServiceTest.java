package com.github.saphyra.skyxplore.product.factory;

import com.github.saphyra.skyxplore.common.DateTimeUtil;
import com.github.saphyra.skyxplore.product.ProductQueryService;
import com.github.saphyra.skyxplore.product.domain.Product;
import com.github.saphyra.skyxplore.product.repository.ProductDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class StartProductServiceTest {
    private static final OffsetDateTime NOW = OffsetDateTime.now(ZoneOffset.UTC);
    private static final Integer CONSTRUCTION_TIME = 30;
    @Mock
    private DateTimeUtil dateTimeUtil;

    @Mock
    private ProductDao productDao;

    @Mock
    private ProductQueryService productQueryService;

    @InjectMocks
    private StartProductService underTest;

    @Test
    public void startProducts() {
        //GIVEN
        Product product = Product.builder()
            .constructionTime(CONSTRUCTION_TIME)
            .build();
        given(productQueryService.getFirstFromQueue()).willReturn(Arrays.asList(product));

        given(dateTimeUtil.now()).willReturn(NOW);
        //WHEN
        underTest.startProducts();
        //THEN
        verify(productDao).save(product);
        assertThat(product.getStartTime()).isEqualTo(NOW);
        assertThat(product.getEndTime()).isEqualTo(NOW.plusSeconds(CONSTRUCTION_TIME));
    }
}