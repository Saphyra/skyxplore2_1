package com.github.saphyra.skyxplore.userdata.community.friendship.repository.friendrequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
interface FriendRequestRepository extends JpaRepository<FriendRequestEntity, String> {
    @Modifying
    @Transactional
    @Query("DELETE FriendRequestEntity f WHERE f.characterId = :characterId OR f.friendId = :characterId")
    void deleteByCharacterId(@Param("characterId") String characterId);

    List<FriendRequestEntity> getByCharacterId(String characterId);

    @Query("SELECT f FROM FriendRequestEntity f WHERE " +
        "(f.characterId = :characterId AND f.friendId = :friendId)" +
        " OR " +
        "(f.characterId = :friendId AND f.friendId = :characterId)")
    List<FriendRequestEntity> getByCharacterIdOrFriendId(@Param("characterId") String characterId, @Param("friendId") String friendId);

    List<FriendRequestEntity> getByFriendId(String characterId);
}
