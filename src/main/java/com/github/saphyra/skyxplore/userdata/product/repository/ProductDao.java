package com.github.saphyra.skyxplore.userdata.product.repository;

import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.common.DateTimeUtil;
import com.github.saphyra.skyxplore.common.event.FactoryDeletedEvent;
import lombok.extern.slf4j.Slf4j;
import com.github.saphyra.skyxplore.userdata.product.domain.Product;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;

@Component
@Slf4j
public class ProductDao extends AbstractDao<ProductEntity, Product, String, ProductRepository> {
    private final DateTimeUtil dateTimeUtil;

    public ProductDao(
        ProductConverter converter,
        ProductRepository repository,
        DateTimeUtil dateTimeUtil
    ) {
        super(converter, repository);
        this.dateTimeUtil = dateTimeUtil;
    }

    @EventListener
    void factoryDeletedEventListener(FactoryDeletedEvent event) {
        repository.deleteByFactoryId(event.getFactoryId());
    }

    public List<Product> getByFactoryId(String factoryId) {
        return converter.convertEntity(repository.getByFactoryId(factoryId));
    }

    public List<Product> getFinishedProducts() {
        OffsetDateTime time = dateTimeUtil.now();
        return converter.convertEntity(repository.getFinishedProducts(dateTimeUtil.convertDomain(time)));
    }

    public List<Product> getFirstOfQueue() {
        return converter.convertEntity(repository.getFirstOfQueue());
    }
}
