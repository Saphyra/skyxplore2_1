package skyxplore.dataaccess.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import skyxplore.domain.product.ProductEntity;

import java.util.List;

@Repository
//TODO unit test
public interface ProductRepository extends JpaRepository<ProductEntity, String> {
    void deleteByFactoryId(String factoryId);

    List<ProductEntity> findByFactoryId(String factoryId);

    @Query("select p from ProductEntity p where p.endTime < :finishTime")
    List<ProductEntity> getFinishedProducts(@Param("finishTime") Long finishTime);

    @Query("select p from ProductEntity p where not exists(select p1 from ProductEntity p1 where p1.factoryId = p.factoryId and p1.startTime is not null) and addedAt = (select min(p1.addedAt) from ProductEntity p1 where p1.factoryId = p.factoryId)")
    List<ProductEntity> getFirstOfQueue();
}
