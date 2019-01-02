package skyxplore.dataaccess.db;

import com.github.saphyra.converter.Converter;
import com.github.saphyra.dao.AbstractDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.db.repository.ProductRepository;
import skyxplore.domain.product.Product;
import skyxplore.domain.product.ProductEntity;
import skyxplore.util.DateTimeUtil;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@SuppressWarnings("WeakerAccess")
@Component
@Slf4j
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
        LocalDateTime time = LocalDateTime.now(ZoneOffset.UTC);
        return converter.convertEntity(repository.getFinishedProducts(dateTimeUtil.convertDomain(time)));
    }

    public List<Product> getFirstOfQueue() {
        return converter.convertEntity(repository.getFirstOfQueue());
    }
}
