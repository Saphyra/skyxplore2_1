package skyxplore.dataaccess.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import skyxplore.domain.friend.blockeduser.BlockedCharacterEntity;

@Repository
//TODO unit test
public interface BlockedCharacterRepository extends JpaRepository<BlockedCharacterEntity, Long> {
    List<BlockedCharacterEntity> findByCharacterId(String characterId);

    BlockedCharacterEntity findByCharacterIdAndBlockedCharacterId(String characterId, String blockedCharacterId);

    @Query("SELECT b FROM BlockedCharacterEntity b WHERE " +
        "(b.characterId = :characterId AND b.blockedCharacterId = :blockedCharacterId)" +
        " OR " +
        "(b.characterId = :blockedCharacterId AND b.blockedCharacterId = :characterId)")
    List<BlockedCharacterEntity> findByCharacterIdOrBlockedCharacterId(@Param("characterId") String characterId, @Param("blockedCharacterId") String blockedCharacterId);
}
