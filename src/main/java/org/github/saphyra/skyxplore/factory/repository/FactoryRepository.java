package org.github.saphyra.skyxplore.factory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface FactoryRepository extends JpaRepository<FactoryEntity, String> {
    void deleteByCharacterId(String characterId);

    FactoryEntity findByCharacterId(String characterId);
}
