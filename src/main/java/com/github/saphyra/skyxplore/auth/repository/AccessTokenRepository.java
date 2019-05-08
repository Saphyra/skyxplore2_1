package com.github.saphyra.skyxplore.auth.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
interface AccessTokenRepository extends JpaRepository<AccessTokenEntity, String> {
    @Transactional
    void deleteByUserId(String userId);

    @Transactional
    @Modifying
    @Query(value = "DELETE AccessTokenEntity a WHERE a.lastAccess < :expiration")
    void deleteExpired(@Param("expiration") Long expiration);

    Optional<AccessTokenEntity> findByCharacterId(String characterId);

    Optional<AccessTokenEntity> findByUserId(String userId);
}
