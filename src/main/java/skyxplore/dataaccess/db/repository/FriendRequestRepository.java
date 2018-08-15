package skyxplore.dataaccess.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import skyxplore.domain.friend.friendrequest.FriendRequestEntity;

@Repository
//TODO unit test
public interface FriendRequestRepository extends JpaRepository<FriendRequestEntity, Long> {
    @Query("SELECT f FROM FriendRequestEntity f WHERE " +
        "(f.characterId = :characterId AND f.friendId = :friendId)" +
        " OR " +
        "(f.characterId = :friendId AND f.friendId = :characterId)")
    List<FriendRequestEntity> findByCharacterIdOrFriendId(@Param("characterId") String characterId, @Param("friendId") String friendId);
}
