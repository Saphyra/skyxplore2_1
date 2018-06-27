package skyxplore.dataaccess.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skyxplore.domain.product.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, String> {
    void deleteByFactoryId(String factoryId);
}
