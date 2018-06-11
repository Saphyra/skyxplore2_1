package skyxplore.dataaccess.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skyxplore.dataaccess.db.entity.EquippedShipEntity;

@Repository
public interface EquippedShipRepository extends JpaRepository<EquippedShipEntity, String> {
    EquippedShipEntity getByCharacterId(String characterId);
}
