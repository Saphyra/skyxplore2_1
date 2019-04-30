package org.github.saphyra.skyxplore.product.repository;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.encryption.impl.IntegerEncryptor;
import com.github.saphyra.encryption.impl.StringEncryptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.github.saphyra.skyxplore.product.domain.Product;
import org.springframework.stereotype.Component;
import org.github.saphyra.skyxplore.common.DateTimeUtil;

@Component
@RequiredArgsConstructor
@Slf4j
class ProductConverter extends ConverterBase<ProductEntity, Product> {
    private final DateTimeUtil dateTimeUtil;
    private final IntegerEncryptor integerEncryptor;
    private final StringEncryptor stringEncryptor;

    @Override
    public Product processEntityConversion(ProductEntity entity) {
        if (entity == null) {
            return null;
        }
        Product domain = new Product();
        domain.setProductId(entity.getProductId());
        domain.setFactoryId(entity.getFactoryId());
        domain.setElementId(stringEncryptor.decryptEntity(entity.getElementId(), entity.getProductId()));
        domain.setAmount(integerEncryptor.decryptEntity(entity.getAmount(), entity.getProductId()));
        domain.setConstructionTime(integerEncryptor.decryptEntity(entity.getConstructionTime(), entity.getProductId()));
        domain.setAddedAt(entity.getAddedAt());
        domain.setStartTime(dateTimeUtil.convertEntity(entity.getStartTime()));
        domain.setEndTime(dateTimeUtil.convertEntity(entity.getEndTime()));

        return domain;
    }

    @Override
    public ProductEntity processDomainConversion(Product domain) {
        if (domain == null) {
            throw new IllegalArgumentException("domain must not be null.");
        }
        ProductEntity entity = new ProductEntity();
        entity.setProductId(domain.getProductId());
        entity.setFactoryId(domain.getFactoryId());
        entity.setElementId(stringEncryptor.encryptEntity(domain.getElementId(), domain.getProductId()));
        entity.setAmount(integerEncryptor.encryptEntity(domain.getAmount(), domain.getProductId()));
        entity.setConstructionTime(integerEncryptor.encryptEntity(domain.getConstructionTime(), domain.getProductId()));
        entity.setAddedAt(domain.getAddedAt());
        entity.setStartTime(dateTimeUtil.convertDomain(domain.getStartTime()));
        entity.setEndTime(dateTimeUtil.convertDomain(domain.getEndTime()));
        return entity;
    }
}
