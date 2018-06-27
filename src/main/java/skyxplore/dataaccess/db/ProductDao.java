package skyxplore.dataaccess.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.db.repository.ProductRepository;
import skyxplore.domain.product.Product;
import skyxplore.domain.product.ProductConverter;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductDao {
    private final ProductConverter productConverter;
    private final ProductRepository productRepository;

    public void deleteByFactoryId(String factoryId){
        productRepository.deleteByFactoryId(factoryId);
    }

    public void save(Product product){
        productRepository.save(productConverter.convertDomain(product));
    }
}
