package org.github.saphyra.skyxplore.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
//TODO unit test
interface AccessTokenRepository extends JpaRepository<AccessTokenEntity, String> {
    void deleteByUserId(String userId);

    @Transactional
    @Modifying
    @Query(value = "DELETE AccessTokenEntity a WHERE a.lastAccess < :expiration")
    void deleteExpired(@Param("expiration") Long expiration);

    AccessTokenEntity findByCharacterId(String characterId);

    AccessTokenEntity findByUserId(String userId);

    AccessTokenEntity findByUserIdOrAccessTokenId(String userId, String accessTokenId);
}
