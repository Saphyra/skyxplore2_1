package com.github.saphyra.skyxplore.userdata.product.repository;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.encryption.impl.IntegerEncryptor;
import com.github.saphyra.encryption.impl.StringEncryptor;
import com.github.saphyra.skyxplore.common.DateTimeUtil;
import com.github.saphyra.skyxplore.userdata.product.domain.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
class ProductConverter extends ConverterBase<ProductEntity, Product> {
    private final DateTimeUtil dateTimeUtil;
    private final IntegerEncryptor integerEncryptor;
    private final StringEncryptor stringEncryptor;

    @Override
    public Product processEntityConversion(ProductEntity entity) {
        return Product.builder()
            .productId(entity.getProductId())
            .factoryId(entity.getFactoryId())
            .elementId(stringEncryptor.decryptEntity(entity.getElementId(), entity.getProductId()))
            .amount(integerEncryptor.decryptEntity(entity.getAmount(), entity.getProductId()))
            .constructionTime(integerEncryptor.decryptEntity(entity.getConstructionTime(), entity.getProductId()))
            .addedAt(entity.getAddedAt())
            .startTime(dateTimeUtil.convertEntity(entity.getStartTime()))
            .endTime(dateTimeUtil.convertEntity(entity.getEndTime()))
            .build();
    }

    @Override
    public ProductEntity processDomainConversion(Product domain) {
        return ProductEntity.builder()
            .productId(domain.getProductId())
            .factoryId(domain.getFactoryId())
            .elementId(stringEncryptor.encryptEntity(domain.getElementId(), domain.getProductId()))
            .amount(integerEncryptor.encryptEntity(domain.getAmount(), domain.getProductId()))
            .constructionTime(integerEncryptor.encryptEntity(domain.getConstructionTime(), domain.getProductId()))
            .addedAt(domain.getAddedAt())
            .startTime(dateTimeUtil.convertDomain(domain.getStartTime()))
            .endTime(dateTimeUtil.convertDomain(domain.getEndTime()))
            .build();
    }
}
