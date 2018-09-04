package skyxplore.domain.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.domain.ConverterBase;
import skyxplore.encryption.IntegerEncryptor;
import skyxplore.encryption.StringEncryptor;
import skyxplore.util.DateTimeUtil;

@Component
@RequiredArgsConstructor
@Slf4j
//TODO unit test
public class ProductConverter extends ConverterBase<ProductEntity, Product> {
    private final DateTimeUtil dateTimeUtil;
    private final IntegerEncryptor integerEncryptor;
    private final StringEncryptor stringEncryptor;

    @Override
    public Product convertEntity(ProductEntity entity) {
        if (entity == null) {
            return null;
        }
        Product domain = new Product();
        domain.setProductId(entity.getProductId());
        domain.setFactoryId(entity.getFactoryId());
        domain.setElementId(stringEncryptor.decryptEntity(entity.getElementId(), entity.getProductId()));
        domain.setAmount(integerEncryptor.decrypt(entity.getAmount(), entity.getProductId()));
        domain.setConstructionTime(integerEncryptor.decrypt(entity.getConstructionTime(), entity.getProductId()));
        domain.setAddedAt(entity.getAddedAt());
        domain.setStartTime(dateTimeUtil.convertEntity(entity.getStartTime()));
        domain.setEndTime(dateTimeUtil.convertEntity(entity.getEndTime()));

        return domain;
    }

    @Override
    public ProductEntity convertDomain(Product domain) {
        if(domain == null){
            throw new IllegalArgumentException("domain must not be null.");
        }
        ProductEntity entity = new ProductEntity();
        entity.setProductId(domain.getProductId());
        entity.setFactoryId(domain.getFactoryId());
        entity.setElementId(stringEncryptor.encryptEntity(domain.getElementId(), domain.getProductId()));
        entity.setAmount(integerEncryptor.encrypt(domain.getAmount(), domain.getProductId()));
        entity.setConstructionTime(integerEncryptor.encrypt(domain.getConstructionTime(), domain.getProductId()));
        entity.setAddedAt(domain.getAddedAt());
        entity.setStartTime(dateTimeUtil.convertDomain(domain.getStartTime()));
        entity.setEndTime(dateTimeUtil.convertDomain(domain.getEndTime()));
        return entity;
    }
}
