package skyxplore.dataaccess.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skyxplore.domain.friend.blockeduser.BlockedUserEntity;

@Repository
public interface BlockedCharacterRepository extends JpaRepository<BlockedUserEntity, Long> {
}
