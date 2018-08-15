package skyxplore.dataaccess.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import skyxplore.domain.friend.friendship.FriendshipEntity;

@Repository
//TODO unit test
public interface FriendshipRepository extends JpaRepository<FriendshipEntity, String> {
}