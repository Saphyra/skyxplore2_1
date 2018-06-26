package skyxplore.domain.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import skyxplore.domain.ConverterBase;
import skyxplore.util.DateTimeConverter;

@Component
@RequiredArgsConstructor
public class ProductConverter extends ConverterBase<ProductEntity, Product> {
    private final DateTimeConverter dateTimeConverter;

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
        domain.setOrder(entity.getProductOrder());
        domain.setStartTime(dateTimeConverter.convertEntity(entity.getStartTime()));

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
        entity.setProductOrder(domain.getOrder());
        entity.setStartTime(dateTimeConverter.convertDomain(domain.getStartTime()));

        return entity;
    }
}
