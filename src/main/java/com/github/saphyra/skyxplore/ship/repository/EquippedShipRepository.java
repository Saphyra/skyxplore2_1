package com.github.saphyra.skyxplore.ship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface EquippedShipRepository extends JpaRepository<EquippedShipEntity, String> {
    Optional<EquippedShipEntity> findByCharacterId(String characterId);
}
