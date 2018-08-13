package skyxplore.dataaccess.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skyxplore.domain.friend.blockeduser.BlockedUser;

@Repository
public interface BlockedUserRepository extends JpaRepository<Long, BlockedUser> {
}
