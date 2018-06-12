package skyxplore.dataaccess.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skyxplore.domain.slot.SlotEntity;

public interface SlotRepository extends JpaRepository<SlotEntity, String> {
    void deleteByShipId(String shipId);
}
