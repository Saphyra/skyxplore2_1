package com.github.saphyra.skyxplore.product;

import com.github.saphyra.skyxplore.common.AbstractViewConverter;
import com.github.saphyra.skyxplore.common.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import com.github.saphyra.skyxplore.product.domain.ProductView;
import org.springframework.stereotype.Component;
import com.github.saphyra.skyxplore.product.domain.Product;

@Component
@RequiredArgsConstructor
class ProductViewConverter extends AbstractViewConverter<Product, ProductView> {
    private final DateTimeUtil dateTimeUtil;

    @Override
    public ProductView convertDomain(Product domain) {
        return ProductView.builder()
            .productId(domain.getProductId())
            .factoryId(domain.getFactoryId())
            .elementId(domain.getElementId())
            .amount(domain.getAmount())
            .constructionTime(domain.getConstructionTime())
            .addedAt(domain.getAddedAt())
            .startTime(dateTimeUtil.convertDomain(domain.getStartTime()))
            .endTime((dateTimeUtil.convertDomain(domain.getEndTime())))
            .build();
    }
}
