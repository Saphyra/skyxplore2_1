package com.github.saphyra.skyxplore.userdata.product.factory;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.common.DateTimeUtil;
import com.github.saphyra.skyxplore.userdata.product.ProductQueryService;
import com.github.saphyra.skyxplore.userdata.product.domain.Product;
import com.github.saphyra.skyxplore.userdata.product.repository.ProductDao;

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

    @Mock
    private Product product;

    @Test
    public void startProducts() {
        //GIVEN
        given(product.getConstructionTime()).willReturn(CONSTRUCTION_TIME);
        given(productQueryService.getFirstFromQueue()).willReturn(Arrays.asList(product));

        given(dateTimeUtil.now()).willReturn(NOW);
        //WHEN
        underTest.startProducts();
        //THEN
        verify(productDao).save(product);
        verify(product).setStartTime(NOW);
        verify(product).setEndTime(NOW.plusSeconds(CONSTRUCTION_TIME));
    }
}