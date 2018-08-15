package skyxplore.dataaccess.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import skyxplore.domain.slot.SlotEntity;

@Repository
//TODO unit test
public interface SlotRepository extends JpaRepository<SlotEntity, String> {
    void deleteByShipId(String shipId);
}
