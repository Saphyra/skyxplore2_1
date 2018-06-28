package skyxplore.dataaccess.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import skyxplore.domain.product.ProductEntity;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, String> {
    void deleteByFactoryId(String factoryId);
    List<ProductEntity> findByFactoryId(String factoryId);

    @Query("select p from ProductEntity p where p.endTime < :finishTime")
    List<ProductEntity> getFinishedProducts(@Param("finishTime") Long finishTime);
}
