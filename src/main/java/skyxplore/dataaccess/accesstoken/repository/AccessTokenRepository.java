package skyxplore.dataaccess.accesstoken.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skyxplore.dataaccess.accesstoken.entity.AccessTokenEntity;

public interface AccessTokenRepository extends JpaRepository<AccessTokenEntity, String> {
    AccessTokenEntity findByUserId(Long userId);
    void deleteByUserId(Long userId);
}
