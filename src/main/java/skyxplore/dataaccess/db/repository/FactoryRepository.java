package skyxplore.dataaccess.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import skyxplore.domain.factory.FactoryEntity;

@Repository
public interface FactoryRepository extends JpaRepository<FactoryEntity, String> {
    void deleteByCharacterId(String characterId);

    FactoryEntity findByCharacterId(String characterId);
}
