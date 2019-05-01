package org.github.saphyra.skyxplore.product;

import lombok.RequiredArgsConstructor;
import org.github.saphyra.skyxplore.product.domain.ProductView;
import org.springframework.stereotype.Component;
import org.github.saphyra.skyxplore.product.domain.Product;
import skyxplore.controller.view.AbstractViewConverter;
import org.github.saphyra.skyxplore.common.DateTimeUtil;

@Component
@RequiredArgsConstructor
class ProductViewConverter extends AbstractViewConverter<Product, ProductView> {
    private final DateTimeUtil dateTimeUtil;

    @Override
    public ProductView convertDomain(Product domain) {
        if (domain == null) {
            return null;
        }
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
