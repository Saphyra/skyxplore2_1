package skyxplore.dataaccess.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import skyxplore.domain.community.friendrequest.FriendRequestEntity;

import java.util.List;

@Repository
//TODO unit test
public interface FriendRequestRepository extends JpaRepository<FriendRequestEntity, String> {
    List<FriendRequestEntity> findByCharacterId(String characterId);

    @Query("SELECT f FROM FriendRequestEntity f WHERE " +
        "(f.characterId = :characterId AND f.friendId = :friendId)" +
        " OR " +
        "(f.characterId = :friendId AND f.friendId = :characterId)")
    List<FriendRequestEntity> findByCharacterIdOrFriendId(@Param("characterId") String characterId, @Param("friendId") String friendId);

    List<FriendRequestEntity> getByFriendId(String characterId);
}
