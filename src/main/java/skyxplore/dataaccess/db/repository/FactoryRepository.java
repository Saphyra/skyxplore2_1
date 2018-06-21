package skyxplore.dataaccess.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skyxplore.domain.factory.FactoryEntity;

public interface FactoryRepository extends JpaRepository<FactoryEntity, String> {
    FactoryEntity findByCharacterId(String characterId);
}
