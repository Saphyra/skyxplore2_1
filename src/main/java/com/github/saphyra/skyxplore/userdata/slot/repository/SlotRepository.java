package com.github.saphyra.skyxplore.userdata.slot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
interface SlotRepository extends JpaRepository<SlotEntity, String> {
    @Transactional
    void deleteByShipId(String shipId);
}
