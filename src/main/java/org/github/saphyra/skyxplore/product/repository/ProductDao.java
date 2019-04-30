package org.github.saphyra.skyxplore.product.repository;

import com.github.saphyra.converter.Converter;
import com.github.saphyra.dao.AbstractDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.github.saphyra.skyxplore.product.domain.Product;
import org.github.saphyra.skyxplore.common.DateTimeUtil;

import java.time.OffsetDateTime;
import java.util.List;

@Component
@Slf4j
//TODO unit test
public class ProductDao extends AbstractDao<ProductEntity, Product, String, ProductRepository> {
    private final DateTimeUtil dateTimeUtil;

    public ProductDao(
        Converter<ProductEntity, Product> converter,
        ProductRepository repository,
        DateTimeUtil dateTimeUtil
    ) {
        super(converter, repository);
        this.dateTimeUtil = dateTimeUtil;
    }

    public void deleteByFactoryId(String factoryId) {
        repository.deleteByFactoryId(factoryId);
    }

    public List<Product> findByFactoryId(String factoryId) {
        return converter.convertEntity(repository.findByFactoryId(factoryId));
    }

    public List<Product> getFinishedProducts() {
        OffsetDateTime time = dateTimeUtil.now();
        return converter.convertEntity(repository.getFinishedProducts(dateTimeUtil.convertDomain(time)));
    }

    public List<Product> getFirstOfQueue() {
        return converter.convertEntity(repository.getFirstOfQueue());
    }
}
