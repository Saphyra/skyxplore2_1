package skyxplore.dataaccess.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import skyxplore.domain.accesstoken.AccessTokenEntity;

import javax.transaction.Transactional;
import java.util.Calendar;

public interface AccessTokenRepository extends JpaRepository<AccessTokenEntity, String> {
    void deleteByUserId(String userId);

    @Transactional
    @Modifying
    @Query(value = "DELETE AccessTokenEntity a WHERE a.lastAccess < :expiration")
    void deleteExpired(@Param("expiration") Calendar expiration);

    AccessTokenEntity findByUserId(String userId);
}
