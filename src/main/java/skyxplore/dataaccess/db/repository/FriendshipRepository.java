package skyxplore.dataaccess.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import skyxplore.domain.community.friendship.FriendshipEntity;

import java.util.List;

@Repository
//TODO unit test
public interface FriendshipRepository extends JpaRepository<FriendshipEntity, String> {
    @Query("SELECT f FROM FriendshipEntity f WHERE " +
        "(f.characterId = :characterId AND f.friendId = :friendId)" +
        " OR " +
        "(f.characterId = :friendId AND f.friendId = :characterId)")
    List<FriendshipEntity> getByCharacterIdOrFriendId(
        @Param("characterId") String characterId,
        @Param("friendId") String friendId
    );

    @Query("SELECT f FROM FriendshipEntity f WHERE " +
        "f.characterId = :characterId" +
        " OR " +
        "f.friendId = :characterId")
    List<FriendshipEntity> getFriendshipsOfCharacter(@Param("characterId") String characterId);
}
