package org.github.saphyra.skyxplore.ship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
//TODO unit test
interface EquippedShipRepository extends JpaRepository<EquippedShipEntity, String> {
    EquippedShipEntity getByCharacterId(String characterId);
}
