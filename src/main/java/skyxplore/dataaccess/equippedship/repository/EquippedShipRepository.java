package skyxplore.dataaccess.equippedship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skyxplore.dataaccess.equippedship.entity.EquippedShipEntity;

@Repository
public interface EquippedShipRepository extends JpaRepository<EquippedShipEntity, Long> {
    EquippedShipEntity findByCharacterId(Long characterId);
}
