package com.github.saphyra.skyxplore.platform.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
interface AccessTokenRepository extends JpaRepository<AccessTokenEntity, String> {
    @Transactional
    @Modifying
    @Query("UPDATE AccessTokenEntity a SET a.characterId = null WHERE a.characterId = :characterId")
    void cleanUpCharacterId(@Param("characterId") String characterId);

    @Transactional
    void deleteByUserId(String userId);

    @Transactional
    @Modifying
    @Query(value = "DELETE AccessTokenEntity a WHERE a.lastAccess < :expiration")
    void deleteExpired(@Param("expiration") Long expiration);

    Optional<AccessTokenEntity> findByCharacterId(String characterId);

    Optional<AccessTokenEntity> findByUserId(String userId);
}
