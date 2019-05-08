package com.github.saphyra.skyxplore.factory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface FactoryRepository extends JpaRepository<FactoryEntity, String> {
    Optional<FactoryEntity> findByCharacterId(String characterId);
}
