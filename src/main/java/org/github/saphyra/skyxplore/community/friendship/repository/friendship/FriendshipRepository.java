package org.github.saphyra.skyxplore.community.friendship.repository.friendship;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
//TODO unit test
interface FriendshipRepository extends JpaRepository<FriendshipEntity, String> {
    @Transactional
    @Modifying
    @Query("DELETE FriendshipEntity f WHERE f.characterId = :characterId OR f.friendId = :characterId")
    void deleteByCharacterId(@Param("characterId") String characterId);

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
