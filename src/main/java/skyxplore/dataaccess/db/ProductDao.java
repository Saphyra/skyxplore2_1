package skyxplore.dataaccess.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.db.repository.ProductRepository;
import skyxplore.domain.product.Product;
import skyxplore.domain.product.ProductConverter;
import skyxplore.util.DateTimeConverter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@SuppressWarnings("WeakerAccess")
@Component
@RequiredArgsConstructor
@Slf4j
public class ProductDao {
    private final DateTimeConverter dateTimeConverter;
    private final ProductConverter productConverter;
    private final ProductRepository productRepository;

    public void  delete(Product product){
        productRepository.deleteById(product.getProductId());
    }

    public void deleteByFactoryId(String factoryId){
        productRepository.deleteByFactoryId(factoryId);
    }

    public List<Product> findByFactoryId(String factoryId){
        return productConverter.convertEntity(productRepository.findByFactoryId(factoryId));
    }

    public List<Product> getFinishedProducts(){
        LocalDateTime time = LocalDateTime.now(ZoneOffset.UTC);
        return productConverter.convertEntity(productRepository.getFinishedProducts(dateTimeConverter.convertDomain(time)));
    }

    public List<Product> getFirstOfQueue(){
        return productConverter.convertEntity(productRepository.getFirstOfQueue());
    }

    public void save(Product product){
        productRepository.save(productConverter.convertDomain(product));
    }
}
