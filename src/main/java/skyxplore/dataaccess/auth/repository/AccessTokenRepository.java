package skyxplore.dataaccess.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skyxplore.dataaccess.auth.entity.AccessTokenEntity;

public interface AccessTokenRepository extends JpaRepository<AccessTokenEntity, String> {
    AccessTokenEntity findByUserId(Long userId);
    void deleteByUserId(Long userId);
}
