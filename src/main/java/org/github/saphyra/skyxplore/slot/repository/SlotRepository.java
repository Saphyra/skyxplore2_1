package org.github.saphyra.skyxplore.slot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
//TODO unit test
interface SlotRepository extends JpaRepository<SlotEntity, String> {
    void deleteByShipId(String shipId);
}
