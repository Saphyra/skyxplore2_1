package skyxplore.dataaccess.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skyxplore.dataaccess.db.entity.SlotEntity;

public interface SlotRepository extends JpaRepository<SlotEntity, String> {
    void deleteByShipId(String shipId);
}
