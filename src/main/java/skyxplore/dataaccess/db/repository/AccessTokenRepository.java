package skyxplore.dataaccess.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import skyxplore.domain.accesstoken.AccessTokenEntity;

import javax.transaction.Transactional;

@Repository
//TODO unit test
public interface AccessTokenRepository extends JpaRepository<AccessTokenEntity, String> {
    void deleteByUserId(String userId);

    @Transactional
    @Modifying
    @Query(value = "DELETE AccessTokenEntity a WHERE a.lastAccess < :expiration")
    void deleteExpired(@Param("expiration") Long expiration);

    AccessTokenEntity findByUserId(String userId);

    AccessTokenEntity findByUserIdOrAccessTokenId(String userId, String accessTokenId);
}
