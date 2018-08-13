package skyxplore.dataaccess.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skyxplore.domain.friend.request.FriendRequestEntity;

@Repository
public interface FriendRequestRepository extends JpaRepository<Long, FriendRequestEntity> {
}
