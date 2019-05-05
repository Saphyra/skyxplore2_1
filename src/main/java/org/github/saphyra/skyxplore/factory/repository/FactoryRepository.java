package org.github.saphyra.skyxplore.factory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
//TODO unit test
interface FactoryRepository extends JpaRepository<FactoryEntity, String> {
    void deleteByCharacterId(String characterId);

    Optional<FactoryEntity> findByCharacterId(String characterId);
}
