package skyxplore.domain.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.domain.ConverterBase;
import skyxplore.util.DateTimeUtil;

@Component
@RequiredArgsConstructor
@Slf4j
//TODO unit test
public class ProductConverter extends ConverterBase<ProductEntity, Product> {
    private final DateTimeUtil dateTimeUtil;

    @Override
    public Product convertEntity(ProductEntity entity) {
        if (entity == null) {
            return null;
        }
        Product domain = new Product();
        domain.setProductId(entity.getProductId());
        domain.setFactoryId(entity.getFactoryId());
        domain.setElementId(entity.getElementId());
        domain.setAmount(entity.getAmount());
        domain.setConstructionTime(entity.getConstructionTime());
        domain.setAddedAt(entity.getAddedAt());
        domain.setStartTime(dateTimeUtil.convertEntity(entity.getStartTime()));
        domain.setEndTime(dateTimeUtil.convertEntity(entity.getEndTime()));

        return domain;
    }

    @Override
    public ProductEntity convertDomain(Product domain) {
        if (domain == null) {
            return null;
        }
        ProductEntity entity = new ProductEntity();
        entity.setProductId(domain.getProductId());
        entity.setFactoryId(domain.getFactoryId());
        entity.setElementId(domain.getElementId());
        entity.setAmount(domain.getAmount());
        entity.setConstructionTime(domain.getConstructionTime());
        entity.setAddedAt(domain.getAddedAt());
        entity.setStartTime(dateTimeUtil.convertDomain(domain.getStartTime()));
        entity.setEndTime(dateTimeUtil.convertDomain(domain.getEndTime()));
        return entity;
    }
}
